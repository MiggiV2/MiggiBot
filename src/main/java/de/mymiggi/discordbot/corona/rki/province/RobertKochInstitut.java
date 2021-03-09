package de.mymiggi.discordbot.corona.rki.province;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;

public class RobertKochInstitut
{
	private List<String> bundesLaender = new ArrayList<String>();
	private List<Attributes> data = new ArrayList<Attributes>();
	private features rikResponse;
	private Logger logger = LoggerFactory.getLogger(RobertKochInstitut.class.getSimpleName());

	public void update()
	{
		String respons = getResponse();
		rikResponse = buildObj(respons);
		bundesLaender = getBundesLaender(rikResponse);
	}

	private String getResponse()
	{
		long beginningUnixStamp = System.currentTimeMillis();
		try
		{
			Client client = new Client();
			String url = "https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/Coronaf%C3%A4lle_in_den_Bundesl%C3%A4ndern/FeatureServer/0/query?where=1%3D1&outFields=Fallzahl,Aktualisierung,faelle_100000_EW,Death,cases7_bl_per_100k,cases7_bl,death7_bl,LAN_ew_GEN&returnGeometry=false&returnDistinctValues=true&outSR=4326&f=json";
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

	public Response getByBundesLand(String bundesLand)
	{
		for (int i = 0; i < bundesLaender.size(); i++)
		{
			if (bundesLand.equals(bundesLaender.get(i)))
			{
				return rikResponse.getFeaturesList().get(i).getResponse();
			}
		}
		logger.error("Error", "RIK_Respons not found!");
		return null;
	}

	private ArrayList<String> getBundesLaender(features rikResponse)
	{
		ArrayList<String> bundesLaender = new ArrayList<String>();
		for (Attributes temp : rikResponse.getFeaturesList())
		{
			bundesLaender.add(temp.getResponse().getBundesLand());
		}
		return bundesLaender;
	}

	public int getAverageDeathLast7Days(List<Attributes> data)
	{
		int average = 0;
		for (Attributes temp : data)
		{
			average += temp.getResponse().getDeath7_bl();
		}
		return (average / data.size());
	}

	public double getAverageInzidenz(List<Attributes> data)
	{
		double average = 0;
		for (Attributes temp : data)
		{
			average += temp.getResponse().getCases7_bl_per_100k();
		}
		return (average / data.size());
	}

	public int getAverageCasesLast7Days(List<Attributes> data)
	{
		int average = 0;
		for (Attributes temp : data)
		{
			average += temp.getResponse().getCases7_bl();
		}
		return (average / data.size());
	}

	public int getTotal(List<Attributes> data)
	{
		int total = 0;
		for (Attributes temp : data)
		{
			total += temp.getResponse().getFallzahl();
		}
		return total;
	}

	private features buildObj(String response)
	{
		Gson gson = new Gson();
		features rikResponse = gson.fromJson(response, features.class);
		setData(rikResponse.getFeaturesList());
		return rikResponse;
	}

	public List<Attributes> getData()
	{
		if (data.isEmpty())
		{
			update();
		}
		return data;
	}

	public void setData(ArrayList<Attributes> data)
	{
		this.data = data;
	}
}
