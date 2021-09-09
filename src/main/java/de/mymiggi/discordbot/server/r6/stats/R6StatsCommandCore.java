package de.mymiggi.discordbot.server.r6.stats;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6HighlightMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6RankedMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6StatsMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.helpers.AskForPlatformAction;
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

	public void runWeekly(SlashCommandCreateEvent event)
	{
		runAbstract(event, new UpdateR6HighlightMessage());
	}

	public void runRankedStats(SlashCommandCreateEvent event)
	{
		runAbstract(event, new UpdateR6RankedMessage());
	}

	public void runStats(SlashCommandCreateEvent event)
	{
		runAbstract(event, new UpdateR6StatsMessage());
	}

	private void runAbstract(SlashCommandCreateEvent event, AbstractUpdateR6MessageAction action)
	{
		if (wrapperManager == null)
		{
			sendNotInConfig(event);
		}
		else
		{
			SlashCommandInteraction interaction = event.getSlashCommandInteraction();
			String username = interaction.getFirstOptionStringValue().orElse("NO_NAME");
			interaction.createImmediateResponder().setContent("Have fun ;D").respond();
			interaction.getChannel().ifPresent(channel -> {
				new AskForPlatformAction().run(channel, username, wrapperManager, action);
			});
		}
	}

	private void sendNotInConfig(SlashCommandCreateEvent event)
	{
		event.getSlashCommandInteraction()
			.createImmediateResponder()
			.setContent("Sorry, but there is no Ubisoft account in my config!")
			.respond();
	}
}
