package de.mymiggi.discordbot.music.youtube.actions.util.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.actions.util.helpers.ConnectedVoicChannelListener;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class LoopAction
{
	private static Logger logger = LoggerFactory.getLogger(LoopAction.class.getSimpleName());
	
	public void run(Queue queue, AudioResource audioResource)
	{
		if(queue.isLooping())
		{
			logger.info("Loop off, for server "+ queue.getTextChannel().asServerTextChannel().get().getServer().getName());
			queue.setLooping(false);
		}else 
		{
			new ConnectedVoicChannelListener().run(queue, audioResource);
			logger.info("Loop on, for server "+ queue.getTextChannel().asServerTextChannel().get().getServer().getName());
			queue.setLoopingPostion(queue.getCurrentTrackPosition());
			queue.setLooping(true);
		}
	}
}
