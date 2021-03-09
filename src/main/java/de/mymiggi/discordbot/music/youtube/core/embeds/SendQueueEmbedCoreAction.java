package de.mymiggi.discordbot.music.youtube.core.embeds;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
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
}
