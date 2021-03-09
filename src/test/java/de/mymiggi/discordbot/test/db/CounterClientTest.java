package de.mymiggi.discordbot.test.db;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.CounterSetting;

class CounterClientTest
{
	private static Logger logger = LoggerFactory.getLogger(CounterClientTest.class.getSimpleName());

	@Test
	void test()
	{
		UniversalHibernateClient client = new UniversalHibernateClient();

		logger.info("Creating CounterSetting!");
		createCounterSetting(client);
		createCounterSetting(client);
		createCounterSetting(client);
		System.out.println("");
		logger.info("List CounterSettings!");
		printCounterSettings(client);
		// logger.info("Delete CounterSettings!");
		// deleteTest(client);
	}

	private void printCounterSettings(UniversalHibernateClient client)
	{
		List<CounterSetting> list = client.getList(CounterSetting.class);

		System.out.println("");
		System.out.println("ChannelID----------RoleID-------------ServerID-----------TimeStamp---");
		list.forEach(setting -> {
			System.out.print(setting.getChannelID() + " ");
			System.out.print(setting.getMessageID() + " ");
			System.out.print(setting.getServerID() + " ");
			System.out.println(setting.getTimeStamp() + " ");
		});
		System.out.println("");
	}

	private void createCounterSetting(UniversalHibernateClient client)
	{

		CounterSetting setting = new CounterSetting();
		setting.setChannelID(genRandomID());
		setting.setMessageID(genRandomID());
		setting.setServerID(genRandomID());
		setting.setTimeStamp(System.currentTimeMillis());

		client.save(setting);
	}

	public void deleteTest(UniversalHibernateClient client)
	{
		CounterSetting setting = new CounterSetting();
		setting.setChannelID(genRandomID());
		setting.setMessageID(genRandomID());
		setting.setServerID(genRandomID());
		setting.setTimeStamp(System.currentTimeMillis());

		client.save(setting);
		client.delete(setting);
		printCounterSettings(client);

		client.deleteAll(CounterSetting.class);
		printCounterSettings(client);
	}

	private long genRandomID()
	{
		String idStr = "";
		for (int i = 0; i < 18; i++)
		{
			int random = (int)(Math.random() * 10);
			idStr += String.valueOf(random);
		}
		return Long.parseLong(idStr);
	}

}
