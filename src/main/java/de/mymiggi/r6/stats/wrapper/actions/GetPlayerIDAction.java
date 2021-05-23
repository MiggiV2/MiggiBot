package de.mymiggi.r6.stats.wrapper.actions;

import java.io.IOException;

import com.google.gson.Gson;

import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.entitys.LoginResponse;
import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetPlayerIDAction
{
	public PlayerIDResponse run(LoginResponse loginResponse, PlatfromType platformType, String playerName) throws IOException
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.addHeader("expiration", loginResponse.getExpiration())
			.addHeader("Ubi-AppId", "3587dcbb-7f81-457c-9781-0e3f29f6f56a")
			.addHeader("Ubi-SessionId", loginResponse.getSessionId())
			.addHeader("Authorization", "ubi_v1 t=" + loginResponse.getTicket())
			.url(String.format("https://public-ubiservices.ubi.com/v3/profiles?platformType=%s&nameOnPlatform=%s", platformType.getType(), playerName))
			.build();
		Response response = client.newCall(request).execute();
		String responseJSON = response.body().string();
		PlayerIDResponse idResponse = new Gson().fromJson(responseJSON, PlayerIDResponse.class);
		if (idResponse.getProfiles() == null || idResponse.getProfiles().length == 0)
		{
			throw new IOException("No player found!");
		}
		return idResponse;
	}
}
