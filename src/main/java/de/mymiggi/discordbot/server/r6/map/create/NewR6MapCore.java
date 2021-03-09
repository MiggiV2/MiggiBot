package de.mymiggi.discordbot.server.r6.map.create;

import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.server.r6.map.create.handler.StartNewMapHandler;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class NewR6MapCore
{
	public void run(MessageCreateEvent messageEvent)
	{
		new StartNewMapHandler().run(messageEvent, new R6Map(), null);
	}
}
