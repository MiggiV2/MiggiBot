package de.mymiggi.discordbot.music.youtube.util;

public class QueryResponse
{
	private String url;
	private boolean wasURL;
	private boolean isPlaylist;

	public boolean isWasURL()
	{
		return wasURL;
	}

	public void wasURL(boolean wasURL)
	{
		this.wasURL = wasURL;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public boolean isPlaylist()
	{
		return isPlaylist;
	}

	public void setPlaylist(boolean isPlaylist)
	{
		this.isPlaylist = isPlaylist;
	}
}
