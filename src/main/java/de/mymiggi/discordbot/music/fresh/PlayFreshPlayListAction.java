package de.mymiggi.discordbot.music.fresh;

import java.util.List;
import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.fresh.entitys.FreshSong;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;

public class PlayFreshPlayListAction
{
	private Logger logger = LoggerFactory.getLogger(PlayFreshPlayListAction.class.getSimpleName());

	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		try
		{
			List<FreshSong> songs = new FreshPlayer().loadSongs();
			if (!serverPlayer.containsKey(event.getServer().get()))
			{
				tryToJoinFirstTime(event, serverPlayer, songs);
			}
			else
			{
				tryToJoin(event, serverPlayer, songs);
			}
			tryToSendQueueEmbed(event, serverPlayer);
		}
		catch (Exception e)
		{
			logger.warn("Error", e);
			new SendErrorEmbedAction().run(event, "Error! -> " + e.getClass().getName());
		}
	}

	private void tryToJoinFirstTime(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<FreshSong> songs)
	{
		if (event.getMessageAuthor().getConnectedVoiceChannel().isPresent())
		{
			event.getServer().ifPresent(server -> {
				ServerPlayer player = new ServerPlayer();
				serverPlayer.put(server, player);
				player.runFreshPlaylist(event, songs, false);
			});
			event.getChannel().type().thenAccept(successful -> {
				event.getMessage().addReaction("👍");
			});
		}
		else
		{
			logger.info("User is not in VoicChannel @" + event.getMessageAuthor().getName());
			new SendErrorEmbedAction().run(event, "Please join a VoicChannel first!");
			event.addReactionsToMessage("👎");
		}
	}

	private void tryToJoin(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<FreshSong> songs)
	{
		event.getServer().ifPresent(server -> {
			if (event.getMessageAuthor().getConnectedVoiceChannel().isPresent())
			{
				ServerPlayer player = serverPlayer.get(server);
				if (player.isAllowed(event.getMessageAuthor().asUser().get()))
				{
					if (player.getConnectedChannel() != null && player.getConnectedChannel().isConnected(BotMainCore.api.getYourself()))
					{
						player.loadFreshPlaylist(songs);
					}
					else
					{
						player.runFreshPlaylist(event, songs, false);
					}
					event.getChannel().type().thenAccept(successful -> {
						event.getMessage().addReaction("👍");
					});
				}
				else
				{
					logger.info("In a different voice channel! @" + event.getMessageAuthor().getName());
					new SendErrorEmbedAction().run(event, "I'm sorry, but im used in a different voice channel!");
					event.addReactionsToMessage("👎");
				}
			}
			else
			{
				logger.info("User is not in VoicChannel @" + event.getMessageAuthor().getName());
				new SendErrorEmbedAction().run(event, "Please join a VoicChannel first!");
				event.addReactionsToMessage("👎");
			}
		});
	}

	private void tryToSendQueueEmbed(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(1500);
					ServerPlayer player = serverPlayer.get(event.getServer().get());
					int counter = 0;
					while (player.getLastAddedTrack() == null)
					{
						counter++;
						Thread.sleep(100);
						if (counter == 40)
						{
							break;
						}
					}
					player.sendQueueEmbed();
				}
				catch (Exception e)
				{
					logger.error("Failed", e);
				}
			}
		};
		thread.start();
	}
}