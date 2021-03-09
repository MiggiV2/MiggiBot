package de.mymiggi.discordbot.server.untis.reminder.manager.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;

public class WrongSyntaxEmbed
{
	public EmbedBuilder build()
	{
		return new EmbedBuilder()
			.setTitle("Missing your role ID!")
			.setColor(Color.RED)
			.setDescription(BotMainCore.prefix + "addUntisReminder 746645343432867882");
	}
}
