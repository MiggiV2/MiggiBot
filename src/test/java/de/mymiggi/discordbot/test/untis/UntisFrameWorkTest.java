package de.mymiggi.discordbot.test.untis;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.webuntis.WebUntisClient;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

class UntisFrameWorkTest
{
	@Test
	void test() throws Exception
	{
		BotMainCore.config.getUntisSchoolName().ifPresent(schoolName -> {
			WebUntisResponse response = new WebUntisClient().getResponse(schoolName);
			LessonPeriod[] lessons = response.getLessons();
			System.out.println(String.format("Got %s lessons!", lessons.length));
			assertTrue(response.getLessonInfos().length != 0);
			assertFalse(response.getTimetables().isEmpty());
			if (lessons.length == 0)
			{
				System.out.println("No subjects found!");
			}
			else
			{
				assertNotNull(getRandomSubject(response));
				assertNotEquals(getRandomSubject(response), "");
				for (int i = 0; i < 5; i++)
				{
					System.out.println("Random subject: " +
						getRandomSubject(response));
				}
			}
			System.out.println("Test passed!");
		});
	}

	private String getRandomSubject(WebUntisResponse response)
	{
		LessonPeriod[] lessons = response.getLessons();
		int randomIndex = (int)(lessons.length * Math.random());
		return lessons[randomIndex].getSubjekt(response).getLongName();
	}
}
