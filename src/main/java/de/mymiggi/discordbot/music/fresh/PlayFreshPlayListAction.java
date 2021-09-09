package de.mymiggi.discordbot.music.fresh;

import java.util.List;
import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.fresh.entitys.FreshSong;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.DMCheckAction;

public class PlayFreshPlayListAction
{
	private Logger logger = LoggerFactory.getLogger(PlayFreshPlayListAction.class.getSimpleName());

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		try
		{
			List<FreshSong> songs = new FreshPlayer().loadSongs();
			interaction.getServer().ifPresent(server -> {
				if (!serverPlayer.containsKey(server))
				{
					tryToJoinFirstTime(event, serverPlayer, songs);
				}
				else
				{
					tryToJoin(event, serverPlayer, songs);
				}
				tryToSendQueueEmbed(server, serverPlayer);
			});
			new DMCheckAction().run(interaction);
		}
		catch (Exception e)
		{
			logger.warn("Error", e);
			interaction.createImmediateResponder()
				.setContent("Failed! error:" + e.getClass())
				.respond();
		}
	}

	private void tryToJoinFirstTime(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<FreshSong> songs)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getServer().ifPresent(server -> {
			if (interaction.getUser().getConnectedVoiceChannel(server).isPresent())
			{
				ServerPlayer player = new ServerPlayer();
				serverPlayer.put(server, player);
				player.runFreshPlaylist(event, songs, false);
				interaction.getChannel().ifPresent(channel -> channel.type());
				interaction.createImmediateResponder()
					.setContent("Have fun ;D")
					.respond();
			}
			else
			{
				logger.info("User is not in VoicChannel @" + interaction.getUser());
				interaction.createImmediateResponder()
					.setContent("Please join a VoicChannel first!")
					.respond();
			}
		});
	}

	private void tryToJoin(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer, List<FreshSong> songs)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getServer().ifPresent(server -> {
			if (interaction.getUser().getConnectedVoiceChannel(server).isPresent())
			{
				ServerPlayer player = serverPlayer.get(server);
				if (player.isAllowed(interaction.getUser()))
				{
					if (player.getConnectedChannel() != null && player.getConnectedChannel().isConnected(BotMainCore.api.getYourself()))
					{
						player.loadFreshPlaylist(songs);
					}
					else
					{
						player.runFreshPlaylist(event, songs, false);
					}
					interaction.getChannel().ifPresent(channel -> channel.type());
					interaction.createImmediateResponder()
						.setContent("Have fun ;D")
						.respond();
				}
				else
				{
					logger.info("In a different voice channel! @" + interaction.getUser().getName());
					interaction.createImmediateResponder()
						.setContent("I'm sorry, but im used in a different voice channel!")
						.respond();
				}
			}
			else
			{
				logger.info("User is not in VoicChannel @" + interaction.getUser().getName());
				interaction.createImmediateResponder()
					.setContent("Please join a VoicChannel first!")
					.respond();
			}
		});
	}

	private void tryToSendQueueEmbed(Server server, Map<Server, ServerPlayer> serverPlayer)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(1500);
					ServerPlayer player = serverPlayer.get(server);
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