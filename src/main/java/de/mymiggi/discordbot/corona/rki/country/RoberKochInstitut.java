package de.mymiggi.discordbot.corona.rki.country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;

public class RoberKochInstitut
{
	private Map<String, Response> countryMap = new HashMap<String, Response>();
	private List<Attributes> data = new ArrayList<Attributes>();
	private List<String> shorterCountryNameList = new ArrayList<String>();
	private List<Response> shorterCountryResponseList = new ArrayList<Response>();
	private List<String> countrys = new ArrayList<String>();
	private Logger logger = LoggerFactory.getLogger("RKICountryAPI");

	public void update()
	{
		String respons = getResponse();
		features rikResponse = buildObj(respons);
		countryMap = getCountry(rikResponse);
	}

	private String getResponse()
	{
		long beginningUnixStamp = System.currentTimeMillis();
		try
		{
			Client client = new Client();
			String url = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=EWZ,death_rate,cases,deaths,cases_per_population,BL,county,last_update,cases7_per_100k,EWZ_BL,death7_bl,cases7_lk,death7_lk,cases7_per_100k_txt,RS,cases7_bl_per_100k,cases7_bl,GEN&returnGeometry=false&returnDistinctValues=true&outSR=4326&f=json";
			String json = client.sendGet(url);

			long newTimeStamp = System.currentTimeMillis() - beginningUnixStamp;
			logger.info("Time till respond: " + newTimeStamp);
			return json;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Response> moreResults(String Country) throws Exception
	{
		String[] splitted = Country.split(" ");
		String shorterName = splitted[0];
		ArrayList<Response> results = new ArrayList<Response>();

		if (!shorterCountryNameList.contains(shorterName.toLowerCase()))
		{
			throw new Exception("Country not found in map");
		}
		shorterCountryResponseList.forEach(response -> {
			if (response.getGEN_Simple().contains(shorterName.toLowerCase()))
			{
				results.add(response);
			}
		});
		return results;
	}

	private features buildObj(String response)
	{
		Gson gson = new Gson();
		features rikResponse = gson.fromJson(response, features.class);
		data = rikResponse.getFeaturesList();
		return rikResponse;
	}

	private HashMap<String, Response> getCountry(features rikResponse)
	{
		HashMap<String, Response> statsMapTemp = new HashMap<String, Response>();

		for (Attributes temp : rikResponse.getFeaturesList())
		{

			statsMapTemp.put(temp.getResponse().getGEN().toLowerCase(), temp.getResponse());
			countrys.add(temp.getResponse().getGEN().toLowerCase());
			shorterCountryNameList.add(temp.getResponse().getGEN_Simple().toLowerCase());
			shorterCountryResponseList.add(temp.getResponse());
		}
		return statsMapTemp;
	}

	public Response getCountryByName(String name) throws Exception
	{
		if (!countryMap.containsKey(name.toLowerCase()))
		{
			throw new Exception("Country not found in map");
		}
		return countryMap.get(name.toLowerCase());
	}

	public List<Attributes> getData()
	{
		return data;
	}

	public Map<String, Response> getStatsMap()
	{
		if (countryMap.isEmpty())
		{
			update();
		}
		return countryMap;
	}

	public List<String> getCountrys()
	{
		if (countrys.isEmpty())
		{
			update();
		}
		return countrys;
	}
}
