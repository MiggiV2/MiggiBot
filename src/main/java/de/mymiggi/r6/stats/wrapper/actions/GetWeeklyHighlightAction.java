package de.mymiggi.r6.stats.wrapper.actions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;

import de.mymiggi.r6.stats.wrapper.entitys.LoginResponse;
import de.mymiggi.r6.stats.wrapper.entitys.weekly.highlight.WeeklyHighlightResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetWeeklyHighlightAction
{
	public WeeklyHighlightResponse run(LoginResponse loginResponse, String playerID) throws IOException
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String startDate = LocalDate.now()
			.minusMonths(4)
			.format(formatter);
		String endDate = LocalDate.now()
			.format(formatter);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
			.addHeader("expiration", loginResponse.getExpiration())
			.addHeader("Ubi-AppId", "3587dcbb-7f81-457c-9781-0e3f29f6f56a")
			.addHeader("Ubi-SessionId", loginResponse.getSessionId())
			.addHeader("Authorization", "ubi_v1 t=" + loginResponse.getTicket())
			.url(String.format("https://r6s-stats.ubisoft.com/v1/narrative/bestmatchweekly/%s?startDate=%s&endDate=%s", playerID, startDate, endDate))
			.build();
		Response response = client.newCall(request).execute();
		String responseJSON = response.body().string();
		if (response.code() == 204)
		{
			throw new IOException("No highlight found!");
		}
		return new Gson().fromJson(responseJSON, WeeklyHighlightResponse.class);
	}
}
