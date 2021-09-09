package de.mymiggi.discordbot.music.youtube.core.embeds;

import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.SendQueueEmbedAction;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class SendQueueEmbedCoreAction
{
	public void run(MessageCreateEvent event, boolean noCheckNeeded, Map<Server, ServerPlayer> serverPlayer)
	{
		Server server = event.getServer().get();
		if (noCheckNeeded || new IsLeagalCheck().run(event, serverPlayer))
		{
			serverPlayer.get(server).sendQueueEmbed();
			event.addReactionsToMessage(Emojis.PENCIL.getEmoji()).exceptionally(ExceptionLogger.get());
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 20);
		}
	}

	public void run(SlashCommandCreateEvent event, boolean noCheckNeeded, Map<Server, ServerPlayer> serverPlayer)
	{
		run(event, noCheckNeeded, false, serverPlayer);
	}

	public void run(SlashCommandCreateEvent event, boolean noCheckNeeded, boolean sendResponse, Map<Server, ServerPlayer> serverPlayer)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getServer().ifPresent(server -> {
			if (noCheckNeeded || new IsLeagalCheck().run(event, serverPlayer))
			{
				Queue queue = serverPlayer.get(server).getQueue();
				if (sendResponse)
				{
					interaction.createImmediateResponder()
						.setContent("Queue:")
						.addEmbed(new SendQueueEmbedAction().getEmbed(queue))
						.respond();
				}
				else
				{
					serverPlayer.get(server).sendQueueEmbed();
				}
			}
		});
	}

	public void run(Server server, User user, TextChannel channel, boolean noCheckNeeded, Map<Server, ServerPlayer> serverPlayer)
	{
		if (noCheckNeeded || new IsLeagalCheck().run(server, channel, user, serverPlayer));
		{
			serverPlayer.get(server).sendQueueEmbed();
		}
	}
}
