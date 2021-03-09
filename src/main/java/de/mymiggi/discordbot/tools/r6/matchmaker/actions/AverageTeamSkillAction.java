package de.mymiggi.discordbot.tools.r6.matchmaker.actions;

import java.util.List;

import de.mymiggi.discordbot.tools.r6.matchmaker.R6Player;

public class AverageTeamSkillAction
{
	public double get(List<R6Player> playerList)
	{
		int totalSkillIndex = 0;
		for (R6Player player : playerList)
		{
			totalSkillIndex += player.getSkillIndex();
		}
		if (playerList.size() == 0)
		{
			return 0;
		}
		return totalSkillIndex / playerList.size();
	}
}
