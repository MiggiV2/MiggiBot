package de.mymiggi.discordbot.server.r6.matchmaker.handler;

import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;

public class GetNumberByReaction
{
	public int get(String reaction) throws Exception
	{
		for (NumberEmoji numberEmoji : NumberEmoji.values())
		{
			if (numberEmoji.getEmoji().equals(reaction))
			{
				return numberEmoji.getNumber();
			}
		}
		throw new Exception("Not found!");
	}
}
