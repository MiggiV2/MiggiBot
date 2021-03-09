package de.mymiggi.discordbot.server.untis.reminder.manager.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DoneEmbed
{
	public EmbedBuilder build(String channelName)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Successfuly added channel " + channelName)
			.setFooter("Btw, thx for using my bot! <3 Miggi");
		return embed;
	}
}
