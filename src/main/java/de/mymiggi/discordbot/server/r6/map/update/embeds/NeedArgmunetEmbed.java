package de.mymiggi.discordbot.server.r6.map.update.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;

public class NeedArgmunetEmbed
{
	public EmbedBuilder build()
	{
		return new EmbedBuilder()
			.setTitle("Sorry but I need a name!")
			.setDescription(BotMainCore.prefix + "updateMap KAFE")
			.setColor(Color.RED);
	}
}
