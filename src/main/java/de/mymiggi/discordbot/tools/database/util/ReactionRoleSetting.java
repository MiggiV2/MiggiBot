package de.mymiggi.discordbot.tools.database.util;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class ReactionRoleSetting
{
	@Id
	private long timeStamp = System.currentTimeMillis();
	private long ServerID;
	private String MessageLink;
	private String reaction;
	private String roleID;
	// Reaction & Role

	public long getServerID()
	{
		return ServerID;
	}

	public void setServerID(long serverID)
	{
		ServerID = serverID;
	}

	public String getMessageLink()
	{
		return MessageLink;
	}

	public void setMessageLink(String messageLink)
	{
		MessageLink = messageLink;
	}
	
	public long getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getRoleID()
	{
		return roleID;
	}

	public void setRoleID(String roleID)
	{
		this.roleID = roleID;
	}

	public String getReaction()
	{
		return reaction;
	}

	public void setReaction(String reaction)
	{
		this.reaction = reaction;
	}
}
