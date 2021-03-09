package de.mymiggi.discordbot.test.covid.api.connection;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.http.Client;
import de.mymiggi.discordbot.http.Response;

public class ResponseTest
{

	private static String URL1 = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=1%3D1&outFields=Fallzahl,LAN_ew_GEN,Death,cases7_bl,death7_bl,cases7_bl_per_100k_txt,Aktualisierung&returnGeometry=false&outSR=4326&f=json";
	private static String URL2 = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=EWZ,death_rate,cases,deaths,cases_per_population,BL,county,last_update,cases7_per_100k,EWZ_BL,death7_bl,cases7_lk,death7_lk,cases7_per_100k_txt,RS,cases7_bl_per_100k,cases7_bl,GEN&returnGeometry=false&returnDistinctValues=true&outSR=4326&f=json";
	private static String testURL;
	private static Response apiResponse;
	private static String status;
	private static int entitySize;
	private static int testLength = 3000;

	@Test
	public void run() throws Exception
	{

		testURL = buildURL2();

		timeOutThread();
		createMap();

		testAPIConnection();
		testResponseEmpty();
		testResponseLength();

		testURL = URL1;

		timeOutThread();
		createMap();

		testAPIConnection();
		testResponseEmpty();
		testResponseLength();

		testURL = URL2;

		timeOutThread();
		createMap();

		testAPIConnection();
		testResponseEmpty();
		testResponseLength();
	}

	public void testAPIConnection() throws Exception
	{

		if (!status.equals("HTTP/1.1 200 OK"))
		{
			System.err.println("ConnectionTest FAILED!" + status);
			throw new Exception("ConnectionTest FAILED!" + status);
		}
		System.out.println("ConnectionTest passsed!");
	}

	public void testResponseEmpty() throws Exception
	{

		if (entitySize == 0)
		{
			System.err.println("ResponseNotEmpty FAILED!" + entitySize);
			throw new Exception("ResponseNotEmpty FAILED!" + entitySize);
		}

		System.out.println("ResponseNotEmpty passsed!");
	}

	public void testResponseLength() throws Exception
	{

		if (entitySize < testLength)
		{

			String errorMessage = String.format("%sResponseLength FAILED! %sLength should be  over %s, but is %s %s", System.lineSeparator(), System.lineSeparator(), testLength, entitySize, System.lineSeparator());

			System.err.println(errorMessage);

			Assertions.fail("ResponseLength FAILED!");

			throw new Exception("ResponseLength FAILED!");
		}
		else
		{
			System.out.println("ResponseLength passsed! Length: " + entitySize + "\r\n" + "\r\n");
		}
	}

	private String buildURL2()
	{

		LocalDate todayDate = LocalDate.now();

		String thisWeek = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'"));
		String threeWeeksBefore = todayDate.minusWeeks(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'"));

		return String.format("https://api.covid19api.com/country/de/status/confirmed?from=%s&to=%s", threeWeeksBefore, thisWeek);
	}

	private void timeOutThread()
	{

		Thread thread = new Thread()
		{

			@Override
			public void run()
			{

				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				assertFalse(null == apiResponse);
			}

		};
		thread.start();
	}

	private void createMap() throws Exception
	{

		Client client = new Client();
		apiResponse = client.sendGetRequest(testURL);
		entitySize = apiResponse.getJsonResult().length();
		status = apiResponse.getStatus();

		System.out.println("#####################");
		System.out.println("# Starting API Test #");
		System.out.println("#####################");
		System.out.println("URL: " + testURL + "\r\n");

		assertFalse(null == apiResponse);

		System.out.println("[TEST RESULTS]" + System.lineSeparator());
	}
}
