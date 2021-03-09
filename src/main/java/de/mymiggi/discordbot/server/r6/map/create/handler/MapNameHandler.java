package de.mymiggi.discordbot.server.r6.map.create.handler;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class MapNameHandler extends AbstractHandler
{
	private ListenerManager<MessageCreateListener> imageURLListener;

	@Override
	public void handle(MessageCreateEvent messageEvent)
	{
		getMapToAdd().setName(messageEvent.getMessageContent());
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Listen for imageURL!")
			.setDescription("Name: " + getMapToAdd().getName())
			.setColor(Color.GREEN);
		messageEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), embedMessage.getChannel(), 6);
			});
		imageURLListener = messageEvent.getChannel()
			.addMessageCreateListener(nextMessageEvent -> {
				new MapImageURLHandler().run(nextMessageEvent, getMapToAdd(), imageURLListener);
			});
	}
}
