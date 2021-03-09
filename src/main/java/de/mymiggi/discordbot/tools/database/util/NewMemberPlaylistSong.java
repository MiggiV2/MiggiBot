package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NewMemberPlaylistSong implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private static final long serialVersionUID = 1L;
	private long userID;
	private String songURL;
	private String playListName;
	private String title;

	public long getUserID()
	{
		return userID;
	}

	public void setUserID(long userID)
	{
		this.userID = userID;
	}

	public String getSongURL()
	{
		return songURL;
	}

	public void setSongURL(String songURL)
	{
		this.songURL = songURL;
	}

	public String getPlayListName()
	{
		return playListName;
	}

	public void setPlayListName(String playListName)
	{
		this.playListName = playListName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
