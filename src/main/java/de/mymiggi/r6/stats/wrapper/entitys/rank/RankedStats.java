package de.mymiggi.r6.stats.wrapper.entitys.rank;

import java.text.DecimalFormat;

import de.mymiggi.r6.stats.wrapper.actions.helper.GetRankedNameByID;

public class RankedStats
{
	private int wins;
	private int losses;
	private double max_mmr;
	private double mmr;
	private double last_match_mmr_change;
	private int deaths;
	private int kills;
	private double next_rank_mmr;
	private int rank;
	private int max_rank;
	private double skill_stdev;
	private double last_match_skill_stdev_change;
	private int abandons;
	private int season;
	private int top_rank_position;
	private double last_match_skill_mean_change;
	private double previous_rank_mmr;
	private int last_match_result;
	private double skill_mean;
	private String region;
	private String profile_id;
	private String update_time;

	public String getRankName()
	{
		try
		{
			return new GetRankedNameByID().get(rank).name();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "NOT FOUND!";
		}
	}

	public String getKDStr()
	{
		return new DecimalFormat("#0.000").format((double)kills / deaths);
	}

	public double getKD()
	{
		return (double)kills / deaths;
	}

	public String getWinsAndLossesRation()
	{
		return new DecimalFormat("#0.000").format((double)wins / losses);
	}

	public int getWins()
	{
		return wins;
	}

	public void setWins(int wins)
	{
		this.wins = wins;
	}

	public int getLosses()
	{
		return losses;
	}

	public void setLosses(int losses)
	{
		this.losses = losses;
	}

	public int getMax_mmr()
	{
		return (int)max_mmr;
	}

	public void setMax_mmr(double max_mmr)
	{
		this.max_mmr = max_mmr;
	}

	public int getMmr()
	{
		return (int)mmr;
	}

	public void setMmr(double mmr)
	{
		this.mmr = mmr;
	}

	public double getLast_match_mmr_change()
	{
		return last_match_mmr_change;
	}

	public void setLast_match_mmr_change(double last_match_mmr_change)
	{
		this.last_match_mmr_change = last_match_mmr_change;
	}

	public int getDeaths()
	{
		return deaths;
	}

	public void setDeaths(int deaths)
	{
		this.deaths = deaths;
	}

	public int getKills()
	{
		return kills;
	}

	public void setKills(int kills)
	{
		this.kills = kills;
	}

	public double getNext_rank_mmr()
	{
		return next_rank_mmr;
	}

	public void setNext_rank_mmr(double next_rank_mmr)
	{
		this.next_rank_mmr = next_rank_mmr;
	}

	public int getRank()
	{
		return rank;
	}

	public void setRank(int rank)
	{
		this.rank = rank;
	}

	public int getMax_rank()
	{
		return max_rank;
	}

	public void setMax_rank(int max_rank)
	{
		this.max_rank = max_rank;
	}

	public double getSkill_stdev()
	{
		return skill_stdev;
	}

	public void setSkill_stdev(double skill_stdev)
	{
		this.skill_stdev = skill_stdev;
	}

	public double getLast_match_skill_stdev_change()
	{
		return last_match_skill_stdev_change;
	}

	public void setLast_match_skill_stdev_change(double last_match_skill_stdev_change)
	{
		this.last_match_skill_stdev_change = last_match_skill_stdev_change;
	}

	public int getAbandons()
	{
		return abandons;
	}

	public void setAbandons(int abandons)
	{
		this.abandons = abandons;
	}

	public int getSeason()
	{
		return season;
	}

	public void setSeason(int season)
	{
		this.season = season;
	}

	public int getTop_rank_position()
	{
		return top_rank_position;
	}

	public void setTop_rank_position(int top_rank_position)
	{
		this.top_rank_position = top_rank_position;
	}

	public double getLast_match_skill_mean_change()
	{
		return last_match_skill_mean_change;
	}

	public void setLast_match_skill_mean_change(double last_match_skill_mean_change)
	{
		this.last_match_skill_mean_change = last_match_skill_mean_change;
	}

	public double getPrevious_rank_mmr()
	{
		return previous_rank_mmr;
	}

	public void setPrevious_rank_mmr(double previous_rank_mmr)
	{
		this.previous_rank_mmr = previous_rank_mmr;
	}

	public int getLast_match_result()
	{
		return last_match_result;
	}

	public void setLast_match_result(int last_match_result)
	{
		this.last_match_result = last_match_result;
	}

	public double getSkill_mean()
	{
		return skill_mean;
	}

	public void setSkill_mean(double skill_mean)
	{
		this.skill_mean = skill_mean;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public String getProfile_id()
	{
		return profile_id;
	}

	public void setProfile_id(String profile_id)
	{
		this.profile_id = profile_id;
	}

	public String getUpdate_time()
	{
		return update_time;
	}

	public void setUpdate_time(String update_time)
	{
		this.update_time = update_time;
	}
}
