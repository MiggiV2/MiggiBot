package de.mymiggi.discordbot.server.r6.addplayer.embed;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ClosedEmbed
{
	public EmbedBuilder build()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Saving your new players")
			.setColor(Color.ORANGE);
		return embed;
	}
}
