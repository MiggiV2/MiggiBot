package de.mymiggi.discordbot.server.r6.map.update;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.discordbot.server.r6.map.update.actions.AbstractUpdateAction;
import de.mymiggi.discordbot.server.r6.map.update.actions.UpdateImageURLAction;
import de.mymiggi.discordbot.server.r6.map.update.actions.UpdateIsRankedPoolAction;
import de.mymiggi.discordbot.server.r6.map.update.actions.UpdateNameAction;

public class BuildActionList
{
	public List<AbstractUpdateAction> build()
	{
		List<AbstractUpdateAction> actions = new ArrayList<AbstractUpdateAction>();
		actions.add(new UpdateImageURLAction());
		actions.add(new UpdateIsRankedPoolAction());
		actions.add(new UpdateNameAction());
		return actions;
	}
}
