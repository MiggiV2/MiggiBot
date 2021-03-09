package de.mymiggi.discordbot.music.youtube.actions.util.core;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
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
			startDeafenListener(event, queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", event.getServer().get(), e);
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
			// waitTillTrackLoaded(queue);
			for (int i = 0; i < playlist.size(); i++)
			{
				Song song = new Song();
				song.setSearchQurry(playlist.get(i).getSongURL());
				song.setTitel(playlist.get(i).getTitle());
				queue.getSongs().add(song);
			}
			audioResource.getPlayerManager().loadItem(searchQuery, instantHandler);
			startDeafenListener(event, queue.getVoicChannel());
		}).exceptionally(e -> {
			logger.error("Could not get connection to server {}", event.getServer().get(), e);
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

	private void startDeafenListener(MessageCreateEvent event, ServerVoiceChannel serverVoiceChanne)
	{
		if (event.getServer().get().canYouDeafenMembers() && serverVoiceChanne.canMuteUsers(BotMainCore.api.getYourself()))
		{
			event.getServer().ifPresent(server -> {
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
								event.getChannel()
									.sendMessage(embed);
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
			});
		}
	}
}
