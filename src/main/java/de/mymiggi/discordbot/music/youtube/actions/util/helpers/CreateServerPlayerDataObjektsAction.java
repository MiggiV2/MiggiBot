package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import java.util.ArrayList;

import org.javacord.api.audio.AudioSource;
import org.javacord.api.event.message.MessageCreateEvent;

import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.LavaplayerAudioSource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.music.youtube.util.Song;

public class CreateServerPlayerDataObjektsAction
{
	public void run(Queue queue, AudioResource audioResource, MessageCreateEvent event)
	{
		audioResource.setPlayerManager(new DefaultAudioPlayerManager());
		queue.setSongs(new ArrayList<Song>());
		audioResource.getPlayerManager().registerSourceManager(new YoutubeAudioSourceManager());
		audioResource.setPlayer(audioResource.getPlayerManager().createPlayer());
		AudioSource source = new LavaplayerAudioSource(BotMainCore.api, audioResource.getPlayer());
		audioResource.setSource(source);
		queue.setTextChannel(event.getChannel().asTextChannel().get());
		queue.setUserWhoStartedQueue(event.getMessageAuthor().asUser().get());
	}
}
