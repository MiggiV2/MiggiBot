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
		config.setYtAPIKey(configMap.get("youtubeAPIKey"));
		if (configMap.containsKey("tenorAPIKey"))
		{
			config.setTenorAPIKey(configMap.get("tenorAPIKey"));
		}
		if (configMap.containsKey("untisSchoolName"))
		{
			config.setUntisSchoolName(configMap.get("untisSchoolName"));
		}
		if (configMap.containsKey("ubisoft-email"))
		{
			config.seteMail(configMap.get("ubisoft-email"));
		}
		if (configMap.containsKey("ubisoft-password"))
		{
			config.setPassword(configMap.get("ubisoft-password"));
		}
		if (configMap.containsKey("ubisoft-credential"))
		{
			config.setCredential(configMap.get("ubisoft-credential"));
		}
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
				if (keyAndValue.length <= 1 && line.length() > 2)
				{
					logger.error("Config should not be empty!");
					logger.error("Error in line: " + line);
					break;
				}
				if (keyAndValue.length > 2)
				{
					combineValue(keyAndValue);
				}
				if (keyAndValue[0].equals("ubisoft-credential"))
				{
					keyAndValue[1] += "==";
				}
				configMap.put(keyAndValue[0], keyAndValue[1]);
			}
			myReader.close();
		}
		catch (FileNotFoundException e)
		{
			throw new RuntimeException("Please fill out target/bot.config!");
		}
		return configMap;
	}

	private String[] combineValue(String[] keyAndValue)
	{
		String newValue = "";
		String newKey = keyAndValue[0];
		for (int i = 1; i < keyAndValue.length - 1; i++)
		{
			newValue += keyAndValue[i] + "=";
		}
		newValue += keyAndValue[keyAndValue.length - 1];
		keyAndValue = new String[2];
		keyAndValue[0] = newKey;
		keyAndValue[1] = newValue;
		return keyAndValue;
	}
}
