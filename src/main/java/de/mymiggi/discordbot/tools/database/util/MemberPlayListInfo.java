package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Deprecated
@Entity
public class MemberPlayListInfo implements Serializable
{
	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long timeStamp;
	private static final long serialVersionUID = 1L;
	private long userID;
	private String playListTitel;
	private boolean joined;
	private boolean publicToAllUseres;

	public long getUserID()
	{
		return userID;
	}

	public void setUserID(long userID)
	{
		this.userID = userID;
	}

	public String getPlayListTitle()
	{
		return playListTitel;
	}

	public void setPlayListTitel(String playListTitel)
	{
		this.playListTitel = playListTitel;
	}

	public boolean isJoined()
	{
		return joined;
	}

	public void setJoined(boolean joined)
	{
		this.joined = joined;
	}

	public boolean isPublicToAllUseres()
	{
		return publicToAllUseres;
	}

	public void setPublicToAllUseres(boolean publicToAllUseres)
	{
		this.publicToAllUseres = publicToAllUseres;
	}
}
