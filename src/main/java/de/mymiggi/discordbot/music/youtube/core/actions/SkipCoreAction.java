package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class SkipCoreAction
{
	public void run(MessageCreateEvent event, String[] context, Map<Server, ServerPlayer> serverPlayer)
	{
		event.getServer().ifPresent(server -> {
			int skipTo = 1;
			if (context.length == 2)
			{
				skipTo = Integer.parseInt(context[1]);
			}
			if (new IsLeagalCheck().run(event, serverPlayer))
			{
				ServerPlayer player = serverPlayer.get(server);
				MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 20);
				player.next(skipTo, true);
				event.addReactionsToMessage(Emojis.FAST_FORWARD.getEmoji()).exceptionally(ExceptionLogger.get());
			}
		});
	}
}
