package de.mymiggi.discordbot.test.untis;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.webuntis.WebUntisClient;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

class UntisFrameWorkTest
{
	private static Logger logger = LoggerFactory.getLogger(UntisFrameWorkTest.class.getSimpleName());

	@Test
	void test() throws Exception
	{
		WebUntisResponse response = new WebUntisClient().getResponse();
		LessonPeriod[] lessons = response.getTimetable().get("195");
		logger.info(String.format("Got %s lessons!", lessons.length));
		assertTrue(response.getLessonInfos().length != 0);
		assertFalse(response.getTimetable().isEmpty());
		assertNotNull(getRandomSubject(response));
		assertNotEquals(getRandomSubject(response), "");
		for (int i = 0; i < 5; i++)
		{
			logger.info("Random subject: " + getRandomSubject(response));
		}
		logger.info("Test passed!");
	}

	private String getRandomSubject(WebUntisResponse response)
	{
		LessonPeriod[] lessons = response.getTimetable().get("195");
		int randomIndex = (int)(lessons.length * Math.random());
		return lessons[randomIndex].getSubjekt(response).getLongName();
	}
}
