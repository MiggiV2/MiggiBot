package de.mymiggi.discordbot.music.youtube.core.helpers;

public class BuildStringAction
{
	public String run(String[] context)
	{
		String searchQuery = "";

		if (context.length == 2)
		{
			return context[1];
		}

		for (int i = 1; i < context.length; i++)
		{
			if (i == context.length - 1)
			{
				searchQuery += context[i];
				return searchQuery;
			}
			searchQuery += context[i] + " ";
		}
		return searchQuery;
	}
}
