package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class StopCoreAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		TextChannel textChannel = event.getChannel();
		event.getServer().ifPresent(server -> {
			EmbedBuilder embed = new EmbedBuilder();
			ServerPlayer player = null;
			if (serverPlayer.containsKey(server))
			{
				player = serverPlayer.get(server);
			}
			if (new IsLeagalCheck().run(event, serverPlayer))
			{
				player.stop();
				embed.setTitle("See u later :wave:");
				try
				{
					event.addReactionsToMessage(Emojis.WAVE.getEmoji()).exceptionally(ExceptionLogger.get());
					String messageLink = event.getChannel().asTextChannel().get().sendMessage(embed).get().getLink().toString();
					MessageCoolDown.del(messageLink, textChannel);
					MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 20);
				}
				catch (InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
				}
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
					player.stop();
					interaction.createImmediateResponder()
						.setContent("See u later :wave:")
						.respond()
						.thenAccept(message -> new RemoveResponseAction().run(message, 5));
				}
				else
				{
					interaction.createImmediateResponder()
						.setContent("You are not allowed to do this! Join the vc ;D")
						.respond()
						.thenAccept(message -> new RemoveResponseAction().run(message, 5));
				}
			}
			else
			{
				interaction.createImmediateResponder()
					.setContent("Something went wrong! Try later again!")
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 5));
			}
		});
	}
}
