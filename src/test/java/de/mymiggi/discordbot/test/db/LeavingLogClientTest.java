package de.mymiggi.discordbot.test.db;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.LeavingLogSetting;

class LeavingLogClientTest
{

	private static Logger logger = LoggerFactory.getLogger(LeavingLogClientTest.class.getSimpleName());
	private List<LeavingLogSetting> settings = new ArrayList<LeavingLogSetting>();
	private int settingsBeforeTest;
	
	@Test
	void test()
	{
		UniversalHibernateClient client = new UniversalHibernateClient();
		settingsBeforeTest = client.getList(LeavingLogSetting.class).size();
		
		logger.info("Creating CounterSetting!");
		createCounterSetting(client);
		createCounterSetting(client);
		createCounterSetting(client);
		System.out.println("");
		logger.info("List CounterSettings!");
		printCounterSettings(client);
		logger.info("Delete CounterSettings!");
		deleteTest(client);
	}

	private void printCounterSettings(UniversalHibernateClient client)
	{
		List<LeavingLogSetting> list = client.getList(LeavingLogSetting.class);

		System.out.println("");
		System.out.println("ChannelID----------RoleID-------------ServerID-----------TimeStamp---");
		list.forEach(setting -> {
			System.out.print(setting.getChannelID() + " ");
			System.out.print(setting.getServerID() + " ");
			System.out.println(setting.getTimeStamp() + " ");
		});
		System.out.println("");
	}

	private void createCounterSetting(UniversalHibernateClient client)
	{

		LeavingLogSetting setting = new LeavingLogSetting();
		setting.setChannelID(genRandomID());
		setting.setServerID(genRandomID());
		setting.setTimeStamp(System.currentTimeMillis());
		settings.add(setting);
		client.save(setting);
	}

	public void deleteTest(UniversalHibernateClient client)
	{
		client.deleteList(settings);
		List<LeavingLogSetting> list = client.getList(LeavingLogSetting.class);
		assertTrue(list.size() == settingsBeforeTest);
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
