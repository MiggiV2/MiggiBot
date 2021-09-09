package de.mymiggi.discordbot.main.statusmessage;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.activity.ActivityType;

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
						api.updateActivity(ActivityType.LISTENING, "/help");
						Thread.sleep(8000);
						api.updateActivity(ActivityType.PLAYING, "/music | music help");
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
