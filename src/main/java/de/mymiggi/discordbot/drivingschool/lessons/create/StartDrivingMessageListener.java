package de.mymiggi.discordbot.drivingschool.lessons.create;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class StartDrivingMessageListener
{
	private ListenerManager<MessageCreateListener> listener;

	public void run(MessageCreateEvent event)
	{
		listener = event.getChannel()
			.addMessageCreateListener(newCreateEvent -> {
				new DrivingMessageCreateListener().run(newCreateEvent, listener);
			});
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Now im listening!")
			.setDescription("Please enter your message")
			.setColor(Color.GREEN);
		event.getMessage()
			.addReaction("👍");
		event.getChannel()
			.sendMessage(embed)
			.thenAccept(message -> {
				MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 6);
			});
	}
}
