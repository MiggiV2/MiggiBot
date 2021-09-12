package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;
import java.util.concurrent.ExecutionException;

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

public class LoopCoreAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		event.getServer().ifPresent(server -> {
			ServerPlayer player = serverPlayer.get(server);
			EmbedBuilder embed = new EmbedBuilder();
			if (new IsLeagalCheck().run(event, serverPlayer))
			{
				if (player.isLooping())
				{
					embed.setTitle("Stopped looping the queue!");
				}
				else
				{
					embed.setTitle("Looping the queue!");
				}
				player.loop();
				event.addReactionsToMessage(Emojis.LOOP_BUTTON.getEmoji()).exceptionally(ExceptionLogger.get());
				try
				{
					String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
					MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 20);
					MessageCoolDown.del(embedLink, event.getChannel(), 20);
				}
				catch (AssertionError | InterruptedException | ExecutionException e)
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
			ServerPlayer player = serverPlayer.get(server);
			String content = "";
			if (new IsLeagalCheck().run(event, serverPlayer))
			{
				if (player.isLooping())
				{
					content = "Stopped looping the queue!";
				}
				else
				{
					content = "Looping the queue!";
				}
				player.loop();
				interaction.createImmediateResponder()
					.setContent(content)
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
		});
	}
}
