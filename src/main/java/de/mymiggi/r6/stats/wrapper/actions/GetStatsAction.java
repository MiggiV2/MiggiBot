package de.mymiggi.r6.stats.wrapper.actions;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.entitys.LoginResponse;
import de.mymiggi.r6.stats.wrapper.entitys.stats.SmartStatsResponse;
import de.mymiggi.r6.stats.wrapper.entitys.stats.StatsResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetStatsAction
{
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public SmartStatsResponse run(LoginResponse loginResponse, PlatfromType platfromType, String playerID) throws IOException
	{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.addHeader("expiration", loginResponse.getExpiration())
			.addHeader("Ubi-AppId", "3587dcbb-7f81-457c-9781-0e3f29f6f56a")
			.addHeader("Ubi-SessionId", loginResponse.getSessionId())
			.addHeader("Authorization", "ubi_v1 t=" + loginResponse.getTicket())
			.url(String.format("https://r6s-stats.ubisoft.com/v1/current/summary/%s?gameMode=all,ranked,unranked,casual&platform=%s", playerID, platfromType.getTypeForStats()))
			.build();
		Response response = client.newCall(request).execute();
		String responseJSON = response.body().string();
		if (response.code() != 200)
		{
			logger.info("Status:" + response.code() + " Body:" + responseJSON);
		}
		return new Gson().fromJson(responseJSON, StatsResponse.class).getSmartResponse();
	}
}
