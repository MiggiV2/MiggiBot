package de.mymiggi.discordbot.server.r6.map;

import java.util.List;

import org.javacord.api.event.interaction.SlashCommandCreateEvent;

import de.mymiggi.discordbot.server.r6.map.create.NewR6MapCore;
import de.mymiggi.discordbot.server.r6.map.get.GetRandomMapAction;
import de.mymiggi.discordbot.server.r6.map.list.ListR6MapsAction;
import de.mymiggi.discordbot.server.r6.map.update.UpdateR6MapAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class RandomR6MapCore
{
	private List<R6Map> mapList = new UniversalHibernateClient().getList(R6Map.class);

	public void get(SlashCommandCreateEvent event)
	{
		new GetRandomMapAction().run(event, mapList);
	}

	public void list(SlashCommandCreateEvent event)
	{
		new ListR6MapsAction().run(mapList, event);
	}

	public void update(SlashCommandCreateEvent event)
	{
		new UpdateR6MapAction().run(event, mapList);
	}

	public void add(SlashCommandCreateEvent event)
	{
		new NewR6MapCore().run(event);
	}
}
