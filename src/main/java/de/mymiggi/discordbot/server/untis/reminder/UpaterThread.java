package de.mymiggi.discordbot.server.untis.reminder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.TimeCalculator;
import de.mymiggi.webuntis.WebUntisClient;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class UpaterThread
{
	private static Logger logger = LoggerFactory.getLogger(UpaterThread.class.getSimpleName());

	public void run(WebUntisResponse response, String schoolName)
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				work(response, schoolName);
			}
		};
		thread.start();
	}

	public void work(WebUntisResponse response, String schoolName)
	{
		try
		{
			new TimeCalculator().sleepTill(6);
		}
		catch (InterruptedException e)
		{
			logger.warn("Sleep till 6 failed!", e);
		}
		response = new WebUntisClient().getResponse(schoolName);
		boolean running = true;
		while (running)
		{
			try
			{
				Thread.sleep(24 * 60 * 60 * 1000);
			}
			catch (InterruptedException e)
			{
				logger.warn("Sleep 24h failed!", e);
				logger.warn("Stopping thread!");
				running = false;
			}
			response = new WebUntisClient().getResponse(schoolName);
		}
	}
}
