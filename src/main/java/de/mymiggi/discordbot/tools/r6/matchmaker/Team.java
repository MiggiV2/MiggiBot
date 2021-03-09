package de.mymiggi.discordbot.tools.r6.matchmaker;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.discordbot.tools.r6.matchmaker.actions.AverageTeamSkillAction;

public class Team
{
	private double balanceMultiplier = 1;

	private List<R6Player> players = new ArrayList<R6Player>();

	public List<R6Player> getPlayers()
	{
		return players;
	}

	public void clearPlayers()
	{
		this.players.clear();
	}

	public void addPlayer(R6Player player)
	{
		this.players.add(player);
	}

	public void removePlaye(R6Player player)
	{
		this.players.remove(player);
	}

	public double getAverageSkillIndex()
	{
		return (new AverageTeamSkillAction().get(players) * this.balanceMultiplier);
	}

	public double getUnBalancedAverageSkillIndex()
	{
		return new AverageTeamSkillAction().get(players);
	}

	public double isBalanceMultiplier()
	{
		return balanceMultiplier;
	}

	public void setBalanceMultiplier(double balanceMultiplier)
	{
		this.balanceMultiplier = balanceMultiplier;
	}
}
