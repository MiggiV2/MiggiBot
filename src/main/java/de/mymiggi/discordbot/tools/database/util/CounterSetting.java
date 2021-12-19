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

	public CounterSetting setMessageID(long messageID)
	{
		this.messageID = messageID;
		return this;
	}

	public long getServerID()
	{
		return serverID;
	}

	public CounterSetting setServerID(long serverID)
	{
		this.serverID = serverID;
		return this;
	}

	public long getChannelID()
	{
		return channelID;
	}

	public CounterSetting setChannelID(long channelID)
	{
		this.channelID = channelID;
		return this;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public CounterSetting setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
		return this;
	}
}
