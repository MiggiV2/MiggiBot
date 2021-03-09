package de.mymiggi.discordbot.server.r6.map.list;

import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.tools.database.util.R6Map;

public class ListR6MapsAction
{
	public void run(List<R6Map> mapList, MessageCreateEvent event)
	{
		event.getMessage()
			.addReaction("✅");
		event.getChannel()
			.sendMessage(new MapListEmbed().build(mapList))
			.thenAccept(message -> {
				event.getMessage().addReactionAddListener(reactionAddEvent -> {
					new MapListEmbedReactionHandler().run(reactionAddEvent, message);
				});
			});
	}
}
