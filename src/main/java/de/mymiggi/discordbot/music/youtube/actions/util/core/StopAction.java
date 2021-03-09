package de.mymiggi.discordbot.music.youtube.actions.util.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class StopAction
{
	private Logger logger = LoggerFactory.getLogger("StopAction");

	public void run(Queue queue, AudioResource audioResource)
	{
		MessageCoolDown.del(queue.getLastQueueEmbedLink(), queue.getTextChannel());
		if (audioResource.getAudioConnection() != null)
		{
			try
			{
				audioResource.getAudioConnection().close();
			}
			catch (Exception e)
			{
				logger.debug(e.getMessage());
				logger.debug(e.getClass().getName());
			}
		}
		queue.setCurrentTrackPosition(0);
		queue.setAddedSongsSize(0);
		queue.setLoopingPostion(0);
		queue.setPlayingStatus(Status.STOPPED);
		queue.setSearchingFailed(false);
		queue.setLoadingPlaylist(false);
		queue.setLooping(false);
		audioResource.setAudioConnection(null);
		queue.setUserWhoStartedQueue(null);
		queue.setLastQueueEmbedLink(null);
		queue.setLastAddedTrack(null);
		audioResource.setPlayerManager(null);
		queue.setCurrentTrack(null);
		queue.setVoicChannel(null);
		queue.setTextChannel(null);
		audioResource.setPlayer(null);
		queue.setSongs(null);
	}
}
