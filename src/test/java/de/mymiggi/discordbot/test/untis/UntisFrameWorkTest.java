package de.mymiggi.discordbot.test.untis;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.mymiggi.webuntis.WebUntisClient;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

class UntisFrameWorkTest
{
	@Test
	void test() throws Exception
	{
		WebUntisResponse response = new WebUntisClient().getResponse();
		LessonPeriod[] lessons = response.getTimetable().get("195");
		System.out.println(String.format("Got %s lessons!", lessons.length));
		assertTrue(response.getLessonInfos().length != 0);
		assertFalse(response.getTimetable().isEmpty());
		assertNotNull(getRandomSubject(response));
		assertNotEquals(getRandomSubject(response), "");
		for (int i = 0; i < 5; i++)
		{
			System.out.println("Random subject: " + getRandomSubject(response));
		}
		System.out.println("Test passed!");
	}

	private String getRandomSubject(WebUntisResponse response)
	{
		LessonPeriod[] lessons = response.getTimetable().get("195");
		int randomIndex = (int)(lessons.length * Math.random());
		return lessons[randomIndex].getSubjekt(response).getLongName();
	}
}
