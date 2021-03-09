package de.mymiggi.discordbot.server.logs.leaving;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.Permissions;

public class NewLeavingLog
{
	private Logger logger = LoggerFactory.getLogger(NewLeavingLog.class.getSimpleName());

	public void add(MessageCreateEvent event)
	{
		LeavingLogCore leavingLogger = BotMainCore.getLeavingLogger();
		LogChannelLoader loader = new LogChannelLoader();
		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 30);

		if (!Permissions.isAdmin(event))
		{
			notAdminEmbed(event);
			logger.info(event.getMessageAuthor().getName() + " try to use command, but he is not an admin!");
			return;
		}
		try
		{
			loader.buildJsonFile(event);
			leavingLogger.syncHashMap();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void done(MessageCreateEvent event, String channelName)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Successfuly added channel " + channelName)
			.setFooter("Btw, thx for using my bot! <3 Miggi");
		try
		{
			String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(embedLink, event.getChannel(), 120);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	public void notFoundServer(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry Server not found!")
			.setDescription("Maybe a typing error?" + System.lineSeparator() + BotMainCore.prefix + "add ServerID ChannelID");
		try
		{
			String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(embedLink, event.getChannel(), 120);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	public void notFoundChannel(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry Channel not found!")
			.setDescription("Maybe a typing error?" + System.lineSeparator() + BotMainCore.prefix + "add ServerID ChannelID");
		try
		{
			String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(embedLink, event.getChannel(), 120);
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
				.setTitle("You are not an admin " + event.getMessageAuthor().getName() + "!")
				.setFooter("Maybe u can aske the owner?")
				.setColor(Color.RED));
		try
		{
			String lastBotMessageLink = m.send(event.getChannel()).get().getLink().toString();
			MessageCoolDown.del(lastBotMessageLink, event.getChannel(), 30);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
