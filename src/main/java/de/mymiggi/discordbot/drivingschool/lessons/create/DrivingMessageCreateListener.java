package de.mymiggi.discordbot.drivingschool.lessons.create;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

public class DrivingMessageCreateListener
{
	public void run(MessageCreateEvent event, ListenerManager<MessageCreateListener> listener)
	{
		if (!event.getMessageAuthor().isYourself())
		{
			new DrivingLessonSaveAction().run(event);
			listener.remove();
		}
	}
}
