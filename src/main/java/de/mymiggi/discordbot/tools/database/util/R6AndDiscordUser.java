package de.mymiggi.discordbot.tools.database.util;

import javax.persistence.Entity;
import javax.persistence.Id;

import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.RankedRegions;

@Entity
public class R6AndDiscordUser
{
	@Id
	private long discordID;
	private String r6Name;
	private int platformID;
	private int rankedRegionID;

	public R6AndDiscordUser(long discordID, String r6Name, int platformID, int rankedRegionID)
	{
		this.discordID = discordID;
		this.r6Name = r6Name;
		this.platformID = platformID;
		this.setRankedRegionID(rankedRegionID);
	}

	public R6AndDiscordUser()
	{

	}

	public long getDiscordID()
	{
		return discordID;
	}

	public R6AndDiscordUser setDiscordID(long discordID)
	{
		this.discordID = discordID;
		return this;
	}

	public String getR6Name()
	{
		return r6Name;
	}

	public R6AndDiscordUser setR6Name(String r6Name)
	{
		this.r6Name = r6Name;
		return this;
	}

	public int getPlatformID()
	{
		return platformID;
	}

	public R6AndDiscordUser setPlatformID(int platformID)
	{
		this.platformID = platformID;
		return this;
	}

	public int getRankedRegionID()
	{
		return rankedRegionID;
	}

	public R6AndDiscordUser setRankedRegionID(int rankedRegionID)
	{
		this.rankedRegionID = rankedRegionID;
		return this;
	}

	public PlatfromType getPlatform()
	{
		if (this.platformID == 0)
		{
			return PlatfromType.UPLAY;
		}
		if (this.platformID == 1)
		{
			return PlatfromType.XBOX;
		}
		if (this.platformID == 2)
		{
			return PlatfromType.PLAYSTATION;
		}
		else
		{
			return null;
		}
	}

	public RankedRegions getRankedRegion()
	{
		if (this.rankedRegionID == 0)
		{
			return RankedRegions.EMEA;
		}
		if (this.rankedRegionID == 1)
		{
			return RankedRegions.NCSA;
		}
		if (this.rankedRegionID == 2)
		{
			return RankedRegions.APAC;
		}
		else
		{
			return null;
		}
	}
}
