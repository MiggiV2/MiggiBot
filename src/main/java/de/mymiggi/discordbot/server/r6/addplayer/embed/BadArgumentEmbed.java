package de.mymiggi.discordbot.server.r6.addplayer.embed;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;

public class BadArgumentEmbed
{
	public EmbedBuilder build()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry, but you have to enter the DiscordUserID + skillIndex")
			.setDescription(BotMainCore.prefix + "309696934174785556 10")
			.setColor(Color.ORANGE);
		return embed;
	}
}
