package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MemberPlayListInfoNew implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	private static final long serialVersionUID = 1L;
	private long userID;
	private String playListTitel;
	private boolean joined;
	private boolean publicToAllUseres;

	public long getUserID()
	{
		return userID;
	}

	public MemberPlayListInfoNew setUserID(long userID)
	{
		this.userID = userID;
		return this;
	}

	public String getPlayListTitle()
	{
		return playListTitel;
	}

	public MemberPlayListInfoNew setPlayListTitel(String playListTitel)
	{
		this.playListTitel = playListTitel;
		return this;
	}

	public boolean isJoined()
	{
		return joined;
	}

	public MemberPlayListInfoNew setJoined(boolean joined)
	{
		this.joined = joined;
		return this;
	}

	public boolean isPublicToAllUseres()
	{
		return publicToAllUseres;
	}

	public MemberPlayListInfoNew setPublicToAllUseres(boolean publicToAllUseres)
	{
		this.publicToAllUseres = publicToAllUseres;
		return this;
	}
}
