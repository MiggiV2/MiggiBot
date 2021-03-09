package de.mymiggi.discordbot.server.r6.map.update;

import java.util.List;

import org.javacord.api.entity.emoji.Emoji;

import de.mymiggi.discordbot.server.r6.map.update.actions.AbstractUpdateAction;

public class GetUpdateActionByEmoji
{
	public AbstractUpdateAction run(List<AbstractUpdateAction> actions, Emoji emoji) throws Exception
	{
		for (AbstractUpdateAction temp : actions)
		{
			if (temp.equalsEmoji(emoji))
			{
				return temp;
			}
		}
		throw new Exception("Not found!");
	}
}
