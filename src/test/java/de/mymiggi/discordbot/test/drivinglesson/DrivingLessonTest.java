package de.mymiggi.discordbot.test.drivinglesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.drivingschool.lessons.create.DrivingLessonBuilder;
import de.mymiggi.discordbot.drivingschool.lessons.create.DrivingLessonSaveAction;
import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.DrivingLesson;

class DrivingLessonTest
{

	@Test
	void builderTest() throws Exception
	{
		String message = "Terminbestätigung der Fahrstunde am 10.03.2021 um 07:00 (90 Minuten).";
		DrivingLesson lesson = new DrivingLessonBuilder().run(message);
		assertNotNull(lesson.getDate());
		assertNotNull(lesson.getTime());
		assertEquals(lesson.getMeetingPlace(), "");

		message = "Terminbestätigung der Fahrstunde am 30.11.2020 um 06:30 (90 Minuten). Treffpunkt: schule";
		lesson = new DrivingLessonBuilder().run(message);
		assertNotNull(lesson.getDate());
		assertNotNull(lesson.getTime());
		assertNotEquals(lesson.getMeetingPlace(), "");

		message = "Terminbestätigung der Fahrstunde am 30.11.2020 um 06:30 (45 Minuten). Treffpunkt: An der Schule";
		lesson = new DrivingLessonBuilder().run(message);
		assertNotNull(lesson.getDate());
		assertNotNull(lesson.getTime());
		assertNotEquals(lesson.getMeetingPlace(), "");
	}

	@Test
	void savingTest()
	{
		BotMainCore.api.getChannelById("813687976927297568").ifPresent(channel -> {

			int lessonsBefore = new UniversalHibernateClient().getList(DrivingLesson.class).size();
			String message = "Terminbestätigung der Fahrstunde am 10.03.2021 um 07:00 (90 Minuten).";
			new DrivingLessonSaveAction().run(message, channel.asTextChannel().get(), BotMainCore.api.getOwnerId());
			int lessonsAfter = new UniversalHibernateClient().getList(DrivingLesson.class).size();
			assertNotEquals(lessonsAfter, lessonsBefore);

			lessonsBefore = new UniversalHibernateClient().getList(DrivingLesson.class).size();
			message = "Terminbestätigung der Fahrstunde am 30.11.2020 um 06:30 (45 Minuten). Treffpunkt: An der Schule";
			new DrivingLessonSaveAction().run(message, channel.asTextChannel().get(), BotMainCore.api.getOwnerId());
			lessonsAfter = new UniversalHibernateClient().getList(DrivingLesson.class).size();
			assertNotEquals(lessonsAfter, lessonsBefore);
		});
	}
}
