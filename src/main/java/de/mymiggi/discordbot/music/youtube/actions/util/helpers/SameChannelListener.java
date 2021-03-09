package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import org.javacord.api.entity.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class SameChannelListener
{
	private Logger logger = LoggerFactory.getLogger("SameChannelListener");

	public void run(Queue queue)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				Server server = queue.getTextChannel().asServerTextChannel().get().getServer();

				while (queue.getPlayingStatus() == Status.PLAYING)
				{
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					BotMainCore.api.getYourself().getConnectedVoiceChannel(server).ifPresent(vc -> {
						if (vc != queue.getVoicChannel())
						{
							logger.info("Not same channel dedected!");
							queue.setPlayingStatus(Status.STOPPED);
						}
					});
				}
			}
		};
		thread.start();
	}
}
