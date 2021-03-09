package de.mymiggi.discordbot.tools.util;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeCalculator
{
	private Logger logger = LoggerFactory.getLogger(TimeCalculator.class.getSimpleName());

	public void sleepTill(int timeToReach) throws InterruptedException
	{
		int nowHour = LocalDateTime.now().getHour();
		for (int i = 0; i < timeToReach; i++)
		{
			if (nowHour == i)
			{
				Thread.sleep((timeToReach - i) * 60 * 60 * 1000);
			}
		}
		if (nowHour > timeToReach)
		{
			int houersToSleep = (24 + timeToReach) - nowHour;
			logger.info("Sleeping " + houersToSleep + "h");
			Thread.sleep(houersToSleep * 60 * 60 * 1000);
		}
	}
}
