package de.mymiggi.discordbot.server.r6.stats;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.discordbot.server.r6.stats.actions.LinkDiscordUserAction;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6HighlightMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6RankedMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.UpdateR6StatsMessage;
import de.mymiggi.discordbot.server.r6.stats.actions.helpers.AskForPlatformAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6AndDiscordUser;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class R6StatsCommandCore
{
	private WrapperManager wrapperManager;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private Map<Long, R6AndDiscordUser> userMap = new HashMap<Long, R6AndDiscordUser>();

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

	public void linkDiscordUser(SlashCommandCreateEvent event)
	{
		new LinkDiscordUserAction().run(event, client);
	}

	private void runAbstract(SlashCommandCreateEvent event, AbstractUpdateR6MessageAction action)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		if (wrapperManager == null)
		{
			sendNoConfig(event);
		}
		else
		{
			interaction.respondLater();
			loadUserMap();
			if (interaction.getFirstOptionStringValue().isPresent() || userMap.containsKey(interaction.getUser().getId()))
			{
				if (interaction.getFirstOptionStringValue().isPresent())
				{
					String userName = interaction.getFirstOptionStringValue().orElse("NO_NAME");
					new AskForPlatformAction().run(interaction, userName, wrapperManager, action);
				}
				else
				{
					R6AndDiscordUser user = userMap.get(interaction.getUser().getId());
					new AskForPlatformAction().skipAsking(interaction, user, wrapperManager, action);
				}
			}
			else
			{
				noR6UserFound(interaction);
			}
		}
	}

	private void noR6UserFound(SlashCommandInteraction interaction)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("No link R6 user found for id " + interaction.getId())
			.setDescription("Sorry, " + interaction.getUser().getName())
			.setColor(Color.RED);
		interaction.createFollowupMessageBuilder()
			.addEmbed(embed)
			.send();
	}

	private void loadUserMap()
	{
		List<R6AndDiscordUser> userList = client.getList(R6AndDiscordUser.class);
		userList.forEach(user -> userMap.put(user.getDiscordID(), user));
	}

	private void sendNoConfig(SlashCommandCreateEvent event)
	{
		event.getSlashCommandInteraction()
			.createImmediateResponder()
			.setContent("Sorry, but there is no Ubisoft account in my config!")
			.respond();
	}
}
