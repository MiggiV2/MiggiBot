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

public class InstantHandler implements AudioLoadResultHandler
{
	private static final Logger logger = LoggerFactory.getLogger(InstantHandler.class.getSimpleName());
	private Queue queue;
	private AudioResource audioResource;
	private String url;
	private boolean queryIsPlayist;
	private boolean replaceWithAudioSource = false;

	public InstantHandler(Queue queue, AudioResource audioResource)
	{
		this.queue = queue;
		this.audioResource = audioResource;
	}

	public void set(String url, boolean queryIsPlayist)
	{
		this.url = url;
		this.queryIsPlayist = queryIsPlayist;
	}

	public AudioTrack get()
	{
		return queue.getCurrentTrack();
	}

	@Override
	public void trackLoaded(AudioTrack track)
	{
		queue.setCurrentTrack(track);
		queue.setLastAddedTrack(track);
		audioResource.getPlayer().playTrack(track);
		if (!replaceWithAudioSource)
		{
			queue.getSongs().add(new Song(track));
		}
		else
		{
			int currentPostion = queue.getCurrentTrackPosition();
			Song song = new Song(track);
			queue.getSongs().set(currentPostion, song);
		}
	}

	@Override
	public void playlistLoaded(AudioPlaylist playlist)
	{
		if (url.startsWith("ytsearch: ") && !queryIsPlayist)
		{
			new JustAddFirstResultAction().run(queue, audioResource, playlist, true, false);
		}
		else
		{
			queue.setLoadingPlaylist(true);
			for (AudioTrack track : playlist.getTracks())
			{
				if (!replaceWithAudioSource)
				{
					queue.getSongs().add(new Song(track));
				}
				queue.addedSongsSizeAddOne();
			}
			int currentPostion = queue.getCurrentTrackPosition();
			Song nextSong = queue.getSongs().get(currentPostion);
			AudioTrack nextAudioSource = nextSong.getAudioSource();
			queue.setCurrentTrack(nextAudioSource);
			queue.setLastAddedTrack(nextAudioSource);
			audioResource.getPlayer().playTrack(nextAudioSource);
		}
	}

	@Override
	public void noMatches()
	{
		queue.setSearchingFailed(true);
		logger.error("run() No matches for '" + url + "'");
	}

	@Override
	public void loadFailed(FriendlyException throwable)
	{
		queue.setSearchingFailed(true);
		logger.error(throwable.getMessage());
	}

	public void setReplaceWithAudioSource(boolean dontAddToQueue)
	{
		this.replaceWithAudioSource = dontAddToQueue;
	}
}
