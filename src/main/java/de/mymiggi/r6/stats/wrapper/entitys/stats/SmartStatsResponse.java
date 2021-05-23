package de.mymiggi.r6.stats.wrapper.entitys.stats;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmartStatsResponse
{
	private GameModeStats casualStats;
	private GameModeStats allStats;
	private GameModeStats rankedStats;
	private GameModeStats unrankedStats;
	private String profileId;
	private String startDate;
	private String endDate;

	public SmartStatsResponse(StatsResponse response)
	{
		response.getPlatforms().forEach((playfrom, value1) -> {
			response.getPlatforms().get(playfrom).getGameModes().forEach((gameMode, value2) -> {
				GameModeStats foundGameModeStats = response.getPlatforms()
					.get(playfrom).getGameModes()
					.get(gameMode).getTeamRoles()
					.get("all")[0];
				if (gameMode.equals("all"))
				{
					this.allStats = foundGameModeStats;
				}
				if (gameMode.equals("casual"))
				{
					this.casualStats = foundGameModeStats;
				}
				if (gameMode.equals("ranked"))
				{
					this.rankedStats = foundGameModeStats;
				}
				if (gameMode.equals("unranked"))
				{
					this.unrankedStats = foundGameModeStats;
				}
			});
		});
		this.profileId = response.getProfileId();
		this.setStartDate(response.getStartDate());
		this.setEndDate(response.getEndDate());
	}

	public GameModeStats getCasualStats()
	{
		return casualStats;
	}

	public void setCasualStats(GameModeStats casualStats)
	{
		this.casualStats = casualStats;
	}

	public GameModeStats getAllStats()
	{
		return allStats;
	}

	public void setAllStats(GameModeStats allStats)
	{
		this.allStats = allStats;
	}

	public GameModeStats getRankedStats()
	{
		return rankedStats;
	}

	public void setRankedStats(GameModeStats rankedStats)
	{
		this.rankedStats = rankedStats;
	}

	public GameModeStats getUnrankedStats()
	{
		return unrankedStats;
	}

	public void setUnrankedStats(GameModeStats unrankedStats)
	{
		this.unrankedStats = unrankedStats;
	}

	public String getProfileId()
	{
		return profileId;
	}

	public void setProfileId(String profileId)
	{
		this.profileId = profileId;
	}

	public String getEndDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d yyyy");
		try
		{
			Date date = sdf.parse(endDate);
			return sdf2.format(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return "UNKNOWN";
		}
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getStartDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MMM d yyyy");
		try
		{
			Date date = sdf.parse(startDate);
			return sdf2.format(date);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return "UNKNOWN";
		}
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
}
