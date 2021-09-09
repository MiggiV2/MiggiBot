package de.mymiggi.discordbot.test.server.logger;

import org.javacord.api.DiscordApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.logs.welcomer.ServerAndChannelsLoader;
import de.mymiggi.discordbot.server.logs.welcomer.WelcomerRunner;

class WelcomerTest
{
	/*
	 * connect to api
	 */
	@SuppressWarnings("unused")
	private static final DiscordApi api = BotMainCore.getTestAPI();
	private static Logger logger = LoggerFactory.getLogger(WelcomerTest.class.getSimpleName());
	private static ServerAndChannelsLoader loader = new ServerAndChannelsLoader();
	private static WelcomerRunner welcomer = new WelcomerRunner();

	@BeforeEach
	public void before()
	{
		logger.info("[START TEST]");
	}

	@Test
	public void createJSON1() throws Exception
	{
		/*
		 * Test should not fail!
		 */
		logger.info("testing to create json file ...");

		long channelID = 743800308353990706L;
		long serverID = 743800306827001958L;
		long roleID = 746645343432867882L;

		loader.buildJsonFile(serverID, channelID, roleID, null);
		welcomer.syncHashMap();

		logger.info("Done! \r\n");
	}

	@Test
	public void failToCreateJSON1()
	{
		/*
		 * Test with wrong numbers and letters
		 */
		logger.info("testing to give wrong input");

		long channelID = 134590329482459593L;
		long serverID = 687389024024523023L;
		long roleID = 234052345323530452L;

		try
		{
			loader.buildJsonFile(serverID, channelID, roleID, null);
			welcomer.syncHashMap();
			Assertions.fail();
		}
		catch (Exception e)
		{
			logger.info("Done! \r\n");
		}
	}

	@Test
	public void failToCreateJSON2()
	{
		/*
		 * Test with wrong numbers
		 */
		logger.info("testing to give wrong input");

		long channelID = 42982762942424927L;
		long serverID = 234098234203424234L;
		long roleID = 930485670380203453L;

		try
		{
			loader.buildJsonFile(serverID, channelID, roleID, null);
			welcomer.syncHashMap();
			Assertions.fail();
		}
		catch (Exception e)
		{
			logger.info("Done! \r\n");
		}
	}

	@Test
	public void createJSON2() throws Exception
	{
		/*
		 * Test should not fail!
		 */
		logger.info("testing to create json file ...");

		long channelID = 743800308353990706L;
		long serverID = 743800306827001958L;
		long roleID = 746645343432867882L;

		loader.buildJsonFile(serverID, channelID, roleID, null);
		welcomer.syncHashMap();

		logger.info("Done! \r\n");
	}
}
