package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;
import de.mymiggi.discordbot.music.youtube.core.helpers.BuildStringAction;
import de.mymiggi.discordbot.music.youtube.core.helpers.CheckURLAction;
import de.mymiggi.discordbot.music.youtube.core.helpers.StartPlayingAction;
import de.mymiggi.discordbot.music.youtube.util.QueryResponse;

public class PlayCoreAction
{
	private static Logger logger = LoggerFactory.getLogger(PlayCoreAction.class.getSimpleName());

	public void run(MessageCreateEvent event, String[] context, Map<Server, ServerPlayer> serverPlayer, boolean queryIsPlayist, boolean toPushInQueue, boolean suppressEmbeds)
	{
		event.getServer().ifPresent(server -> {
			if (!event.getMessageAuthor().getConnectedVoiceChannel().isPresent())
			{
				logger.info("User is not in VoicChannel");
				new SendErrorEmbedAction().run(event, "Please join a VoicChannel first!");
			}
			else
			{
				String searchStr = new BuildStringAction().run(context);
				QueryResponse queryResponse = new CheckURLAction().run(searchStr);
				if (!serverPlayer.containsKey(server) || serverPlayer.get(server).getConnectedChannel() == null)
				{
					ServerPlayer player = new ServerPlayer();
					serverPlayer.put(server, player);
					logger.info("Created musicplayer for server " + server.getName());
					logger.info(event.getMessageAuthor().getName() + " used used the bot!");
					new StartPlayingAction().run(event, serverPlayer, queryResponse, false, queryIsPlayist, toPushInQueue, suppressEmbeds);
				}
				else
				{
					ServerPlayer player = serverPlayer.get(server);
					if (player.isAllowed(event.getMessageAuthor().asUser().get()))
					{
						new StartPlayingAction().run(event, serverPlayer, queryResponse, true, queryIsPlayist, toPushInQueue, suppressEmbeds);
					}
					else
					{
						new SendErrorEmbedAction().run(event, "I'm sorry, but im used in a different voice channel!");
					}
				}
			}
		});
	}

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer, boolean queryIsPlayist, boolean toPushInQueue, boolean suppressEmbeds)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		interaction.getServer().ifPresent(server -> {
			if (!interaction.getUser().getConnectedVoiceChannel(server).isPresent())
			{
				logger.info("User is not in VoicChannel");
				respond(interaction, "Please join a VoicChannel first!");
			}
			else
			{
				String searchStr = interaction.getFirstOptionStringValue().orElse("https://youtu.be/jeg_TJvkSjg");
				QueryResponse queryResponse = new CheckURLAction().run(searchStr);
				if (!serverPlayer.containsKey(server) || serverPlayer.get(server).getConnectedChannel() == null)
				{
					ServerPlayer player = new ServerPlayer();
					serverPlayer.put(server, player);
					logger.info("Created musicplayer for server " + server.getName());
					logger.info(interaction.getUser().getName() + " used used the bot!");
					new StartPlayingAction().run(event, serverPlayer, queryResponse, false, queryIsPlayist, toPushInQueue, suppressEmbeds);
					respond(interaction, "Have fun ;D");
				}
				else
				{
					ServerPlayer player = serverPlayer.get(server);
					if (player.isAllowed(interaction.getUser()))
					{
						new StartPlayingAction().run(event, serverPlayer, queryResponse, true, queryIsPlayist, toPushInQueue, suppressEmbeds);
						respond(interaction, "Have fun ;D");
					}
					else
					{
						respond(interaction, "I'm sorry, but im used in a different voice channel!");
					}
				}
			}
		});
	}

	private void respond(SlashCommandInteraction interaction, String content)
	{
		interaction.createFollowupMessageBuilder()
			.setContent(content)
			.send();
	}
}
