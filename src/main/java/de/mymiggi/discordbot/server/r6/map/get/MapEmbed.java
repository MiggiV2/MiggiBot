package de.mymiggi.discordbot.server.r6.map.get;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.tools.database.util.R6Map;

public class MapEmbed
{
	private List<R6Map> mapList;

	public MapEmbed(List<R6Map> mapList)
	{
		this.mapList = mapList;
	}

	public EmbedBuilder buildRandomRankedMap()
	{
		R6Map randomMap = getRandomRankedMap();
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(randomMap.getName())
			.setImage(randomMap.getImageURL())
			.setColor(Color.GREEN);
		return embed;
	}

	public EmbedBuilder buildRandomMap()
	{
		R6Map randomMap = getRandomMap();
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(randomMap.getName())
			.setImage(randomMap.getImageURL())
			.setColor(Color.GREEN);
		return embed;
	}

	private R6Map getRandomRankedMap()
	{
		List<R6Map> rankedMapList = new ArrayList<R6Map>();
		for (R6Map tempMap : mapList)
		{
			if (tempMap.isRankedPool())
			{
				rankedMapList.add(tempMap);
			}
		}
		int random = (int)(Math.random() * rankedMapList.size());
		R6Map randomMap = rankedMapList.get(random);
		return randomMap;
	}

	private R6Map getRandomMap()
	{
		int random = (int)(Math.random() * mapList.size());
		R6Map randomMap = mapList.get(random);
		return randomMap;
	}
}
