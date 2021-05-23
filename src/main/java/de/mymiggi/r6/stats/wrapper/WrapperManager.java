package de.mymiggi.r6.stats.wrapper;

import java.io.IOException;

import de.mymiggi.r6.stats.wrapper.actions.GetAccountInfos;
import de.mymiggi.r6.stats.wrapper.actions.GetPlayerIDAction;
import de.mymiggi.r6.stats.wrapper.actions.GetRankAction;
import de.mymiggi.r6.stats.wrapper.actions.GetStatsAction;
import de.mymiggi.r6.stats.wrapper.actions.GetWeeklyHighlightAction;
import de.mymiggi.r6.stats.wrapper.actions.LoginAction;
import de.mymiggi.r6.stats.wrapper.entitys.LoginResponse;
import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;
import de.mymiggi.r6.stats.wrapper.entitys.rank.RankedResponse;
import de.mymiggi.r6.stats.wrapper.entitys.stats.SmartStatsResponse;
import de.mymiggi.r6.stats.wrapper.entitys.weekly.highlight.WeeklyHighlightResponse;

public class WrapperManager
{
	private LoginResponse loginResponse = new LoginAction().runMaybeNull();

	public PlayerIDResponse getPlayerID(PlatfromType platformType, String playerName) throws IOException
	{
		checkIfExpired();
		return new GetPlayerIDAction().run(loginResponse, platformType, playerName);
	}

	public SmartStatsResponse getStats(PlatfromType platfromType, String playerID) throws IOException
	{
		checkIfExpired();
		return new GetStatsAction().run(loginResponse, platfromType, playerID);
	}

	public RankedResponse getRankedStats(RankedRegions regions, String playerID) throws IOException
	{
		checkIfExpired();
		return new GetRankAction().run(loginResponse, playerID, regions);
	}

	public WeeklyHighlightResponse getWeeklyHighlight(String playerID) throws IOException
	{
		checkIfExpired();
		return new GetWeeklyHighlightAction().run(loginResponse, playerID);
	}

	public PlayerIDResponse getAccountConnections(String playerID) throws IOException
	{
		checkIfExpired();
		return new GetAccountInfos().connectedAccs(loginResponse, playerID);
	}

	private void checkIfExpired()
	{
		if (loginResponse.isExpired())
		{
			loginResponse = new LoginAction().runMaybeNull();
		}
	}
}
