package de.mymiggi.discordbot.tools.r6.matchmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.util.RainbowSixPlayer;
import de.mymiggi.discordbot.tools.r6.matchmaker.actions.AverageTeamSkillAction;

public class MatchMakerCore
{
	private int skillDifferencAim = 0;
	private int playerDifferenceAim = 0;
	private List<Team> lowestResult = null;

	private static Logger logger = LoggerFactory.getLogger(MatchMakerCore.class.getSimpleName());

	public List<Team> runUsingR6Player(List<RainbowSixPlayer> playerR6)
	{
		List<List<Team>> results = new ArrayList<List<Team>>();
		tryToBuild(buildMap(playerR6), 0, results);
		return getLowestTry(results);
	}

	public List<Team> run(Map<String, Integer> playerMap)
	{
		List<List<Team>> results = new ArrayList<List<Team>>();
		tryToBuild(playerMap, 0, results);
		return getLowestTry(results);
	}

	private List<Team> getLowestTry(List<List<Team>> results)
	{
		while (lowestResult == null)
		{
			results
				.stream()
				.filter(result -> getPlayerDifference(result) == playerDifferenceAim && maxFifePlayerCheck(result))
				.forEach(result -> {
					balancePlayerDifference(result);
					if (getSkillDifferenc(lowestResult) >= getSkillDifferenc(result))
					{
						lowestResult = result;
					}
				});
			playerDifferenceAim++;
		}

		return lowestResult;
	}

	private void balancePlayerDifference(List<Team> teams)
	{
		/*
		 * Balance one team is enough
		 */
		double teamOneSize = teams.get(0).getPlayers().size();
		double teamTwoSize = teams.get(1).getPlayers().size();
		double balanceMultiplier = (teamOneSize / teamTwoSize);
		teams.get(0).setBalanceMultiplier(balanceMultiplier);
		logger.info("balanceMultiplier: " + balanceMultiplier);
	}

	private double getSkillDifferenc(List<Team> teams)
	{
		if (teams == null)
		{
			return 10;
		}
		return Math.abs(teams.get(0).getAverageSkillIndex() - teams.get(1).getAverageSkillIndex());
	}

	private int getPlayerDifference(List<Team> teams)
	{
		int playerSizeBlue = teams.get(0).getPlayers().size();
		int playerSizeOrange = teams.get(1).getPlayers().size();
		return Math.abs(playerSizeBlue - playerSizeOrange);
	}

	private boolean maxFifePlayerCheck(List<Team> teams)
	{
		int playerSizeBlue = teams.get(0).getPlayers().size();
		int playerSizeOrange = teams.get(1).getPlayers().size();

		return playerSizeBlue < 6 && playerSizeOrange < 6;
	}

	private void tryToBuild(Map<String, Integer> playerMap, int tryCounter, List<List<Team>> results)
	{
		List<R6Player> playerList = buildPlayerList(playerMap);
		List<Team> teams = buildTeams(playerList);
		if (getSkillDifferenc(teams) > skillDifferencAim)
		{
			tryCounter++;
			if (tryCounter != 100 && results.size() < 1000)
			{
				skillDifferencAim++;
				results.add(teams);
				tryToBuild(playerMap, tryCounter, results);
			}
		}
		else if (results.size() < 200)
		{
			skillDifferencAim = 0;
			results.add(teams);
			tryToBuild(playerMap, tryCounter, results);
		}
	}

	private List<R6Player> buildPlayerList(Map<String, Integer> playerMap)
	{
		List<R6Player> playerList = new ArrayList<R6Player>();
		playerMap.forEach((name, skillIndex) -> {
			playerList.add(new R6Player(name, skillIndex));
		});
		return playerList;
	}

	private List<Team> buildTeams(List<R6Player> playerList)
	{
		List<Team> teams = new ArrayList<Team>();

		Team teamBlue = new Team();
		Team teamOrange = new Team();

		double avagerTeamSkillIndex = new AverageTeamSkillAction().get(playerList);
		int totalTryToAdd = 0;
		while (!playerList.isEmpty() && totalTryToAdd < 1000)
		{
			addPlayerToTeam(teamBlue, playerList, totalTryToAdd, avagerTeamSkillIndex);
			addPlayerToTeam(teamOrange, playerList, totalTryToAdd, avagerTeamSkillIndex);
			totalTryToAdd++;
		}
		teams.add(teamOrange);
		teams.add(teamBlue);
		return teams;
	}

	private void addPlayerToTeam(Team team, List<R6Player> playerList, int failCounter, double avagerTeamSkillIndex)
	{
		if (!playerList.isEmpty())
		{
			int randomIndex = (int)(playerList.size() * Math.random());
			R6Player currentPlayer = playerList.get(randomIndex);
			if (team.getAverageSkillIndex() + currentPlayer.getSkillIndex() < avagerTeamSkillIndex + skillDifferencAim)
			{
				team.addPlayer(currentPlayer);
				playerList.remove(randomIndex);
			}
			else
			{
				failCounter++;
				if (failCounter < 10 && !team.getPlayers().isEmpty())
				{
					R6Player lastAddedPlayer = team.getPlayers().get(team.getPlayers().size() - 1);
					team.removePlaye(lastAddedPlayer);
					playerList.add(lastAddedPlayer);
					addPlayerToTeam(team, playerList, failCounter, avagerTeamSkillIndex);
				}
				else if (!team.getPlayers().isEmpty())
				{
					skillDifferencAim++;
					R6Player lastAddedPlayer = team.getPlayers().get(team.getPlayers().size() - 1);
					team.removePlaye(lastAddedPlayer);
					playerList.add(lastAddedPlayer);
					addPlayerToTeam(team, playerList, failCounter, avagerTeamSkillIndex);
				}
				else
				{
					skillDifferencAim++;
					addPlayerToTeam(team, playerList, failCounter, avagerTeamSkillIndex);
				}
			}
		}
	}

	private Map<String, Integer> buildMap(List<RainbowSixPlayer> playerR6)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		playerR6.forEach(player -> {
			BotMainCore.api.getUserById(player.getId()).thenAccept(user -> {
				map.put(user.getName(), player.getSkillIndex());
			});
		});
		return map;
	}
}
