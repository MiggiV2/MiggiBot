package de.mymiggi.tenor.actions;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class SendTenorRequest
{
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public String run(String API_Key, String searchQuerry) throws Exception
	{
		String url = String.format("https://g.tenor.com/v1/random?key=%s&q=%s&limit=20", API_Key, searchQuerry);
		HttpGet request = new HttpGet(url);
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		try (CloseableHttpResponse response = httpClient.execute(request))
		{
			if (!response.getStatusLine().toString().equals("HTTP/1.1 200 OK"))
			{
				System.err.print("HTTP REQUEST FAILED");
				System.err.println(" Code: " + response.getStatusLine().toString());
			}
			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
				String result = EntityUtils.toString(entity);
				return result;
			}
		}
		throw new Exception("Result is null!");
	}
}
