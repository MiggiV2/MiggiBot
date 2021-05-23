package de.mymiggi.r6.stats.wrapper.entitys.stats;

public class GameModeStats
{
	private int matchesPlayed;
	private int roundsPlayed;
	private int minutesPlayed;
	private int matchesWon;
	private int matchesLost;
	private int roundsWon;
	private int roundsLost;
	private int kills;
	private int assists;
	private int death;
	private int headshots;
	private int meleeKills;
	private int teamKills;
	private int openingKills;
	private int openingDeaths;
	private int trades;
	private int openingKillTrades;
	private int openingDeathTrades;
	private int distanceTravelled;
	private int revives;
	private double winLossRatio;
	private SpecialValue killDeathRatio;
	private SpecialValue headshotAccuracy;
	private SpecialValue killsPerRound;
	private SpecialValue roundsWithAKill;
	private SpecialValue roundsWithMultiKill;
	private SpecialValue roundsWithOpeningKill;
	private SpecialValue roundsWithOpeningDeath;
	private SpecialValue roundsWithKOST;
	private SpecialValue roundsSurvived;
	private SpecialValue roundsWithAnAce;
	private SpecialValue roundsWithClutch;
	private double timeAlivePerMatch;
	private double timeDeadPerMatch;
	private double distancePerRound;

	public int getMatchesPlayed()
	{
		return matchesPlayed;
	}

	public void setMatchesPlayed(int matchesPlayed)
	{
		this.matchesPlayed = matchesPlayed;
	}

	public int getRoundsPlayed()
	{
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed)
	{
		this.roundsPlayed = roundsPlayed;
	}

	public int getMinutesPlayed()
	{
		return minutesPlayed;
	}

	public void setMinutesPlayed(int minutesPlayed)
	{
		this.minutesPlayed = minutesPlayed;
	}

	public int getMatchesWon()
	{
		return matchesWon;
	}

	public void setMatchesWon(int matchesWon)
	{
		this.matchesWon = matchesWon;
	}

	public int getMatchesLost()
	{
		return matchesLost;
	}

	public void setMatchesLost(int matchesLost)
	{
		this.matchesLost = matchesLost;
	}

	public int getRoundsWon()
	{
		return roundsWon;
	}

	public void setRoundsWon(int roundsWon)
	{
		this.roundsWon = roundsWon;
	}

	public int getRoundsLost()
	{
		return roundsLost;
	}

	public void setRoundsLost(int roundsLost)
	{
		this.roundsLost = roundsLost;
	}

	public int getKills()
	{
		return kills;
	}

	public void setKills(int kills)
	{
		this.kills = kills;
	}

	public int getAssists()
	{
		return assists;
	}

	public void setAssists(int assists)
	{
		this.assists = assists;
	}

	public int getDeath()
	{
		return death;
	}

	public void setDeath(int death)
	{
		this.death = death;
	}

	public int getHeadshots()
	{
		return headshots;
	}

	public void setHeadshots(int headshots)
	{
		this.headshots = headshots;
	}

	public int getMeleeKills()
	{
		return meleeKills;
	}

	public void setMeleeKills(int meleeKills)
	{
		this.meleeKills = meleeKills;
	}

	public int getTeamKills()
	{
		return teamKills;
	}

	public void setTeamKills(int teamKills)
	{
		this.teamKills = teamKills;
	}

	public int getOpeningKills()
	{
		return openingKills;
	}

	public void setOpeningKills(int openingKills)
	{
		this.openingKills = openingKills;
	}

	public int getOpeningDeaths()
	{
		return openingDeaths;
	}

	public void setOpeningDeaths(int openingDeaths)
	{
		this.openingDeaths = openingDeaths;
	}

	public int getTrades()
	{
		return trades;
	}

	public void setTrades(int trades)
	{
		this.trades = trades;
	}

	public int getOpeningKillTrades()
	{
		return openingKillTrades;
	}

	public void setOpeningKillTrades(int openingKillTrades)
	{
		this.openingKillTrades = openingKillTrades;
	}

	public int getOpeningDeathTrades()
	{
		return openingDeathTrades;
	}

	public void setOpeningDeathTrades(int openingDeathTrades)
	{
		this.openingDeathTrades = openingDeathTrades;
	}

	public int getDistanceTravelled()
	{
		return distanceTravelled;
	}

	public void setDistanceTravelled(int distanceTravelled)
	{
		this.distanceTravelled = distanceTravelled;
	}

	public int getRevives()
	{
		return revives;
	}

	public void setRevives(int revives)
	{
		this.revives = revives;
	}

	public double getWinLossRatio()
	{
		return winLossRatio;
	}

	public void setWinLossRatio(double winLossRatio)
	{
		this.winLossRatio = winLossRatio;
	}

	public double getDistancePerRound()
	{
		return distancePerRound;
	}

	public void setDistancePerRound(double distancePerRound)
	{
		this.distancePerRound = distancePerRound;
	}

	public double getTimeDeadPerMatch()
	{
		return timeDeadPerMatch;
	}

	public void setTimeDeadPerMatch(double timeDeadPerMatch)
	{
		this.timeDeadPerMatch = timeDeadPerMatch;
	}

	public double getTimeAlivePerMatch()
	{
		return timeAlivePerMatch;
	}

	public void setTimeAlivePerMatch(double timeAlivePerMatch)
	{
		this.timeAlivePerMatch = timeAlivePerMatch;
	}

	public double getKillDeathRatio()
	{
		return killDeathRatio.getValue();
	}

	public void setKillDeathRatio(SpecialValue killDeathRatio)
	{
		this.killDeathRatio = killDeathRatio;
	}

	public double getHeadshotAccuracy()
	{
		return headshotAccuracy.getValue();
	}

	public void setHeadshotAccuracy(SpecialValue headshotAccuracy)
	{
		this.headshotAccuracy = headshotAccuracy;
	}

	public double getKillsPerRound()
	{
		return killsPerRound.getValue();
	}

	public void setKillsPerRound(SpecialValue killsPerRound)
	{
		this.killsPerRound = killsPerRound;
	}

	public double getRoundsWithAKill()
	{
		return roundsWithAKill.getValue();
	}

	public void setRoundsWithAKill(SpecialValue roundsWithAKill)
	{
		this.roundsWithAKill = roundsWithAKill;
	}

	public double getRoundsWithMultiKill()
	{
		return roundsWithMultiKill.getValue();
	}

	public void setRoundsWithMultiKill(SpecialValue roundsWithMultiKill)
	{
		this.roundsWithMultiKill = roundsWithMultiKill;
	}

	public double getRoundsWithOpeningKill()
	{
		return roundsWithOpeningKill.getValue();
	}

	public void setRoundsWithOpeningKill(SpecialValue roundsWithOpeningKill)
	{
		this.roundsWithOpeningKill = roundsWithOpeningKill;
	}

	public double getRoundsWithOpeningDeath()
	{
		return roundsWithOpeningDeath.getValue();
	}

	public void setRoundsWithOpeningDeath(SpecialValue roundsWithOpeningDeath)
	{
		this.roundsWithOpeningDeath = roundsWithOpeningDeath;
	}

	public double getRoundsWithKOST()
	{
		return roundsWithKOST.getValue();
	}

	public void setRoundsWithKOST(SpecialValue roundsWithKOST)
	{
		this.roundsWithKOST = roundsWithKOST;
	}

	public double getRoundsSurvived()
	{
		return roundsSurvived.getValue();
	}

	public void setRoundsSurvived(SpecialValue roundsSurvived)
	{
		this.roundsSurvived = roundsSurvived;
	}

	public double getRoundsWithAnAce()
	{
		return roundsWithAnAce.getValue();
	}

	public void setRoundsWithAnAce(SpecialValue roundsWithAnAce)
	{
		this.roundsWithAnAce = roundsWithAnAce;
	}

	public double getRoundsWithClutch()
	{
		return roundsWithClutch.getValue();
	}

	public void setRoundsWithClutch(SpecialValue roundsWithClutch)
	{
		this.roundsWithClutch = roundsWithClutch;
	}
}

class SpecialValue
{
	private double value;

	public double getValue()
	{
		return value;
	}

	public void setValue(double value)
	{
		this.value = value;
	}
}
