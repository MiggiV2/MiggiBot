package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
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
					if (player.isAllowd(event.getMessageAuthor().asUser().get()))
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
}
