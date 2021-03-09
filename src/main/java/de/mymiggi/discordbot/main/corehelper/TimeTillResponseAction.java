package de.mymiggi.discordbot.main.corehelper;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;

public class TimeTillResponseAction
{
	private static Logger logger = LoggerFactory.getLogger(TimeTillResponseAction.class.getSimpleName());

	public static void run()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				long startStamp = System.currentTimeMillis();
				DecimalFormat df = new DecimalFormat("#,###.##");
				logger.info("Connecting ...");

				while (BotMainCore.api == null)
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}

				long time = System.currentTimeMillis() - startStamp;
				logger.info("After " + df.format(time) + "s connected!");
			}
		};
		thread.start();
	}
}
