package de.mymiggi.discordbot.music.youtube.util;

import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class AudioResource
{
	private AudioPlayerManager playerManager;
	private AudioPlayer player;
	private AudioSource source;
	private AudioConnection audioConnection;

	public AudioPlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public void setPlayerManager(AudioPlayerManager playerManager)
	{
		this.playerManager = playerManager;
	}

	public AudioPlayer getPlayer()
	{
		return player;
	}

	public void setPlayer(AudioPlayer player)
	{
		this.player = player;
	}

	public AudioSource getSource()
	{
		return source;
	}

	public void setSource(AudioSource source)
	{
		this.source = source;
	}

	public AudioConnection getAudioConnection()
	{
		return audioConnection;
	}

	public void setAudioConnection(AudioConnection audioConnection)
	{
		this.audioConnection = audioConnection;
	}
}
