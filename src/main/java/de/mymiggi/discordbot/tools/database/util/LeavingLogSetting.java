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

	public void setServerID(long serverID)
	{
		ServerID = serverID;
	}

	public long getChannelID()
	{
		return ChannelID;
	}

	public void setChannelID(long channelID)
	{
		ChannelID = channelID;
	}
	
	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}
}
