package de.mymiggi.discordbot.test.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.CovidChannelConfig;

class CovidChannelTest
{
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private List<CovidChannelConfig> savedConfigList = new ArrayList<CovidChannelConfig>();
	private static Logger logger = LoggerFactory.getLogger(CovidChannelTest.class.getSimpleName());

	@Test
	void test() throws InterruptedException
	{
		for (int i = 0; i < 15; i++)
		{
			createChannelConfig();
		}
		logger.info("Created! Waiting 3sec");
		Thread.sleep(3000);
		deleteSavedConfigList();
		logger.info("Deleted!");
	}

	private void deleteSavedConfigList()
	{
		for (CovidChannelConfig temp : savedConfigList)
		{
			client.delete(temp);
		}
	}

	private void createChannelConfig()
	{
		CovidChannelConfig config = new CovidChannelConfig();
		config.setChannelID(getRandomID());
		config.setServerID(getRandomID());
		config.setServerName(getRandomString());
		config.setChannelName(getRandomString());
		client.save(config);
		savedConfigList.add(config);
	}

	private long getRandomID()
	{
		char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		char[] randomID = new char[15];
		for (int i = 0; i < 15; i++)
		{
			int randomInt = (int)(Math.random() * chars.length);
			randomID[i] = chars[randomInt];
		}
		return Long.parseLong(new String(randomID));
	}

	String getRandomString()
	{
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'X', 'Z' };
		char[] randomStr = new char[8];
		for (int i = 0; i < randomStr.length; i++)
		{
			int randomIndex = (int)(Math.random() * chars.length);
			randomStr[i] = chars[randomIndex];
		}
		return new String(randomStr);
	}
}
