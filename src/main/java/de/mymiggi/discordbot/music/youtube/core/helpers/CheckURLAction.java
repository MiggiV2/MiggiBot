package de.mymiggi.discordbot.music.youtube.core.helpers;

import de.mymiggi.discordbot.music.youtube.util.QueryResponse;

public class CheckURLAction
{
	public QueryResponse run(String searchQuery)
	{
		QueryResponse response = new QueryResponse();
		if (searchQuery.startsWith("https://"))
		{
			response.setUrl(searchQuery);
			response.wasURL(true);
		}
		else
		{
			response.setUrl("ytsearch: " + searchQuery);
			response.wasURL(false);
		}
		return response;
	}
}
