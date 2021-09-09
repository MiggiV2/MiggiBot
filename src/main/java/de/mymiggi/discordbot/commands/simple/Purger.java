package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class Purger
{
	private String lastBotMessageLink;
	private Logger logger = LoggerFactory.getLogger(Purger.class.getSimpleName());

	public void clear(MessageCreateEvent event, String[] context)
	{
		User user = event.getMessageAuthor().asUser().get();
		Server server = event.getServer().get();
		Message message = event.getMessage();
		TextChannel channel = event.getChannel();
		logger.info(user.getName() + " used command!");
		if (!server.isAdmin(user))
		{
			sendNotAdminEmbed(channel);
			if (message != null)
			{
				MessageCoolDown.del(message.getLink().toString(), channel, 0);
				logger.info(user.getName() + " try to use command, but he is not an admin!");
			}
			return;
		}
		int size = (context.length == 2) ? Integer.parseInt(context[1]) : 10;
		if (size > 100)
		{
			size = 100;
		}
		try
		{
			MessageSet messages = channel.getMessages(size).get();
			ArrayList<Message> unPinedMessages = new ArrayList<Message>();
			messages.stream().filter(m -> !m.isPinned()).forEach(m -> unPinedMessages.add(m));

			if (message == null || message.isServerMessage())
			{
				ServerTextChannel serverTextChannel = channel.asServerTextChannel().get();
				serverTextChannel.bulkDelete(unPinedMessages);
			}
			else
			{
				PrivateChannel privateChannel = channel.asPrivateChannel().get();
				privateChannel.bulkDelete(unPinedMessages);
			}
			int purgedSize = unPinedMessages.size();
			sendSuccesfulEmbed(channel, purgedSize);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	public void clear(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		int unconfirmedSize = interaction.getFirstOptionIntValue().orElse(5);
		int size = (unconfirmedSize > 100) ? 100 : unconfirmedSize;
		interaction.getChannel().ifPresent(channel -> {
			interaction.getServer().ifPresent(server -> {
				User user = interaction.getUser();
				if (!server.isAdmin(user))
				{
					notAnAdmin(interaction);
				}
				else
				{
					purge(interaction, size, channel);
				}
			});
		});
	}

	private void purge(SlashCommandInteraction interaction, int size, TextChannel channel)
	{
		channel.getMessages(size).thenAccept(messages -> {
			ArrayList<Message> unPinedMessages = new ArrayList<Message>();
			messages.stream()
				.filter(m -> !m.isPinned())
				.forEach(m -> unPinedMessages.add(m));
			channel.bulkDelete(messages);
			EmbedBuilder respose = new EmbedBuilder()
				.setTitle("Succesfuly purged!")
				.setDescription("Purged messages: " + unPinedMessages.size());
			interaction.createImmediateResponder()
				.setContent("Successful!")
				.addEmbed(respose)
				.respond();
		});
	}

	private void notAnAdmin(SlashCommandInteraction interaction)
	{
		EmbedBuilder respose = new EmbedBuilder()
			.setTitle("You are not an admin!")
			.setDescription("Maybe u can aske the owner?")
			.setColor(Color.red);
		interaction.createImmediateResponder()
			.setContent("You are not an admin!")
			.addEmbed(respose)
			.respond();
	}

	private void sendSuccesfulEmbed(TextChannel channel, int purgedSize)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("Succesfully purged!")
				.setFooter("Purged messages: " + purgedSize)
				.setColor(Color.BLUE));
		try
		{
			lastBotMessageLink = m.send(channel).get().getLink().toString();
			MessageCoolDown.del(lastBotMessageLink, channel);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	private void sendNotAdminEmbed(TextChannel channel)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("You are not an admin!")
				.setFooter("Maybe u can aske the owner?")
				.setColor(Color.RED));
		try
		{
			lastBotMessageLink = m.send(channel).get().getLink().toString();
			MessageCoolDown.del(lastBotMessageLink, channel);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
