package de.mymiggi.discordbot.music.youtube.util;

import java.util.ArrayList;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.youtube.state.Status;

public class Queue
{
	private Status playingStatus;
	private boolean searchingFailed = false;
	private boolean loadingPlaylist = false;
	private boolean looping = false;
	private int loopingPostion = 0;
	private int currentTrackPosition = 0;
	private int addedSongsSize = 0;
	private String lastQueueEmbedLink;
	private ArrayList<Song> songs;
	private User userWhoStartedQueue;
	private ServerVoiceChannel voicChannel;
	private TextChannel textChannel;
	private AudioTrack lastAddedTrack;
	private AudioTrack currentTrack;

	public Status getPlayingStatus()
	{
		return playingStatus;
	}

	public void setPlayingStatus(Status playingStatus)
	{
		this.playingStatus = playingStatus;
	}

	public boolean isSearchingFailed()
	{
		return searchingFailed;
	}

	public void setSearchingFailed(boolean searchingFailed)
	{
		this.searchingFailed = searchingFailed;
	}

	public boolean isLoadingPlaylist()
	{
		return loadingPlaylist;
	}

	public void setLoadingPlaylist(boolean loadingPlaylist)
	{
		this.loadingPlaylist = loadingPlaylist;
	}

	public int getCurrentTrackPosition()
	{
		return currentTrackPosition;
	}

	public void setCurrentTrackPosition(int trackNumber)
	{
		this.currentTrackPosition = trackNumber;
	}

	public void currentTrackPositionAdd(int toAdd)
	{
		this.currentTrackPosition = +toAdd;
	}

	public void currentTrackPositionAddOne()
	{
		this.currentTrackPosition += 1;
	}

	public void gotOneSongBack()
	{
		this.currentTrackPosition -= 1;
	}

	public int getAddedSongsSize()
	{
		return addedSongsSize;
	}

	public void setAddedSongsSize(int addedSongsSize)
	{
		this.addedSongsSize = addedSongsSize;
	}

	public void addedSongsSizeAdd(int toAdd)
	{
		this.currentTrackPosition = +toAdd;
	}

	public void addedSongsSizeAddOne()
	{
		this.addedSongsSize += 1;
	}

	public String getLastQueueEmbedLink()
	{
		return lastQueueEmbedLink;
	}

	public void setLastQueueEmbedLink(String lastQueueEmbedLink)
	{
		this.lastQueueEmbedLink = lastQueueEmbedLink;
	}

	public ArrayList<Song> getSongs()
	{
		return songs;
	}

	public void setSongs(ArrayList<Song> songs)
	{
		this.songs = songs;
	}

	public User getUserWhoStartedQueue()
	{
		return userWhoStartedQueue;
	}

	public void setUserWhoStartedQueue(User userWhoStartedQueue)
	{
		this.userWhoStartedQueue = userWhoStartedQueue;
	}

	public ServerVoiceChannel getVoicChannel()
	{
		return voicChannel;
	}

	public void setVoicChannel(ServerVoiceChannel voicChannel)
	{
		this.voicChannel = voicChannel;
	}

	public TextChannel getTextChannel()
	{
		return textChannel;
	}

	public void setTextChannel(TextChannel textChannel)
	{
		this.textChannel = textChannel;
	}

	public AudioTrack getLastAddedTrack()
	{
		return lastAddedTrack;
	}

	public void setLastAddedTrack(AudioTrack lastAddedTrack)
	{
		this.lastAddedTrack = lastAddedTrack;
	}

	public AudioTrack getCurrentTrack()
	{
		return currentTrack;
	}

	public void setCurrentTrack(AudioTrack currentTrack)
	{
		this.currentTrack = currentTrack;
	}

	public boolean isLooping()
	{
		return looping;
	}

	public void setLooping(boolean looping)
	{
		this.looping = looping;
	}

	public int getLoopingPostion()
	{
		return loopingPostion;
	}

	public void setLoopingPostion(int loopingPostion)
	{
		this.loopingPostion = loopingPostion;
	}
}
