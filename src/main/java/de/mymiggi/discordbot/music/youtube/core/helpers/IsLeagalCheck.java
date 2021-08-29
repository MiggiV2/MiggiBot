package de.mymiggi.discordbot.music.youtube.core.helpers;

import java.util.Map;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;

public class IsLeagalCheck
{
	private static Logger logger = LoggerFactory.getLogger(IsLeagalCheck.class.getSimpleName());
	private Server server;

	public boolean run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		event.getServer().ifPresent(server -> {
			this.server = server;
		});
		if (!serverPlayer.containsKey(server))
		{
			logger.info("Server Player not found!");
			new SendErrorEmbedAction().run(event, "Server Player not found!");
			return false;
		}
		ServerPlayer player = serverPlayer.get(server);
		if (!player.isAllowed(event.getMessageAuthor().asUser().get()))
		{
			logger.info("Please join the VoicChannel first! @" + event.getMessageAuthor().getName());
			new SendErrorEmbedAction().run(event, "Please join the VoicChannel first!");
			return false;
		}
		ServerVoiceChannel channel = player.getConnectedChannel();
		if (!BotMainCore.api.getYourself().isConnected(channel))
		{
			logger.info("Bot not the VoicChannel yet!");
			new SendErrorEmbedAction().run(event, "Please add some songs first!");
			return false;
		}
		if (channel.getId() != player.getConnectedChannel().getId())
		{
			logger.info("Different voice channel!");
			new SendErrorEmbedAction().run(event, "The bot is used in a different voice channel!");
			return false;
		}
		return true;
	}
}
