package de.mymiggi.r6.stats.wrapper.entitys.weekly.highlight;

public class HighlightStats
{
	private String[] narrative;
	private BestMatchFullStatisticsResponse bestMatchFullStatistics;
	private WeeklyFullPlayerStatisticsResponse weeklyFullPlayerStatistics;

	public WeeklyFullPlayerStatisticsResponse getWeeklyFullPlayerStatistics()
	{
		return weeklyFullPlayerStatistics;
	}

	public void setWeeklyFullPlayerStatistics(WeeklyFullPlayerStatisticsResponse weeklyFullPlayerStatistics)
	{
		this.weeklyFullPlayerStatistics = weeklyFullPlayerStatistics;
	}

	public BestMatchFullStatisticsResponse getBestMatchFullStatistics()
	{
		return bestMatchFullStatistics;
	}

	public void setBestMatchFullStatistics(BestMatchFullStatisticsResponse bestMatchFullStatistics)
	{
		this.bestMatchFullStatistics = bestMatchFullStatistics;
	}

	public String[] getNarrative()
	{
		return narrative;
	}

	public void setNarrative(String[] narrative)
	{
		this.narrative = narrative;
	}
}
