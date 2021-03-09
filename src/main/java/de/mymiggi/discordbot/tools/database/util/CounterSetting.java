package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CounterSetting implements Serializable
{
	@Id
	private long timeStamp = System.currentTimeMillis();
	private static final long serialVersionUID = 11L;
	private long messageID;
	private long serverID;
	private long channelID;

	public long getMessageID()
	{
		return messageID;
	}

	public void setMessageID(long messageID)
	{
		this.messageID = messageID;
	}

	public long getServerID()
	{
		return serverID;
	}

	public void setServerID(long serverID)
	{
		this.serverID = serverID;
	}

	public long getChannelID()
	{
		return channelID;
	}

	public void setChannelID(long channelID)
	{
		this.channelID = channelID;
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
