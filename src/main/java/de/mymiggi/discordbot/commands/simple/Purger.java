package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.Permissions;

public class Purger
{
	private String lastBotMessageLink;
	private Logger logger = LoggerFactory.getLogger("Purger");

	public void clear(MessageCreateEvent event, String[] context)
	{
		logger.info(event.getMessageAuthor().getName() + " used command!");

		if (!Permissions.isAdmin(event))
		{
			notAdminEmbed(event);
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 0);
			logger.info(event.getMessageAuthor().getName() + " try to use command, but he is not an admin!");
			return;
		}

		int size = Integer.parseInt(context[1]);

		if (size > 100)
		{
			size = 100;
		}
		try
		{
			MessageSet messages = event.getChannel().getMessages(size).get();
			ArrayList<Message> unPinedMessages = new ArrayList<Message>();
			messages.stream().filter(m -> !m.isPinned()).forEach(m -> unPinedMessages.add(m));

			if (event.getMessage().isServerMessage())
			{
				ServerTextChannel channel = BotMainCore.api.getChannelById(event.getChannel().getId()).get().asServerTextChannel().get();
				channel.bulkDelete(unPinedMessages);
			}
			else
			{
				PrivateChannel channel = BotMainCore.api.getChannelById(event.getChannel().getId()).get().asPrivateChannel().get();
				channel.bulkDelete(unPinedMessages);
			}
			int purgedSize = unPinedMessages.size();
			succesfull(event, purgedSize);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	private void succesfull(MessageCreateEvent event, int purgedSize)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("Succesfuly purged!")
				.setFooter("Purged messages: " + purgedSize)
				.setColor(Color.BLUE));
		try
		{
			lastBotMessageLink = m.send(event.getChannel()).get().getLink().toString();
			MessageCoolDown.del(lastBotMessageLink, event.getChannel());
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	private void notAdminEmbed(MessageCreateEvent event)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("You are not an admin!")
				.setFooter("Maybe u can aske the owner?")
				.setColor(Color.RED));
		try
		{
			lastBotMessageLink = m.send(event.getChannel()).get().getLink().toString();
			MessageCoolDown.del(lastBotMessageLink, event.getChannel());
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
