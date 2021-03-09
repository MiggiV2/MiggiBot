package de.mymiggi.discordbot.server.r6.map.update.listener;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class NameUpdateListener
{
	public void run(MessageCreateEvent event, R6Map map, ListenerManager<MessageCreateListener> listener)
	{
		if (event.getMessageContent().length() > 1)
		{
			map.setName(event.getMessageContent());
			event.addReactionsToMessage("👍");
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Listener closed! Click :white_check_mark: to save!")
				.setDescription("Click :one: again to change again.")
				.setColor(Color.GREEN);
			event.getChannel()
				.sendMessage(embed)
				.thenAccept(message -> {
					MessageCoolDown.del(message.getLink().toString(), event.getChannel(), 4);
				});
			listener.remove();
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 6);
		}
	}
}
