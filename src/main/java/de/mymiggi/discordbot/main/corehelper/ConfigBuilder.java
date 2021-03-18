package de.mymiggi.discordbot.main.corehelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotConfig;

public class ConfigBuilder
{
	private static Logger logger = LoggerFactory.getLogger(ConfigBuilder.class);

	public BotConfig run()
	{
		BotConfig config = new BotConfig();
		Map<String, String> configMap = read("bot.config");
		config.setBotToken(configMap.get("botToken"));
		config.setPrefix(configMap.get("botPrefix"));
		config.setTenorAPIKey(configMap.get("tenorAPIKey"));
		config.setUntisSchoolName(configMap.get("untisSchoolName"));
		config.setYtAPIKey(configMap.get("youtubeAPIKey"));
		return config;
	}

	public Map<String, String> read(String fileName)
	{
		Map<String, String> configMap = new HashMap<String, String>();
		try
		{
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine())
			{
				String line = myReader.nextLine();
				String[] keyAndValue = line.split("=");
				if (keyAndValue.length != 2 && line.length() > 2)
				{
					System.err.println("Config should not be empty!");
					System.err.println("Error in line: " + line);
					logger.error("Config should not be empty!");
					logger.error("Error in line: " + line);
					break;
				}
				configMap.put(keyAndValue[0], keyAndValue[1]);
			}
			myReader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return configMap;
	}
}
