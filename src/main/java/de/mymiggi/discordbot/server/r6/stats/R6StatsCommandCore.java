package de.mymiggi.discordbot.server.r6.stats;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6HighlightMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6RankedMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6StatsMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.helpers.AskForPlatformAction;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class R6StatsCommandCore
{
	private WrapperManager wrapperManager;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public R6StatsCommandCore()
	{
		if (BotMainCore.config.getEMail() != null && BotMainCore.config.getPassword() != null || BotMainCore.config.getCredential() != null)
		{
			this.wrapperManager = new WrapperManager();
		}
		else
		{
			logger.warn("No Ubisoft account in config!");
		}
	}

	public void runWeekly(MessageCreateEvent event, String[] context)
	{
		if (context.length != 2)
		{
			sendMissingUserName(event);
		}
		else if (wrapperManager == null)
		{
			sendNotInConfig(event);
		}
		else
		{
			String username = context[1];
			TextChannel channel = event.getChannel();
			event.addReactionsToMessage("👍");
			new AskForPlatformAction().run(channel, username, wrapperManager, new UpdateR6HighlightMessage());
		}
	}

	public void runRankedStats(MessageCreateEvent event, String[] context)
	{
		if (context.length != 2)
		{
			sendMissingUserName(event);
		}
		else if (wrapperManager == null)
		{
			sendNotInConfig(event);
		}
		else
		{
			String username = context[1];
			TextChannel channel = event.getChannel();
			event.addReactionsToMessage("👍");
			new AskForPlatformAction().run(channel, username, wrapperManager, new UpdateR6RankedMessage());
		}
	}

	public void runStats(MessageCreateEvent event, String[] context)
	{
		if (context.length != 2)
		{
			sendMissingUserName(event);
		}
		else if (wrapperManager == null)
		{
			sendNotInConfig(event);
		}
		else
		{
			String username = context[1];
			TextChannel channel = event.getChannel();
			event.addReactionsToMessage("👍");
			new AskForPlatformAction().run(channel, username, wrapperManager, new UpdateR6StatsMessage());
		}
	}

	private void sendNotInConfig(MessageCreateEvent event)
	{
		BotMainCore.api.getOwner().thenAccept(owner -> {
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but there is no Ubisoft account in my config!")
				.setDescription(String.format("Ask %s to set one!", owner.getName()))
				.setColor(Color.RED);
			event.getChannel()
				.sendMessage(embed);
		});
	}

	private void sendMissingUserName(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("You have to enter your username!")
			.setColor(Color.ORANGE);
		event.getChannel()
			.sendMessage(embed)
			.thenAccept(message -> {
				MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 12);
			});
	}
}
