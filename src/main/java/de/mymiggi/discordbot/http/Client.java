package de.mymiggi.discordbot.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Client
{
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public String sendGet(String url) throws Exception
	{

		return sendGetRequest(url).getJsonResult();
	}

	public Response sendGetRequest(String url) throws Exception
	{
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
				Response apiResponse = new Response();

				apiResponse.setJsonResult(result);
				apiResponse.setStatus(response.getStatusLine().toString());
				return apiResponse;
			}
		}
		throw new Exception("Result is null!");
	}

	public boolean sendPost(String name, String token, String mesContent) throws IOException
	{
		try (CloseableHttpClient httpclient = HttpClients.createDefault())
		{
			HttpPost httpPost = new HttpPost("https://mymiggi.de/message");
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			String json = String.format("{\r\n"
				+ "    \"name\": \"%s\",\r\n"
				+ "    \"authorPin\": \"%s\",\r\n"
				+ "    \"mesContent\": \"%s\"\r\n"
				+ "}", name, token, mesContent);
			StringEntity stringEntity = new StringEntity(json);
			httpPost.setEntity(stringEntity);

			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300)
				{
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				}
				else
				{
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			String responseBody = httpclient.execute(httpPost, responseHandler);

			if (responseBody != null)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
}
