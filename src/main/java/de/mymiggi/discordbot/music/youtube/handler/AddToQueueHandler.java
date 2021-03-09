package de.mymiggi.discordbot.music.youtube.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.youtube.actions.util.helpers.JustAddFirstResultAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.music.youtube.util.Song;

public final class AddToQueueHandler implements AudioLoadResultHandler
{
	private final boolean toPush;
	private final String url;
	private final boolean queryIsPlayist;
	private Queue queue;
	private AudioResource audioRescure;
	private static final Logger logger = LoggerFactory.getLogger(AddToQueueHandler.class.getSimpleName());

	public AddToQueueHandler(Queue queue, AudioResource audioRescure, boolean toPush, String url, boolean queryIsPlayist)
	{
		this.queue = queue;
		this.toPush = toPush;
		this.url = url;
		this.queryIsPlayist = queryIsPlayist;
		this.audioRescure = audioRescure;
	}

	@Override
	public void trackLoaded(AudioTrack track)
	{
		if (toPush)
		{
			queue.getSongs().add(queue.getCurrentTrackPosition() + 1, new Song(track));
		}
		else
		{
			queue.getSongs().add(new Song(track));
		}
		queue.setLastAddedTrack(track);
		logger.info("Loaded song " + track.getInfo().title);
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist)
	{
		if (url.startsWith("ytsearch: ") && !queryIsPlayist)
		{
			new JustAddFirstResultAction().run(queue, audioRescure, playlist, false, toPush);
		}
		else
		{
			for (AudioTrack track : playlist.getTracks())
			{
				queue.getSongs().add(new Song(track));
				queue.setLastAddedTrack(track);
				queue.addedSongsSizeAddOne();
				logger.info("Loaded song " + track.getInfo().title);
			}
		}
	}

	@Override
	public void noMatches()
	{
		logger.error("run() No matches for " + url);
	}

	@Override
	public void loadFailed(FriendlyException throwable)
	{
		logger.error(throwable.getMessage());
	}
}