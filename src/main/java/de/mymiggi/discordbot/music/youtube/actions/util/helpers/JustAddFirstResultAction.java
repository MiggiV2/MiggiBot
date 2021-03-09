package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.music.youtube.util.Song;

public class JustAddFirstResultAction
{
	public void run(Queue queue, AudioResource audioResource, AudioPlaylist playlist, boolean shouldPlay, boolean toPush)
	{
		AudioTrack track = playlist.getTracks().get(0);
		queue.setLastAddedTrack(track);

		if (toPush)
		{
			queue.getSongs().add(queue.getCurrentTrackPosition() + 1, new Song(track));
		}
		else
		{
			queue.getSongs().add(new Song(track));
		}
		if (shouldPlay)
		{
			queue.setCurrentTrack(track);
			audioResource.getPlayer().playTrack(track);
		}
	}
}
