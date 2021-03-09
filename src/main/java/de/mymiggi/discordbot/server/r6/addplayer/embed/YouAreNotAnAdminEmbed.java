package de.mymiggi.discordbot.server.r6.addplayer.embed;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class YouAreNotAnAdminEmbed
{
	public EmbedBuilder build()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry, but onlay server admins can add new players!")
			.setColor(Color.RED);
		return embed;
	}
}
