package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CovidChannelConfig implements Serializable
{
	@Id
	private long serverID;
	private static final long serialVersionUID = 1L;
	private long channelID;
	private String serverName;
	private String channelName;

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

	public String getServerName()
	{
		return serverName;
	}

	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	public String getChannelName()
	{
		return channelName;
	}

	public void setChannelName(String channelName)
	{
		this.channelName = channelName;
	}
}
