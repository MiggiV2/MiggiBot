package de.mymiggi.discordbot.server.member.playlist.actions.helpers;

import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.user.User;

public class LastEmbedMaps
{
	private Map<User, String> lastAllPlaylistEmbedMap = new HashMap<User, String>();
	private Map<User, String> lastSongsEmbedMap = new HashMap<User, String>();
	private Map<User, String> lastPublishedPlaylistEmbedMap = new HashMap<User, String>();

	public Map<User, String> getLastAllPlaylistEmbedMap()
	{
		return lastAllPlaylistEmbedMap;
	}

	public void setLastAllPlaylistEmbedMap(Map<User, String> lastAllPlaylistEmbedMap)
	{
		this.lastAllPlaylistEmbedMap = lastAllPlaylistEmbedMap;
	}

	public Map<User, String> getLastSongsEmbedMap()
	{
		return lastSongsEmbedMap;
	}

	public void setLastSongsEmbedMap(Map<User, String> lastSongsEmbedMap)
	{
		this.lastSongsEmbedMap = lastSongsEmbedMap;
	}

	public Map<User, String> getLastPublishedPlaylistEmbedMap()
	{
		return lastPublishedPlaylistEmbedMap;
	}

	public void setLastPublishedPlaylistEmbedMap(Map<User, String> lastPublishedPlaylistEmbedMap)
	{
		this.lastPublishedPlaylistEmbedMap = lastPublishedPlaylistEmbedMap;
	}
}
