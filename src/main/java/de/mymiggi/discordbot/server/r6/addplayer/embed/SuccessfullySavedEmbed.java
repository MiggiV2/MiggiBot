package de.mymiggi.discordbot.server.r6.addplayer.embed;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class SuccessfullySavedEmbed
{
	public EmbedBuilder build()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Successfully saved your players!")
			.setColor(Color.GREEN);
		return embed;
	}
}
