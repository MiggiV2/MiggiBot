package de.mymiggi.discordbot.test.covid.api.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.corona.rki.country.RKICountry;
import de.mymiggi.discordbot.corona.rki.country.Response;
import de.mymiggi.discordbot.corona.rki.province.Attributes;
import de.mymiggi.discordbot.corona.rki.province.RKIProvince;
import de.mymiggi.discordbot.corona.rki.province.RobertKochInstitut;
import de.mymiggi.discordbot.main.BotMainCore;

class RKI_Client
{

	@SuppressWarnings("unused")
	private static DiscordApi api = null;

	private static List<Attributes> data;
	private static int totalCases;
	private static int avDeath;
	private static int avCases;
	private static int testCounter = 0;
	private static RKIProvince rkiProvince = new RKIProvince();
	private static RKICountry rkiCountry = new RKICountry();
	private static RobertKochInstitut rki = new RobertKochInstitut();

	@BeforeAll
	static void loadData() throws Exception
	{
		System.out.println("STARTING TEST \r\n");
		api = BotMainCore.getTestAPI();

		System.out.println("Got! \r\n");
		System.out.println("Getting data from api ...");

		rkiProvince.syncData();
		data = rkiProvince.getData();
		rkiCountry.updateData();

		assertFalse(data == null);
		System.out.println("Got! \r\n");
	}

	@Test
	void totalCases()
	{
		totalCases = rki.getTotal(data);
		assertTrue(totalCases > 0);
	}

	@Test
	void avDeath()
	{
		avDeath = rki.getAverageDeathLast7Days(data);
		assertTrue(avDeath > 0);
	}

	@Test
	void avCases()
	{
		avCases = rki.getAverageCasesLast7Days(data);
		assertTrue(avCases > 0);
	}

	@Test
	void embedTest()
	{
		EmbedBuilder embed = rkiProvince.buildEmbed();
		assertFalse(embed == null);
	}

	@Test
	void highstEmbedTest()
	{
		EmbedBuilder embed = rkiProvince.highestEmbed();
		assertFalse(embed == null);
	}

	@Test
	void averageInzidenz()
	{
		double average = rki.getAverageInzidenz(data);
		assertTrue(average > 0);
	}

	@Test
	void getCountryList() throws Exception
	{

		List<String> country = rkiCountry.getCountrysList();

		assertFalse(country == null);
		assertFalse(country.isEmpty());

		try
		{
			getCountyByName("NotInListCountry");
			fail();
		}
		catch (Exception e)
		{
		}
		for (int i = 0; i < 999; i++)
		{
			int randomInt = (int)(Math.random() * country.size());
			String randomCountry = country.get(randomInt);
			getCountyByName(randomCountry);
		}

		for (int i = 0; i < 999; i++)
		{
			for (int o = 0; o < 999; o++)
			{
				String countryStr = rkiCountry.getRandomCountry();
				assertFalse(countryStr == null);
				assertFalse(countryStr.isEmpty());
			}
		}
	}

	@BeforeEach
	void befor()
	{
		testCounter++;
		System.out.println("[TEST " + testCounter + "]");
		System.out.println("Starting test...");
	}

	@AfterEach
	void after()
	{
		System.out.println("Test passed! \r\n");
	}

	private void getCountyByName(String name) throws Exception
	{
		Response response = rkiCountry.getCountryByName(name);
		assertFalse(response == null);
	}
}
