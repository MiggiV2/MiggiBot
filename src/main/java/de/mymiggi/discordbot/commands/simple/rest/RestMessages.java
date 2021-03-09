package de.mymiggi.discordbot.commands.simple.rest;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;
import de.mymiggi.discordbot.tools.util.SHA_512;

public class RestMessages
{
	private Gson gson = new Gson();
	private Logger logger = LoggerFactory.getLogger("RestMessages");

	public void sendVisiCard(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("A Message Service")
			.setDescription("Its am simple message service and in early access!")
			.setAuthor("Click here", "https://mymiggi.de/read.html", "https://cdn.discordapp.com/avatars/309696934174785556/33e6da076c77181fcd088c110a7f5d16.png?size=128")
			.addField("What can i do there", ":thought_balloon:")
			.addInlineField("Gobal Chat :globe_with_meridians:", "In the moment, you can send messages in a gobal chat")
			.addInlineField("Simple Account :white_check_mark:", "You can create an acount and dont need your email Adress" + System.lineSeparator() + "It's FREE")
			.setColor(Color.BLACK)
			.setImage("https://techkatha.com/wp-content/uploads/2020/09/Web-Development-1024x683.jpg");

		event.getChannel().sendMessage(embed);
		logger.info(event.getMessageAuthor().getName() + " used command!");
	}

	public void read(MessageCreateEvent event)
	{
		String result = null;
		Client client = new Client();
		try
		{
			result = client.sendGet("http://mymiggi.de/message");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Message[] messages = gson.fromJson(result, Message[].class);
		EmbedBuilder embed = new EmbedBuilder();

		if (messages != null)
		{
			embed
				.setTitle("Message from Rest Message-Service")
				.setDescription("Here are all messages from https://mymiggi.de/read.html");

			for (Message temp : messages)
			{
				String head = String.format("**%s** [%s]", temp.getName(), temp.getDateStr());
				String body = temp.getMesContent();
				embed.addField(head, body);
			}
		}
		else
		{
			embed
				.setTitle("Message from Rest Message-Service")
				.setDescription("No messages at the moment! :pleading_face: ")
				.addField("But no problem", "You can  send some there https://mymiggi.de/read.html");
		}
		event.getChannel().sendMessage(embed);
		logger.info(event.getMessageAuthor().getName() + " used command!");
	}

	public boolean send(MessageCreateEvent event, String[] splitted)
	{

		String name = "---";
		String pin = "---";
		String message = "";
		Client client = new Client();

		if (event.getMessageAuthor().getId() != 309696934174785556L)
		{
			notAutherised(event);
			return false;
		}
		for (int i = 1; i < splitted.length; i++)
		{
			message += splitted[i] + " ";
		}
		String token = genUserToken(name, SHA_512.hash(pin));

		try
		{
			if (client.sendPost(name, token, message))
			{
				System.out.println("Send!");
				succesfull(event);
				return true;
			}
			if (client.sendPost(name, token, message))
			{
				System.out.println("Send!");
				succesfull(event);
				return true;
			}
			if (client.sendPost(name, token, message))
			{
				System.out.println("Send!");
				succesfull(event);
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.err.println("Send failed");
		failed(event);
		return false;
	}

	private void succesfull(MessageCreateEvent event)
	{
		new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("Succesfuly send!")
				.setDescription("See your message here https://mymiggi.de/read.html")
				.setColor(Color.ORANGE))
			.send(event.getChannel());
	}

	private void failed(MessageCreateEvent event)
	{
		new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("Error!")
				.setDescription("Something went wrong")
				.setColor(Color.ORANGE))
			.send(event.getChannel());
	}

	private void notAutherised(MessageCreateEvent event)
	{
		new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("Error!")
				.setDescription("Your acount is not linked yeat! Comming soon")
				.setColor(Color.ORANGE))
			.send(event.getChannel());
	}

	private String genUserToken(String name, String pin)
	{
		Date date = new Date();
		SimpleDateFormat part1 = new SimpleDateFormat("ssHH");
		SimpleDateFormat part2 = new SimpleDateFormat("mm");

		System.out.println("[Part1Code]" + part1.format(date));
		return SHA_512.hash((pin + part1.format(date))) + SHA_512.hash(part2.format(date) + name);
	}
}
