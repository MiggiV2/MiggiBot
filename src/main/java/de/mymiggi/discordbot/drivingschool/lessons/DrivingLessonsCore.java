package de.mymiggi.discordbot.drivingschool.lessons;

import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.drivingschool.lessons.create.StartDrivingMessageListener;
import de.mymiggi.discordbot.drivingschool.lessons.remove.DrivingLessonRemoveCore;

public class DrivingLessonsCore
{
	public void beginSavingAction(MessageCreateEvent event)
	{
		new StartDrivingMessageListener().run(event);
	}

	public void remove(MessageCreateEvent event)
	{
		new DrivingLessonRemoveCore().run(event);
	}
}
