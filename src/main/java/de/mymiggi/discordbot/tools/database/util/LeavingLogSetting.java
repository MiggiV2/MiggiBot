package de.mymiggi.discordbot.tools.database.util;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LeavingLogSetting
{
	@Id
	private long timeStamp = System.currentTimeMillis();
	private long ChannelID;
	private long ServerID;

	public long getServerID()
	{
		return ServerID;
	}

	public LeavingLogSetting setServerID(long serverID)
	{
		ServerID = serverID;
		return this;
	}

	public long getChannelID()
	{
		return ChannelID;
	}

	public LeavingLogSetting setChannelID(long channelID)
	{
		ChannelID = channelID;
		return this;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public LeavingLogSetting setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
		return this;
	}
}
