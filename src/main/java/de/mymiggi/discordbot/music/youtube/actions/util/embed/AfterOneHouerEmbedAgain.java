package de.mymiggi.discordbot.music.youtube.actions.util.embed;

import java.util.List;

import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class AfterOneHouerEmbedAgain
{

	public void run(String lastQueueEmbedLink, Queue queue, List<AbstractYTAction> actions)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(60 * 60 * 1000);
				}
				catch (Exception e)
				{
				}

				if (lastQueueEmbedLink == queue.getLastQueueEmbedLink() && queue.getPlayingStatus() != Status.STOPPED)
				{
					new SendQueueEmbedAction().run(queue, actions);
				}
			}
		};
		thread.start();
	}
}
