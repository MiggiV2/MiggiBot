package de.mymiggi.tenor;

import com.google.gson.Gson;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.tenor.actions.SendTenorRequest;
import de.mymiggi.tenor.util.TenorResponse;

public class TenorClientCore
{
	public TenorResponse run(String searchQuerry) throws Exception
	{
		String apiKey = BotMainCore.config.getTenorAPIKey();
		String json = new SendTenorRequest().run(apiKey, searchQuerry);
		TenorResponse response = new Gson().fromJson(json, TenorResponse.class);
		return response;
	}
}
