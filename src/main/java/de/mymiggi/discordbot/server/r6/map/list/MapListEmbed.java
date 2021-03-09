package de.mymiggi.discordbot.server.r6.map.list;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.tools.database.util.R6Map;

public class MapListEmbed
{
	public EmbedBuilder build(List<R6Map> mapList)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle(String.format("All %s maps", mapList.size()))
			.setColor(Color.BLUE);
		for (R6Map temp : mapList)
		{
			embed.addInlineField(temp.getName(), "Is Ranked map: " + temp.isRankedPool());
		}
		return embed;
	}
}
