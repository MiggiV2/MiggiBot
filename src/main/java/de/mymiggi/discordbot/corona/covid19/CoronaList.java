package de.mymiggi.discordbot.corona.covid19;

public class CoronaList
{
	private CoronaResponse towWeeksAgo;
	private CoronaResponse lastWeek;
	private CoronaResponse today;
	private CoronaResponse yesterday;

	public CoronaResponse getTowWeeksAgo()
	{
		return towWeeksAgo;
	}

	public void setTowWeeksAgo(CoronaResponse towWeeksAgo)
	{
		this.towWeeksAgo = towWeeksAgo;
	}

	public CoronaResponse getLastWeek()
	{
		return lastWeek;
	}

	public void setLastWeek(CoronaResponse lastWeek)
	{
		this.lastWeek = lastWeek;
	}

	public CoronaResponse getToday()
	{
		return today;
	}

	public void setToday(CoronaResponse today)
	{
		this.today = today;
	}

	public CoronaResponse getYesterday()
	{
		return yesterday;
	}

	public void setYesterday(CoronaResponse yesterday)
	{
		this.yesterday = yesterday;
	}

}
