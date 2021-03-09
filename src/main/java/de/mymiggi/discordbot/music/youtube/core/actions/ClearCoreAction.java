package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.helpers.IsLeagalCheck;

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
}
