package de.mymiggi.discordbot.music.youtube.actions.util.core;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.core.actions.PlayCoreAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class MoveVoiceChannelAction
{
	private static Logger logger = LoggerFactory.getLogger(PlayCoreAction.class.getSimpleName());

	public void run(ServerVoiceChannel channelToMove, AudioResource audioResource, Queue queue)
	{
		channelToMove.connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			audioResource.setAudioConnection(audioConnection);
			queue.setVoicChannel(channelToMove);
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", channelToMove.getServer().getName(), e);
			throw new RuntimeException(e);
		});
	}
}
