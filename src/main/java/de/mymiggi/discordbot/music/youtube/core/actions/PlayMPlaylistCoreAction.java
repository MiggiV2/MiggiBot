package de.mymiggi.discordbot.music.youtube.core.actions;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class PlayMPlaylistCoreAction
{
	private Logger logger = LoggerFactory.getLogger(PlayMPlaylistCoreAction.class.getSimpleName());

	public void fromEventUser(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		try
		{
			List<NewMemberPlaylistSong> currentPlayListSongs = BotMainCore.getMemberPlayListCore().getCurrentPlayList(event.getMessageAuthor().asUser().get());
			run(event, serverPlayer, currentPlayListSongs);
		}
		catch (Exception e)
		{
			logger.warn("Getting Memeberplaylist failed!", e);
		}
	}

	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<NewMemberPlaylistSong> songs)
	{
		if (songs.isEmpty())
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Please add songs first!")
				.setColor(Color.RED);
			event.getChannel().sendMessage(embed);
		}
		else
		{
			try
			{
				if (!serverPlayer.containsKey(event.getServer().get()))
				{
					tryToJoinFirstTime(event, serverPlayer, songs);
				}
				else
				{
					tryToJoin(event, serverPlayer, songs);
				}
				event.getChannel().type().get();
				event.getMessage().addReaction("👍");
				tryToSendQueueEmbed(event, serverPlayer);
			}
			catch (Exception e)
			{
				logger.warn("Error", e);
			}
		}
	}

	private void tryToJoinFirstTime(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<NewMemberPlaylistSong> songs)
	{
		if (event.getMessageAuthor().getConnectedVoiceChannel().isPresent())
		{
			event.getServer().ifPresent(server -> {
				ServerPlayer player = new ServerPlayer();
				serverPlayer.put(server, player);
				player.run(event, songs, false);
			});
		}
		else
		{
			logger.info("User is not in VoicChannel @" + event.getMessageAuthor().getName());
			new SendErrorEmbedAction().run(event, "Please join a VoicChannel first!");
			event.addReactionsToMessage("👎");
		}
	}

	private void tryToJoin(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<NewMemberPlaylistSong> songs)
	{
		event.getServer().ifPresent(server -> {
			if (event.getMessageAuthor().getConnectedVoiceChannel().isPresent())
			{
				ServerPlayer player = serverPlayer.get(server);
				if (player.isAllowd(event.getMessageAuthor().asUser().get()))
				{
					if (player.getConnectedChannel() != null && player.getConnectedChannel().isConnected(BotMainCore.api.getYourself()))
					{
						player.loadCustomPlaylist(songs);
					}
					else
					{
						player.run(event, songs, false);
					}
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
