package de.mymiggi.r6.stats.wrapper.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WrapperConfigBuilder
{
	private Logger logger = LoggerFactory.getLogger(WrapperConfigBuilder.class);

	public WrapperConfig build(File configFile) throws Exception
	{
		List<String> content = getConfigContent(configFile);
		return buildConfig(content);
	}

	private WrapperConfig buildConfig(List<String> content) throws Exception
	{
		WrapperConfig config = new WrapperConfig();
		for (String tempLine : content)
		{
			if (tempLine.startsWith("ubisoft-email"))
			{
				config.setEMail(getConfigValue(tempLine));
			}
			if (tempLine.startsWith("ubisoft-password"))
			{
				config.setPassword(getConfigValue(tempLine));
			}
			if (tempLine.startsWith("ubisoft-credential"))
			{
				config.setCredential(getConfigValue(tempLine));
			}
		}
		if (config.getEMail() != null && config.getPassword() != null || config.getClass() != null)
		{
			return config;
		}
		throw new Exception("No email + pw or credential found!");
	}

	private String getConfigValue(String tempLine)
	{
		String[] splitted = tempLine.split("=", 2);
		String value = "";
		for (int i = 1; i < splitted.length; i++)
		{
			value += splitted[i];
		}
		return value;
	}

	private List<String> getConfigContent(File confgFile)
	{
		List<String> content = new ArrayList<String>();
		try
		{
			Scanner myReader = new Scanner(confgFile);
			while (myReader.hasNextLine())
			{
				String data = myReader.nextLine();
				if (!data.startsWith("#"))
				{
					content.add(data);
				}
			}
			myReader.close();
		}
		catch (FileNotFoundException e)
		{
			logger.error("An error occurred!", e);
		}
		return content;
	}
}
