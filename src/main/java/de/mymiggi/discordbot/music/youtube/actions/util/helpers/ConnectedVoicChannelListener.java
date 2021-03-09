package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.actions.util.core.StopAction;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ConnectedVoicChannelListener
{
	private static final Logger logger = LoggerFactory.getLogger(ConnectedVoicChannelListener.class.getSimpleName());
	
	public void run(Queue queue, AudioResource audioResource)
	{
		queue.getVoicChannel().getConnectedUsers().size();
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				logger.info("Alive!");
				while(queue.getPlayingStatus() == Status.PLAYING && queue.isLooping())
				{
					try
					{
						Thread.sleep(2000);
					}
					catch(Exception e)
					{
						logger.warn("Error", e);
					}
					if(queue.getVoicChannel().getConnectedUsers().size() == 1)
					{
						logger.info("Only me in VC! -> I have to go!");
						new StopAction().run(queue, audioResource);
					}
				}
				logger.info("Dead");
			}
		};
		thread.start();
	}
}
