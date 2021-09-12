package de.mymiggi.discordbot.tools.util;

import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

public class RemoveResponseAction
{
	public void run(InteractionOriginalResponseUpdater message)
	{
		run(message, 3);
	}

	public void run(InteractionOriginalResponseUpdater message, int sec)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(sec * 1000);
					message.delete();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}
