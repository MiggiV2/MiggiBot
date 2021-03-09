package de.mymiggi.discordbot.main.corehelper;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.FileManager;

public class LoadMyTokenAction
{
	private static Logger logger = LoggerFactory.getLogger(LoadMyTokenAction.class.getSimpleName());

	public static String run(boolean printTime)
	{
		FileManager manager = new FileManager();
		File prefixFile = new File(System.getProperty("user.dir") + "/token.bot");

		if (!prefixFile.exists())
		{
			try
			{
				prefixFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			logger.error("Please enter a bot token first! prefix.bot");
			System.exit(0);
		}

		String token = manager.read("token.bot");

		if (token.isEmpty())
		{
			logger.error("Please enter a bot token first! prefix.bot");
			System.exit(0);
		}
		if (printTime)
		{
			TimeTillResponseAction.run();
		}
		return token;
	}
}
