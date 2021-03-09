package de.mymiggi.discordbot.main.statusmessage;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;

import de.mymiggi.discordbot.main.BotMainCore;

public class HelpStatusThread
{
	public static void run(DiscordApi api)
	{
		api.unsetActivity();
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					try
					{
						api.updateActivity(ActivityType.LISTENING, BotMainCore.prefix + "help");
						Thread.sleep(8000);
						api.updateActivity(ActivityType.PLAYING, BotMainCore.prefix + "music | music help");
						Thread.sleep(8000);
						api.updateActivity(ActivityType.LISTENING, api.getServers().size() + " server");
						Thread.sleep(8000);
						api.updateActivity(ActivityType.LISTENING, api.getCachedUsers().size() + " users");
						Thread.sleep(8000);
					}
					catch (Exception e)
					{
					}
				}
			}
		};
		thread.start();
	}
}
