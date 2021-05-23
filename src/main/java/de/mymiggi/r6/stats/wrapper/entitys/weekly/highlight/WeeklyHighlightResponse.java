package de.mymiggi.r6.stats.wrapper.entitys.weekly.highlight;

import java.util.Map;

public class WeeklyHighlightResponse
{
	private Map<String, WrappedWrappedStats> profiles;
	private HighlightStats highlightStats;
	public HighlightStats getStats()
	{
		/*
		 * Go throw every map, till we reached the right value
		 */
		this.profiles.forEach((playerID, wrappedWrappedStats) -> {
			wrappedWrappedStats.getYears().forEach((year, week) -> {
				week.getWeeks().forEach((weekInYear, stats) -> {
					highlightStats = stats;
				});
			});
		});
		return highlightStats;
	}
}

class WrappedWrappedStats
{
	private Map<String, WrappedHighlightStats> years;

	public Map<String, WrappedHighlightStats> getYears()
	{
		return years;
	}

	public void setYears(Map<String, WrappedHighlightStats> years)
	{
		this.years = years;
	}
}

class WrappedHighlightStats
{
	private Map<String, HighlightStats> weeks;

	public Map<String, HighlightStats> getWeeks()
	{
		return weeks;
	}

	public void setWeeks(Map<String, HighlightStats> weeks)
	{
		this.weeks = weeks;
	}

}