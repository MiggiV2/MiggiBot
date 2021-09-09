package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ShuffleCoreAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		event.getServer().ifPresent(server -> {
			ServerPlayer player = null;
			if (serverPlayer.containsKey(server))
			{
				player = serverPlayer.get(server);
			}
			if (new IsLeagalCheck().run(event, serverPlayer))
			{
				player.shuffle();
				MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 20);
				event.addReactionsToMessage(Emojis.TWISTED_RIGHTWARDS_ARROWS.getEmoji()).exceptionally(ExceptionLogger.get());
			}
		});
	}

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getServer().ifPresent(server -> {
			ServerPlayer player = null;
			if (serverPlayer.containsKey(server))
			{
				player = serverPlayer.get(server);
				if (new IsLeagalCheck().run(event, serverPlayer))
				{
					player.shuffle();
					interaction.createImmediateResponder().setContent("Shuffled ;D").respond();
				}
				else
				{
					interaction.createImmediateResponder().setContent("You are not allowed to do this! Join the vc ;D").respond();
				}
			}
			else
			{
				interaction.createImmediateResponder().setContent("Soemthing went wrong! Try later again!").respond();
			}
		});
	}
}
