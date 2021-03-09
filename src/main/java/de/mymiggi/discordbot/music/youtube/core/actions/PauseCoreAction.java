package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class PauseCoreAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		TextChannel textChannel = event.getChannel();
		event.getServer().ifPresent(server -> {
			ServerPlayer player = null;
			if (serverPlayer.containsKey(server))
			{
				player = serverPlayer.get(server);
			}
			EmbedBuilder embed = new EmbedBuilder();
			if (new IsLeagalCheck().run(event, serverPlayer))
			{
				player.pause();
				try
				{
					embed.setTitle("Paused song " + player.getCurrentTrack().getInfo().title);
				}
				catch (Exception e1)
				{
					embed.setTitle("I'm sorry. We can't find our song");
				}
				try
				{
					event.addReactionsToMessage(Emojis.ARROW_FORWARD.getEmoji()).exceptionally(ExceptionLogger.get());
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
}
