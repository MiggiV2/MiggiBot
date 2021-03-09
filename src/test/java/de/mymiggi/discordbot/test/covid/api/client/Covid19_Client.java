package de.mymiggi.discordbot.test.covid.api.client;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.corona.covid19.CoronaAPI;
import de.mymiggi.discordbot.corona.covid19.CoronaList;
import de.mymiggi.discordbot.corona.covid19.CoronaResponse;
import de.mymiggi.discordbot.main.BotMainCore;

class Covid19_Client
{

	/*
	 * connect to discord api
	 */

	@SuppressWarnings("unused")
	private static DiscordApi api = null;
	private static CoronaResponse[] coronaResponse;
	private static CoronaAPI covid19API = new CoronaAPI();

	@BeforeAll
	static void loadData() throws Exception
	{
		System.out.println("STARTING TEST \r\n");
		System.out.println("connecting to discord api ...");
		api = BotMainCore.getTestAPI();
		System.out.println("getting data from api ... \r\n");

		try
		{
			coronaResponse = covid19API.buildRequestObj();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Can't connect to corona api!");
			throw e;
		}
	}

	@Test
	void testEmbedBuild() throws Exception
	{
		System.out.println("Starting testEmbedBuild ...");
		EmbedBuilder embed;

		try
		{
			embed = covid19API.calculateStatsAndBuildEmbed(coronaResponse);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Test testEmbedBuild failed!");
			throw e;
		}
		assertFalse(embed == null);
		System.out.println("Test passed! No errors found \r\n");
	}

	@Test
	void testBuildCoronaList() throws Exception
	{
		System.out.println("Starting testBuildCoronaList ...");
		CoronaList list;

		try
		{
			list = covid19API.buildCoronaList(coronaResponse);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Test testBuildCoronaList failed!");
			throw e;
		}
		assertFalse(list == null);
		System.out.println("Test passed! No errors found");
	}

	@Test
	void testCoronaListFormattedDate() throws Exception
	{
		System.out.println("Starting testBuildCoronaList ...");
		CoronaList list;

		try
		{
			list = covid19API.buildCoronaList(coronaResponse);

			assertFalse(list.getLastWeek().getFormattedDate() == null);
			assertFalse(list.getToday().getFormattedDate() == null);
			assertFalse(list.getTowWeeksAgo().getFormattedDate() == null);
			assertFalse(list.getYesterday().getFormattedDate() == null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Test testCoronaListFormattedDate failed!");
			throw e;
		}
		System.out.println("Test passed! No errors found \r\n");
	}
}
