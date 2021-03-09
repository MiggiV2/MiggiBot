package de.mymiggi.discordbot.server.r6.addplayer.embed;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class StartListeningEmbed
{
	public EmbedBuilder build()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("So! Now I'm listening!")
			.setDescription("Usage: DISCORD-USER-ID SKILL-INDEX\r\nOn your messsage, click :white_check_mark: to save!")
			.setColor(Color.GREEN);
		return embed;
	}
}
