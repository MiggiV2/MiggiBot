package de.mymiggi.discordbot.tools.ytclients;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.FileManager;

public class LoadMyYTTokenAction
{
	private static Logger logger = LoggerFactory.getLogger(LoadMyYTTokenAction.class.getSimpleName());

	public String run()
	{
		FileManager manager = new FileManager();
		File keyFile = new File(System.getProperty("user.dir") + "/YTKey.bot");
		if (!keyFile.exists())
		{
			try
			{
				keyFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			logger.error("Please enter a bot token first! YTKey.bot");
			System.exit(0);
		}
		String token = manager.read("YTKey.bot");
		if (token.isEmpty())
		{
			logger.error("Please enter a bot token first! YTKey.bot");
			System.exit(0);
		}
		return token;
	}
}
