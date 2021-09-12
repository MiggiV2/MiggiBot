package de.mymiggi.discordbot.server.r6.stats.actions;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6AndDiscordUser;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class LinkDiscordUserAction
{
	public void run(SlashCommandCreateEvent event, UniversalHibernateClient client)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		String r6Name = interaction.getFirstOptionStringValue().orElse("NO_NAME");
		int platformID = interaction.getSecondOptionIntValue().orElse(0);
		long discordID = interaction.getUser().getId();
		int rankedRegionID = interaction.getThirdOptionIntValue().orElse(0);
		R6AndDiscordUser r6Player = new R6AndDiscordUser(discordID, r6Name, platformID, rankedRegionID);
		if (client.save(r6Player))
		{
			interaction.createFollowupMessageBuilder()
				.setContent("Saved! " + Emojis.GREEN_CHECK.getEmoji())
				.send()
				.thenAccept(message -> MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 12));
		}
		else
		{
			interaction.createFollowupMessageBuilder()
				.setContent("Failed to save! Sry")
				.send()
				.thenAccept(message -> MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 12));
		}
	}
}
