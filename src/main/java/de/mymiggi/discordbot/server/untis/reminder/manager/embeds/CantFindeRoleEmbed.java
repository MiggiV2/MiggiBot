package de.mymiggi.discordbot.server.untis.reminder.manager.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class CantFindeRoleEmbed
{
	public EmbedBuilder build()
	{
		return new EmbedBuilder()
			.setTitle("Cant find this role!")
			.setDescription("Maybe a tipe mistake?")
			.setColor(Color.RED);
	}
}
