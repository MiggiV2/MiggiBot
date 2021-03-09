package de.mymiggi.tenor;

import com.google.gson.Gson;

import de.mymiggi.tenor.actions.LoadTenorAPIKey;
import de.mymiggi.tenor.actions.SendTenorRequest;
import de.mymiggi.tenor.util.TenorResponse;

public class TenorClientCore
{
	public TenorResponse run(String searchQuerry) throws Exception
	{
		String apiKey = new LoadTenorAPIKey().run();
		String json = new SendTenorRequest().run(apiKey, searchQuerry);
		TenorResponse response = new Gson().fromJson(json, TenorResponse.class);
		return response;
	}
}
