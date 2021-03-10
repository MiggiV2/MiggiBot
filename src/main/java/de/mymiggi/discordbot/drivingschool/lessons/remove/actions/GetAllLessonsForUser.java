package de.mymiggi.discordbot.drivingschool.lessons.remove.actions;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.tools.database.util.DrivingLesson;

public class GetAllLessonsForUser
{
	public List<DrivingLesson> get(User user, List<DrivingLesson> allLessons)
	{
		List<DrivingLesson> usersLessons = new ArrayList<DrivingLesson>();
		for (DrivingLesson temp : allLessons)
		{
			if (temp.getUserID() == user.getId())
			{
				usersLessons.add(temp);
			}
		}
		return usersLessons;
	}
}
