package de.mymiggi.discordbot.music.youtube.actions.util.core;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.fresh.entitys.FreshSong;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.CreateServerPlayerDataObjektsAction;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.NextTrackListener;
import de.mymiggi.discordbot.music.youtube.handler.InstantHandler;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.music.youtube.util.Song;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class StartPlayingAction
{
	private static final Logger logger = LoggerFactory.getLogger(StartPlayingAction.class.getSimpleName());

	public void run(Queue queue, AudioResource audioResource, InstantHandler instantHandler, MessageCreateEvent event, String searchQuery, boolean queryIsPlayist)
	{
		new CreateServerPlayerDataObjektsAction().run(queue, audioResource, event);
		new NextTrackListener().run(queue, audioResource);

		queue.getVoicChannel().connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			audioResource.setAudioConnection(audioConnection);
			instantHandler.set(searchQuery, queryIsPlayist);
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(event.getServer().get(), event.getChannel(), queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", event.getServer().get(), e);
			throw new RuntimeException(e);
		});
	}

	public void run(Queue queue, AudioResource audioResource, InstantHandler instantHandler, SlashCommandCreateEvent event, String searchQuery, boolean queryIsPlayist)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		new CreateServerPlayerDataObjektsAction().run(queue, audioResource, event);
		new NextTrackListener().run(queue, audioResource);

		queue.getVoicChannel().connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			audioResource.setAudioConnection(audioConnection);
			instantHandler.set(searchQuery, queryIsPlayist);
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(interaction.getServer().get(), interaction.getChannel().get(), queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", interaction.getServer().get(), e);
			throw new RuntimeException(e);
		});
	}

	public void runFreshPlaylist(Queue queue, AudioResource audioResource, InstantHandler instantHandler, MessageCreateEvent event, List<FreshSong> playlist, boolean queryIsPlayist)
	{
		new CreateServerPlayerDataObjektsAction().run(queue, audioResource, event);
		new NextTrackListener().run(queue, audioResource);

		queue.getVoicChannel().connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			String searchQuery = playlist.get(0).getSearchQuery();
			audioResource.setAudioConnection(audioConnection);
			instantHandler.setReplaceWithAudioSource(true);
			instantHandler.set(searchQuery, queryIsPlayist);
			for (int i = 0; i < playlist.size(); i++)
			{
				Song song = new Song();
				FreshSong freshSong = playlist.get(i);
				song.setSearchQurry(freshSong.getSearchQuery());
				song.setTitel(freshSong.getTitle() + " | " + freshSong.getArtist());
				queue.getSongs().add(song);
			}
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(event.getServer().get(), event.getChannel(), queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", event.getServer().get(), e);
			throw new RuntimeException(e);
		});
	}

	public void runFreshPlaylist(Queue queue, AudioResource audioResource, InstantHandler instantHandler, SlashCommandCreateEvent event, List<FreshSong> playlist, boolean queryIsPlayist)
	{
		new CreateServerPlayerDataObjektsAction().run(queue, audioResource, event);
		new NextTrackListener().run(queue, audioResource);
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();

		queue.getVoicChannel().connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			String searchQuery = playlist.get(0).getSearchQuery();
			audioResource.setAudioConnection(audioConnection);
			instantHandler.setReplaceWithAudioSource(true);
			instantHandler.set(searchQuery, queryIsPlayist);
			for (int i = 0; i < playlist.size(); i++)
			{
				Song song = new Song();
				FreshSong freshSong = playlist.get(i);
				song.setSearchQurry(freshSong.getSearchQuery());
				song.setTitel(freshSong.getTitle() + " | " + freshSong.getArtist());
				queue.getSongs().add(song);
			}
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(interaction.getServer().get(), interaction.getChannel().get(), queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", interaction.getServer(), e);
			throw new RuntimeException(e);
		});
	}

	public void run(Queue queue, AudioResource audioResource, InstantHandler instantHandler, MessageCreateEvent event, List<NewMemberPlaylistSong> playlist, boolean queryIsPlayist)
	{
		new CreateServerPlayerDataObjektsAction().run(queue, audioResource, event);
		new NextTrackListener().run(queue, audioResource);

		queue.getVoicChannel().connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			audioResource.setAudioConnection(audioConnection);
			String searchQuery = playlist.get(0).getSongURL();
			instantHandler.setReplaceWithAudioSource(true);
			instantHandler.set(searchQuery, queryIsPlayist);
			for (int i = 0; i < playlist.size(); i++)
			{
				Song song = new Song();
				song.setSearchQurry(playlist.get(i).getSongURL());
				song.setTitel(playlist.get(i).getTitle());
				queue.getSongs().add(song);
			}
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(event.getServer().get(), event.getChannel(), queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", event.getServer().get(), e);
			throw new RuntimeException(e);
		});
	}

	public void run(Queue queue, AudioResource audioResource, InstantHandler instantHandler, SlashCommandCreateEvent event, List<NewMemberPlaylistSong> playlist, boolean queryIsPlayist)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		new CreateServerPlayerDataObjektsAction().run(queue, audioResource, event);
		new NextTrackListener().run(queue, audioResource);

		queue.getVoicChannel().connect().thenAccept(audioConnection -> {
			audioConnection.setAudioSource(audioResource.getSource());
			audioResource.setAudioConnection(audioConnection);
			String searchQuery = playlist.get(0).getSongURL();
			instantHandler.setReplaceWithAudioSource(true);
			instantHandler.set(searchQuery, queryIsPlayist);
			for (int i = 0; i < playlist.size(); i++)
			{
				Song song = new Song();
				song.setSearchQurry(playlist.get(i).getSongURL());
				song.setTitel(playlist.get(i).getTitle());
				queue.getSongs().add(song);
			}
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(interaction.getServer().get(), interaction.getChannel().get(), queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", interaction.getServer().get(), e);
			throw new RuntimeException(e);
		});
	}

	void waitTillTrackLoaded(Queue queue)
	{
		int counter = 0;
		while (queue.getCurrentTrack() == null)
		{
			counter++;
			try
			{
				Thread.sleep(20);
			}
			catch (InterruptedException e1)
			{
			}
			if (counter == 200)
			{
				break;
			}
		}
	}

	private void startDeafenListener(Server server, TextChannel channel, ServerVoiceChannel serverVoiceChanne)
	{
		if (server.canYouDeafenMembers() && serverVoiceChanne.canMuteUsers(BotMainCore.api.getYourself()))
		{
			try
			{
				BotMainCore.api.getYourself().deafen(server).get();
				BotMainCore.api.getYourself().addUserChangeDeafenedListener(deafenedEvent -> {
					if (!BotMainCore.api.getYourself().isDeafened(server))
					{
						try
						{
							BotMainCore.api.getYourself().deafen(server).get();
							EmbedBuilder embed = new EmbedBuilder()
								.setTitle("Please dont deafen me! Thx")
								.setColor(Color.RED);
							channel.sendMessage(embed);
						}
						catch (InterruptedException | ExecutionException e1)
						{
							e1.printStackTrace();
							logger.error("Could not mute my self on server {}", server.getName(), e1);
						}
					}
				});
			}
			catch (InterruptedException | ExecutionException e1)
			{
				logger.error("Could not mute my self on server {}", server.getName(), e1);
			}
		}
	}
}
