package de.mymiggi.discordbot.music.youtube.util;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class Song
{
	private AudioTrack audioSource;
	private String searchQurry;
	private String title;

	public Song()
	{

	}

	public AudioTrack getAudioSource()
	{
		return audioSource;
	}

	public Song(AudioTrack audioSource)
	{
		this.audioSource = audioSource;
	}

	public Song(String searchQurry)
	{
		this.searchQurry = searchQurry;
	}

	public void setAudioSource(AudioTrack audioSource)
	{
		this.audioSource = audioSource;
	}

	public String getSearchQurry()
	{
		return searchQurry;
	}

	public void setSearchQurry(String searchQurry)
	{
		this.searchQurry = searchQurry;
	}

	public String getTitel()
	{
		return title;
	}

	public void setTitel(String titel)
	{
		this.title = titel;
	}
}
