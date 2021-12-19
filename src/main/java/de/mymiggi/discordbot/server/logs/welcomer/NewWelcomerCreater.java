package de.mymiggi.discordbot.server.logs.welcomer;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.Permissions;

@Deprecated
public class NewWelcomerCreater
{
	private Logger logger = LoggerFactory.getLogger(NewWelcomerCreater.class);

	public void add(MessageCreateEvent event, String[] context)
	{
		ServerAndChannelsLoader loader = new ServerAndChannelsLoader();
		WelcomerRunner welcomer = BotMainCore.getWelcomer();
		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 5);
		if (!Permissions.isAdmin(event))
		{
			notAdminEmbed(event);
			logger.info(event.getMessageAuthor().getName() + " try to use command, but he is not an admin!");
			return;
		}
		if (context.length != 2)
		{
			badInput(event);
			return;
		}
		try
		{
			loader.buildJsonFile(event, context[1]);
			welcomer.syncHashMap();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void badInput(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Miss something!")
			.setDescription(String.format("%swelcome RoleID", BotMainCore.prefix))
			.addField("Example:", BotMainCore.prefix + "welcome 746645343432867882");

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

	public void done(MessageCreateEvent event, String channelName)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Successfully added channel " + channelName)
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

	private EmbedBuilder getNotAdminEmbed(User user)
	{
		return new EmbedBuilder()
			.setTitle("You are not an admin " + user.getName() + "!")
			.setFooter("Maybe u can aske the owner?")
			.setColor(Color.RED);
	}

	private void notAdminEmbed(MessageCreateEvent event)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(getNotAdminEmbed(event.getMessageAuthor().asUser().get()));
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
