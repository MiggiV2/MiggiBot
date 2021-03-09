package de.mymiggi.discordbot.server.r6.map;

import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.server.r6.map.get.GetRandomMapAction;
import de.mymiggi.discordbot.server.r6.map.list.ListR6MapsAction;
import de.mymiggi.discordbot.server.r6.map.update.UpdateR6MapAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class RandomR6MapCore
{
	private List<R6Map> mapList = new UniversalHibernateClient().getList(R6Map.class);

	public void get(MessageCreateEvent event, String[] context)
	{
		new GetRandomMapAction().run(event.getMessage(), event.getChannel(), context, mapList);
	}

	public void list(MessageCreateEvent event)
	{
		new ListR6MapsAction().run(mapList, event);
	}

	public void update(MessageCreateEvent event, String[] context)
	{
		new UpdateR6MapAction().run(event, context, mapList);
	}
}
