package de.mymiggi.discordbot.server.r6.stats.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;

public class R6HighlightEmbed
{
	public EmbedBuilder build(String[] lines, PlayerIDResponse userProfile)
	{
		String highlight = "";
		for (String tempLine : lines)
		{
			highlight += tempLine + "\r\n";
		}
		return new EmbedBuilder()
			.setTitle("Your weekly highlight!")
			.setThumbnail(userProfile.get().getProfilePrictureURL())
			.setDescription(highlight)
			.setColor(Color.GREEN);
	}
}
