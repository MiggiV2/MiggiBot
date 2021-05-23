package de.mymiggi.r6.stats.wrapper.entitys.stats;

import java.util.Map;

public class StatsResponse
{
	private Map<String, WrappedGameModes> platforms;
	private String profileId;
	private String startDate;
	private String endDate;

	public String getProfileId()
	{
		return profileId;
	}

	public void setProfileId(String profileId)
	{
		this.profileId = profileId;
	}

	public Map<String, WrappedGameModes> getPlatforms()
	{
		return platforms;
	}

	public void setPlatforms(Map<String, WrappedGameModes> platforms)
	{
		this.platforms = platforms;
	}

	public SmartStatsResponse getSmartResponse()
	{
		return new SmartStatsResponse(this);
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}
}

class WrappedGameModes
{
	private Map<String, TeamRoles> gameModes;

	public Map<String, TeamRoles> getGameModes()
	{
		return gameModes;
	}

	public void setGameModes(Map<String, TeamRoles> gameModes)
	{
		this.gameModes = gameModes;
	}
}

class TeamRoles
{
	private Map<String, GameModeStats[]> teamRoles;

	public Map<String, GameModeStats[]> getTeamRoles()
	{
		return teamRoles;
	}

	public void setTeamRoles(Map<String, GameModeStats[]> teamRoles)
	{
		this.teamRoles = teamRoles;
	}
}
