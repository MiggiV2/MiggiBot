package de.mymiggi.discordbot.server.r6.map.update.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class DeleteConfirmEmbed
{
	public EmbedBuilder build()
	{
		return new EmbedBuilder()
			.setTitle("Are you really sure, you want to delete this map?")
			.setDescription("This can't be undone!")
			.setColor(Color.RED);
	}
}
