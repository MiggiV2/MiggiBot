package de.mymiggi.webuntis.helper.actions;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class SendUntisRequestAction
{
	private String cookieURLPattern = "https://erato.webuntis.com/WebUntis/?school=%s#/basic/timetable";
	private String dataURLPattern = "https://erato.webuntis.com/WebUntis/api/public/timetable/weekly/data?elementType=1&elementId=360&date=%s&formatId=3";
	private static Logger logger = LoggerFactory.getLogger(SendUntisRequestAction.class.getSimpleName());

	public WebUntisResponse run() throws IOException
	{
		LocalDate currentTime = LocalDate.now();
		String untisSchoolName = BotMainCore.config.getUntisSchoolName();
		String dataURL = String.format(dataURLPattern, currentTime.toString());
		String cookieURL = String.format(cookieURLPattern, untisSchoolName);
		HttpClientContext context = HttpClientContext.create();
		try (CloseableHttpClient httpClient = HttpClients.createDefault())
		{
			try (CloseableHttpResponse response = httpClient.execute(new HttpGet(cookieURL), context))
			{
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300)
				{
					logger.info("Got cookies!");
				}
				else
				{
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			}
		}
		try (CloseableHttpClient httpclient = HttpClients.createDefault())
		{
			HttpGet cookieRequestTow = new HttpGet(dataURL);
			cookieRequestTow.addHeader(HttpHeaders.ACCEPT, "application/json");
			cookieRequestTow.addHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:85.0) Gecko/20100101 Firefox/85.0");
			try (CloseableHttpClient httpClient = HttpClients.createDefault())
			{
				try (CloseableHttpResponse response = httpClient.execute(cookieRequestTow, context))
				{
					logger.info("Got json!");
					HttpEntity entity = response.getEntity();
					String responseJSON = entity != null ? EntityUtils.toString(entity) : null;
					Gson gson = new Gson();
					return gson.fromJson(responseJSON, WebUntisResponse.class);
				}
			}
		}
	}
}
