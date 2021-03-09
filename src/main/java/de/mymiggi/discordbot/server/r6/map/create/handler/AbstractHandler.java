package de.mymiggi.discordbot.server.r6.map.create.handler;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.server.r6.map.create.NotAllowedAction;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public abstract class AbstractHandler
{
	private R6Map mapToAdd;

	public abstract void handle(MessageCreateEvent messageEvent);

	public void run(MessageCreateEvent messageEvent, R6Map mapToAdd, ListenerManager<MessageCreateListener> imageListener)
	{
		if (!messageEvent.getMessageAuthor().isYourself())
		{
			if (messageEvent.getMessageAuthor().isBotOwner())
			{
				this.mapToAdd = mapToAdd;
				handle(messageEvent);
				if (imageListener != null)
				{
					imageListener.remove();
				}
				if (messageEvent.getMessage().canYouDelete())
				{
					MessageCoolDown.del(messageEvent.getMessage().getLink().toString(), messageEvent.getChannel(), 2);
				}
			}
			else
			{
				new NotAllowedAction().run(messageEvent);
			}
		}
	}

	public R6Map getMapToAdd()
	{
		return mapToAdd;
	}
}
