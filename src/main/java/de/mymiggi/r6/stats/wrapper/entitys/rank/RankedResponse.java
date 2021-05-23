package de.mymiggi.r6.stats.wrapper.entitys.rank;

import java.util.Map;

public class RankedResponse
{
	private Map<String, RankedStats> players;
	private RankedStats rankedStats;

	public RankedStats getStats()
	{
		this.players.forEach((playerID, stats) -> {
			rankedStats = stats;
		});
		return rankedStats;
	}

	public Map<String, RankedStats> getPlayers()
	{
		return players;
	}

	public void setPlayers(Map<String, RankedStats> players)
	{
		this.players = players;
	}
}
