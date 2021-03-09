package de.mymiggi.discordbot.server.r6.map.update.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class EnterUpdateEmbed
{
	public EmbedBuilder build(String updateType)
	{
		return new EmbedBuilder()
			.setTitle("Please enter your update for " + updateType)
			.setColor(Color.GREEN);
	}
}
