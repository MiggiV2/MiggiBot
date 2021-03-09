package de.mymiggi.discordbot.server.r6.map.update.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class SavedEmbed
{
	public EmbedBuilder build()
	{
		return new EmbedBuilder()
			.setTitle("Successful saved")
			.setColor(Color.GREEN);
	}
}
