package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Deprecated
@Entity
public class UntisReminderChannel implements Serializable
{
	@Id
	private long id = 0;
	private static final long serialVersionUID = -2889762040666765811L;
	private long channelID;
	private String serverName;
	private String channelName;

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
