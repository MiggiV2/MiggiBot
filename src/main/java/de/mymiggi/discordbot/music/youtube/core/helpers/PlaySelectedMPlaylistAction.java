package de.mymiggi.discordbot.music.youtube.core.helpers;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.QueryResponse;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class PlaySelectedMPlaylistAction
{
	private Logger logger = LoggerFactory.getLogger(PlaySelectedMPlaylistAction.class.getSimpleName());

	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<NewMemberPlaylistSong> currentPlayListSongs)
	{
		try
		{
			ServerVoiceChannel channel;
			Server server;
			try
			{
				channel = event.getMessageAuthor().getConnectedVoiceChannel().get().asServerVoiceChannel().get();
				server = event.getServer().get();
			}
			catch (NoSuchElementException e)
			{
				logger.info("User is not in VoicChannel");
				new SendErrorEmbedAction().run(event, "Please join a VoicChannel first!");
				return;
			}
			QueryResponse queryResponse = new CheckURLAction().run(currentPlayListSongs.get(0).getSongURL());
			String loandingMessageURL = event.getChannel().sendMessage("Loading your songs...").get().getLink().toString();
			if (!BotMainCore.api.getYourself().isConnected(channel))
			{
				ServerPlayer player = new ServerPlayer();
				serverPlayer.put(server, player);
				logger.info("Created musicplayer for server " + server.getName());
				logger.info("Start playing...");
				new StartPlayingAction().run(event, serverPlayer, queryResponse, false, false, false, true);
			}
			else if (new IsLeagalCheck().run(event, serverPlayer))
			{
				logger.info("Created musicplayer for server " + server.getName());
				logger.info("Start playing...");
				new StartPlayingAction().run(event, serverPlayer, queryResponse, true, false, false, true);
			}
			ServerPlayer player = serverPlayer.get(server);
			while (player.getCurrentTrack() == null)
			{
				Thread.sleep(50);
			}
			player.sendQueueEmbed();
			MessageCoolDown.del(loandingMessageURL, event.getChannel(), 0);
			for (int i = 1; i < currentPlayListSongs.size(); i++)
			{
				queryResponse = new CheckURLAction().run(currentPlayListSongs.get(i).getSongURL());
				if (player.isAllowd(event.getMessageAuthor().asUser().get()))
				{
					new StartPlayingAction().run(event, serverPlayer, queryResponse, true, false, false, true);
					Thread.sleep(20);
				}
			}
			int lastIndex = currentPlayListSongs.size() - 1;
			String titelToAdd = currentPlayListSongs.get(lastIndex).getTitle();
			int counter = 0;
			while (!player.getLastAddedTrack().getInfo().title.equals(titelToAdd))
			{
				counter++;
				Thread.sleep(10);
				if (counter == 100)
				{
					logger.warn("Time out! lastTrackTitel equals lastToAdd");
					logger.warn(titelToAdd + " != " + player.getLastAddedTrack().getInfo().title);
					break;
				}
			}
			player.updateQueueEmbed();
			Thread thread = new Thread()
			{
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(2000);
						player.updateQueueEmbed();
					}
					catch (Exception e)
					{
						logger.error("Failed", e);
					}
				}
			};
			thread.start();
		}
		catch (Exception e)
		{
			logger.error("Error", e);
		}
	}
}
