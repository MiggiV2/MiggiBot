package de.mymiggi.discordbot.test.server.logger;

import org.javacord.api.DiscordApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.logs.leaving.LeavingLogCore;
import de.mymiggi.discordbot.server.logs.leaving.LeavingLogChannelLoader;

class LeavingLogTest
{

	@SuppressWarnings("unused")
	private static final DiscordApi api = BotMainCore.getTestAPI();
	private static Logger logger = LoggerFactory.getLogger(LeavingLogTest.class.getSimpleName());
	private static LeavingLogChannelLoader loader = new LeavingLogChannelLoader();
	private static LeavingLogCore leavingLogger = new LeavingLogCore();

	@BeforeEach
	public void before()
	{
		logger.info("[START TEST]");
	}

	@Test
	public void create1() throws Exception
	{
		/*
		 * Test should not fail!
		 */
		logger.info("testing to create json file ...");

		String channelID = "743800308353990706";
		String serverID = "743800306827001958";

		loader.buildJsonFile(serverID, channelID);
		leavingLogger.syncHashMap();

		logger.info("Done! \r\n");
	}

	@Test
	public void failToCreate1()
	{
		/*
		 * Test with wrong numbers and letters
		 */
		logger.info("testing to give wrong input");

		String channelID = "345783452823878G34";
		String serverID = "97i12�09345�20886868";

		try
		{
			loader.buildJsonFile(serverID, channelID);
			leavingLogger.syncHashMap();
			Assertions.fail();
		}
		catch (Exception e)
		{
			logger.info("Done!");
		}
	}

	@Test
	public void failToCreate2()
	{
		/*
		 * Test with wrong numbers
		 */
		logger.info("testing to give wrong input");

		String channelID = "349503948593453455";
		String serverID = "86824563403452923424";

		try
		{
			loader.buildJsonFile(serverID, channelID);
			leavingLogger.syncHashMap();
			Assertions.fail();
		}
		catch (Exception e)
		{
			logger.info("Done!");
		}
	}

	@Test
	public void create2() throws Exception
	{
		/*
		 * Test should not fail!
		 */
		logger.info("testing to create json file ...");

		String channelID = "368429093555011586";
		String serverID = "368429093555011584";

		loader.buildJsonFile(serverID, channelID);
		leavingLogger.syncHashMap();

		logger.info("Done!");
	}
}
