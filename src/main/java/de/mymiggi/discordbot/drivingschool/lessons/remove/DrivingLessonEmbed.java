package de.mymiggi.discordbot.drivingschool.lessons.remove;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.tools.database.util.DrivingLesson;

public class DrivingLessonEmbed
{
	public EmbedBuilder build(List<DrivingLesson> usersLessons)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Your lessons")
			.setDescription(buildDescriptionString(usersLessons))
			.setColor(Color.YELLOW);
		return embed;
	}

	private String buildDescriptionString(List<DrivingLesson> usersLessons)
	{
		String description = "";
		for (int i = 0; i < usersLessons.size() - 1; i++)
		{
			/*
			 * Format date
			 */
			DrivingLesson tempLesson = usersLessons.get(i);
			description += String.format("%s at %s\r\n", tempLesson.getDate(), tempLesson.getTime());
		}
		DrivingLesson tempLesson = usersLessons.get(usersLessons.size() - 1);
		description += String.format("%s at %s\r\n", tempLesson.getDate(), tempLesson.getTime());
		return description;
	}
}
