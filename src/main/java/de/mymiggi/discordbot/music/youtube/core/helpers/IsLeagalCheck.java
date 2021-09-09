package de.mymiggi.discordbot.music.youtube.core.helpers;

import java.util.Map;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;

public class IsLeagalCheck
{
	private static Logger logger = LoggerFactory.getLogger(IsLeagalCheck.class.getSimpleName());
	private boolean response;

	public boolean run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		this.response = false;
		event.getServer().ifPresent(server -> {
			this.response = run(server, event.getChannel(), event.getMessageAuthor().asUser().get(), serverPlayer);
		});
		return response;
	}

	public boolean run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		this.response = false;
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getServer().ifPresent(server -> {
			interaction.getChannel().ifPresent(channel -> {
				this.response = run(server, channel, interaction.getUser(), serverPlayer);
			});
		});
		return response;
	}

	public boolean run(Server server, TextChannel channel, User user, Map<Server, ServerPlayer> serverPlayer)
	{
		if (!serverPlayer.containsKey(server))
		{
			logger.info("Server Player not found!");
			new SendErrorEmbedAction().run(channel, "Server Player not found!");
			return false;
		}
		ServerPlayer player = serverPlayer.get(server);
		if (!player.isAllowed(user))
		{
			logger.info("Please join the VoicChannel first! @" + user.getName());
			new SendErrorEmbedAction().run(channel, "Please join the VoicChannel first!");
			return false;
		}
		ServerVoiceChannel vc = player.getConnectedChannel();
		if (!BotMainCore.api.getYourself().isConnected(vc))
		{
			logger.info("Bot not the VoicChannel yet!");
			new SendErrorEmbedAction().run(channel, "Please add some songs first!");
			return false;
		}
		if (vc.getId() != player.getConnectedChannel().getId())
		{
			logger.info("Different voice channel!");
			new SendErrorEmbedAction().run(channel, "The bot is used in a different voice channel!");
			return false;
		}
		return true;
	}
}
