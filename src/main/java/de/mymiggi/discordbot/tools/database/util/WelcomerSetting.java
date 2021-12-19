package de.mymiggi.discordbot.tools.database.util;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class WelcomerSetting
{
	@Id
	private long timeStamp = System.currentTimeMillis();
	private long ChannelID;
	private long ServerID;
	private long RoleID;

	public long getServerID()
	{
		return ServerID;
	}

	public WelcomerSetting setServerID(long serverID)
	{
		ServerID = serverID;
		return this;
	}

	public long getChannelID()
	{
		return ChannelID;
	}

	public WelcomerSetting setChannelID(long channelID)
	{
		ChannelID = channelID;
		return this;
	}

	public long getRoleID()
	{
		return RoleID;
	}

	public WelcomerSetting setRoleID(long roleID)
	{
		RoleID = roleID;
		return this;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public WelcomerSetting setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
		return this;
	}

}
