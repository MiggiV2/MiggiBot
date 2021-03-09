package de.mymiggi.discordbot.test.server.logger;

import static org.junit.jupiter.api.Assertions.fail;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.counter.CounterSync;
import de.mymiggi.discordbot.server.counter.MemberCounterCore;

class MemberCounterTest
{
	/*
	 * connect to api
	 */
	@SuppressWarnings("unused")
	private static final DiscordApi api = BotMainCore.api;
	private static Logger logger = LoggerFactory.getLogger(MemberCounterTest.class.getSimpleName());
	private static CounterSync countrySync = new CounterSync();
	private static MemberCounterCore counter = new MemberCounterCore();

	@BeforeEach
	public void test()
	{
		System.out.println("");
		logger.info("[START TEST]");
	}

	@Test
	public void create() throws Exception
	{
		/*
		 * Test should not fail!
		 */
		logger.info("testing with correct IDs...");

		long channelID = 803162374482952202L;
		long serverID = 743800306827001958L;
		long messageID = 803176360348221460L;

		try
		{
			checkIDs(channelID, serverID, messageID);
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage(),e);
			logger.info("Please update the IDs, to start the test!");
			throw new Exception("Please update the IDs, to start the test");
		}
		countrySync.saveObjInDB(messageID, serverID, channelID, null);
		counter.syncList();
		counter.runNewAddedCounter();

		logger.info("Passed!");
	}

	@Test
	public void failToCreate()
	{
		/*
		 * Test with wrong numbers and letters
		 */
		logger.info("testing to give wrong input");

		long channelID = 928345025203535023L;
		long serverID = 207503475237457203L;
		long messageID = 494385603456739485L;

		try
		{
			countrySync.saveObjInDB(messageID, serverID, channelID, null);
			counter.syncList();
			counter.runNewAddedCounter();
			fail();
		}
		catch (Exception e)
		{
			Assertions.assertEquals("Message not found!", e.getMessage());
		}
		logger.info("Passed!");
	}

	private void checkIDs(long ChannelID, long ServerID, long messageID) throws Exception
	{
		if (!BotMainCore.api.getChannelById(ChannelID).isPresent())
		{
			logger.warn("[ServerAndChannelsLoader] Channel not found!");
			throw new Exception("Channel not found!");
		}

		if (!BotMainCore.api.getServerById(ServerID).isPresent())
		{
			logger.warn("[ServerAndChannelsLoader] Server not found!");
			throw new Exception("Server not found!");
		}
		/*
		 * get message, if donest exist, throw error
		 */
		TextChannel channel = BotMainCore.api.getChannelById(ChannelID).get().asTextChannel().get();
		BotMainCore.api.getMessageById(messageID, channel).get();
	}
}
