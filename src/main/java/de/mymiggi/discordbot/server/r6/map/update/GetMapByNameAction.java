package de.mymiggi.discordbot.server.r6.map.update;

import java.util.List;

import de.mymiggi.discordbot.tools.database.util.R6Map;

public class GetMapByNameAction
{
	public R6Map run(List<R6Map> mapList, String requestedMap) throws Exception
	{
		for (R6Map temp : mapList)
		{
			if (temp.getName().equalsIgnoreCase(requestedMap))
			{
				return temp;
			}
		}
		throw new Exception(requestedMap + "not found!");
	}
}
