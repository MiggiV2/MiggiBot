package de.mymiggi.discordbot.music.youtube.util;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.audio.AudioSourceBase;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

public class LavaplayerAudioSource extends AudioSourceBase
{
	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;

	/**
	 * Creates a new lavaplayer audio source.
	 *
	 * @param api
	 *            A discord api instance.
	 * @param audioPlayer
	 *            An audio player from Lavaplayer.
	 */
	public LavaplayerAudioSource(DiscordApi api, AudioPlayer audioPlayer)
	{
		super(api);
		this.audioPlayer = audioPlayer;
	}

	@Override
	public byte[] getNextFrame()
	{
		if (lastFrame == null)
		{
			return null;
		}
		return applyTransformers(lastFrame.getData());
	}

	@Override
	public boolean hasFinished()
	{
		return false;
	}

	@Override
	public boolean hasNextFrame()
	{
		lastFrame = audioPlayer.provide();
		return lastFrame != null;
	}

	@Override
	public AudioSource copy()
	{
		return new LavaplayerAudioSource(getApi(), audioPlayer);
	}
}
