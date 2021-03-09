package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RainbowSixPlayer implements Serializable
{
	private static final long serialVersionUID = -7579383455638994904L;
	private long userID;
	private long serverID;
	private int skillIndex;
	private String name;
	@Id
	private String id;

	public String getId()
	{
		return id;
	}

	public int getSkillIndex()
	{
		return skillIndex;
	}

	public void setSkillIndex(int skillIndex)
	{
		this.skillIndex = skillIndex;
	}

	public long getUserID()
	{
		return userID;
	}

	public void setUserID(long userID)
	{
		id = serverID + "" + userID;
		this.userID = userID;
	}

	public long getServerID()
	{
		return serverID;
	}

	public void setServerID(long serverID)
	{
		id = serverID + "" + userID;
		this.serverID = serverID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
