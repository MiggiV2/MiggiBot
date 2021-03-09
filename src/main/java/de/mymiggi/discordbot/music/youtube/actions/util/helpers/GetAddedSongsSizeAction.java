package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.util.Queue;

public class GetAddedSongsSizeAction
{
	private static final Logger logger = LoggerFactory.getLogger(GetAddedSongsSizeAction.class.getSimpleName());

	public int run(Queue queue)
	{
		if (queue.getAddedSongsSize() == 0)
		{
			logger.error("Error! AddedSongsInt is 0");
		}

		logger.info("Added " + queue.getAddedSongsSize() + " songs");

		return queue.getAddedSongsSize();
	}
}
