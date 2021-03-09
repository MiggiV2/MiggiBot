package de.mymiggi.discordbot.server.r6.map.update.actions;

import org.javacord.api.entity.message.Message;

public class CloseEmbedAction
{
	public void run(Message message)
	{
		if (message.canYouDelete())
		{
			message.delete();
		}
	}
}
