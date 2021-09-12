package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class ClearCoreAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		if (new IsLeagalCheck().run(event, serverPlayer))
		{
			event.getServer().ifPresent(server -> {
				ServerPlayer player = serverPlayer.get(server);
				player.stop();
			});
		}
	}

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		if (new IsLeagalCheck().run(event, serverPlayer))
		{
			interaction.getServer().ifPresent(server -> {
				ServerPlayer player = serverPlayer.get(server);
				player.stop();
				interaction.createImmediateResponder()
					.setContent("My job is done!")
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 5));
			});
		}
		else
		{
			interaction.createImmediateResponder()
				.setContent("You are not allowed to do this! Join the vc ;D")
				.respond()
				.thenAccept(message -> new RemoveResponseAction().run(message, 5));
		}
	}
}