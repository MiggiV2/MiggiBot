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

	public ReactionRoleSetting setServerID(long serverID)
	{
		ServerID = serverID;
		return this;
	}

	public String getMessageLink()
	{
		return MessageLink;
	}

	public ReactionRoleSetting setMessageLink(String messageLink)
	{
		MessageLink = messageLink;
		return this;
	}

	public long getTimeStamp()
	{
		return timeStamp;
	}

	public ReactionRoleSetting setTimeStamp(long timeStamp)
	{
		this.timeStamp = timeStamp;
		return this;
	}

	public String getRoleID()
	{
		return roleID;
	}

	public ReactionRoleSetting setRoleID(String roleID)
	{
		this.roleID = roleID;
		return this;
	}

	public String getReaction()
	{
		return reaction;
	}

	public ReactionRoleSetting setReaction(String reaction)
	{
		this.reaction = reaction;
		return this;
	}
}
