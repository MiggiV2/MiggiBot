package de.mymiggi.discordbot.music.fresh;

import java.util.List;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;
import de.mymiggi.discordbot.music.fresh.entitys.FreshSong;
import de.mymiggi.discordbot.music.fresh.entitys.FreshWrapper;

public class FreshPlayer
{
	private static final String freshURL = "https://www.antenne.de/api/metadata/1.0/fresh/details?&apikey=c3e8228c8714e77f1544f95e7b9ebdee177bdcf8&historyitemlimit=30";

	public List<FreshSong> loadSongs() throws Exception
	{
		String jsonResponse = new Client().sendGetRequest(freshURL).getJsonResult();
		FreshWrapper wrapper = new Gson().fromJson(jsonResponse, FreshWrapper.class);
		return wrapper.getData().getSongs();
	}
}
