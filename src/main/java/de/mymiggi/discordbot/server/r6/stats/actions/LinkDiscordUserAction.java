package de.mymiggi.discordbot.server.r6.stats.actions;

import java.util.List;

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
		List<R6AndDiscordUser> userList = client.getList(R6AndDiscordUser.class);
		String r6Name = interaction.getFirstOptionStringValue().orElse("NO_NAME");
		int platformID = interaction.getSecondOptionIntValue().orElse(0);
		long discordID = interaction.getUser().getId();
		int rankedRegionID = interaction.getThirdOptionIntValue().orElse(0);
		R6AndDiscordUser r6Player = new R6AndDiscordUser(discordID, r6Name, platformID, rankedRegionID);
		R6AndDiscordUser fromDB = getUserFromList(r6Player, userList);
		boolean status = (fromDB == null)
			? client.save(r6Player)
			: updateUser(r6Player, fromDB, client);
		if (status)
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

	private R6AndDiscordUser getUserFromList(R6AndDiscordUser user, List<R6AndDiscordUser> userList)
	{
		for (R6AndDiscordUser temp : userList)
		{
			if (temp.getDiscordID() == user.getDiscordID())
			{
				return temp;
			}
		}
		return null;
	}

	private boolean updateUser(R6AndDiscordUser newUser, R6AndDiscordUser oldUser, UniversalHibernateClient client)
	{
		oldUser
			.setPlatformID(newUser.getPlatformID())
			.setR6Name(newUser.getR6Name())
			.setRankedRegionID(newUser.getRankedRegionID());
		return client.update(oldUser);
	}
}
