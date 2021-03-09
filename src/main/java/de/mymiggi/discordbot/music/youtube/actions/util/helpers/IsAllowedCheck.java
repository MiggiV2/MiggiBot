package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class IsAllowedCheck
{
	public boolean run(Queue queue, User user)
	{
		if (queue == null)
		{
			return false;
		}
		if (queue.getUserWhoStartedQueue() == null || queue.getUserWhoStartedQueue().getId() == user.getId())
		{
			return true;
		}
		if (user == BotMainCore.api.getYourself())
		{
			return true;
		}
		if (user.isConnected(queue.getVoicChannel()))
		{
			return true;
		}
		return false;
	}
}
