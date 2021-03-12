package de.mymiggi.discordbot.server.r6.map.update.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.tools.database.util.R6Map;

public class ChangeSelectionEmbed
{
	public EmbedBuilder build(R6Map map)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Now select what to change")
			.setDescription(String.format("" +
				":one: Name = %s\r\n" +
				":two: ImageURL\r\n" +
				":three: isRanked = %s\r\n" +
				":wave: DELETE MAP",
				map.getName(), map.isRankedPool()))
			.setImage(map.getImageURL())
			.setColor(Color.GREEN);
		return embed;
	}
}
