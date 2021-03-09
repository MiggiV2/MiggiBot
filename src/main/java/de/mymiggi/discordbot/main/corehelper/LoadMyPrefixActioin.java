package de.mymiggi.discordbot.main.corehelper;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.FileManager;

public class LoadMyPrefixActioin
{
	private static Logger logger = LoggerFactory.getLogger(LoadMyPrefixActioin.class.getSimpleName());

	public static String run()
	{
		FileManager manager = new FileManager();
		File prefixFile = new File(System.getProperty("user.dir") + "/prefix.bot");

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
			logger.error("Please enter a bot prefix first! prefix.bot");
			System.exit(0);
		}

		String prefix = manager.read("prefix.bot");

		if (prefix.isEmpty())
		{
			logger.error("Please enter a bot prefix first! prefix.bot");
			System.exit(0);
		}

		return prefix;
	}
}
