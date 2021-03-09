package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.handler.AddToQueueHandler;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class AddSongToQueueAction
{
	private static final Logger logger = LoggerFactory.getLogger(AddSongToQueueAction.class.getSimpleName());

	public void run(Queue queue, AudioResource audioResource, String url, boolean queryIsPlayist, boolean toPush)
	{
		if (queue.getPlayingStatus() != Status.PLAYING)
		{
			logger.info("Restarted queue");
			new NextTrackListener().run(queue, audioResource);
		}
		queue.setLastAddedTrack(null);
		queue.setAddedSongsSize(0);
		audioResource.getPlayerManager().loadItem(url, new AddToQueueHandler(queue, audioResource, toPush, url, queryIsPlayist));
	}
}
