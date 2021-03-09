package de.mymiggi.discordbot.server.r6.map.create.handler;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class StartNewMapHandler extends AbstractHandler
{
	private ListenerManager<MessageCreateListener> nameListener;

	@Override
	public void handle(MessageCreateEvent messageEvent)
	{
		messageEvent.getMessage()
			.addReaction("👍");
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Listen for map name!")
			.setColor(Color.GREEN);
		messageEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), embedMessage.getChannel(), 6);
			});
		nameListener = messageEvent.getChannel()
			.addMessageCreateListener(newMessageEvent -> {
				new MapNameHandler().run(newMessageEvent, getMapToAdd(), nameListener);
			});
	}
}
