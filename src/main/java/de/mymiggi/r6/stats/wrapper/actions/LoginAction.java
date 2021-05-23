package de.mymiggi.r6.stats.wrapper.actions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.r6.stats.wrapper.entitys.LoginResponse;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class LoginAction
{
	private Logger logger = LoggerFactory.getLogger(LoginAction.class);

	public LoginResponse run() throws IOException
	{
		logger.info("Ubisoft, logging in...");
		Builder builder = new OkHttpClient.Builder();
		builder.authenticator(new Authenticator()
		{
			@Override
			public Request authenticate(Route route, Response response) throws IOException
			{
				String credential = getCredential();
				return response.request().newBuilder().header("Authorization", credential).build();
			}
		});
		builder.connectTimeout(10, TimeUnit.SECONDS);
		OkHttpClient client = builder.build();
		RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "{}");
		Request request = new Request.Builder()
			.addHeader("Ubi-AppId", "39baebad-39e5-4552-8c25-2c9b919064e2")
			.url("https://public-ubiservices.ubi.com/v3/profiles/sessions")
			.post(body)
			.build();
		Response response = client.newCall(request).execute();
		String responseJson = response.body().string();
		logger.info("Loged in!");
		return new Gson().fromJson(responseJson, LoginResponse.class);
	}

	public LoginResponse runMaybeNull()
	{
		try
		{
			return run();
		}
		catch (IOException e)
		{
			logger.error("Failed to login", e);
			throw new RuntimeException("Failed to login");
		}
	}

	private String getCredential() throws IOException
	{
		try
		{
			String credential;
			if (BotMainCore.config.getCredential() == null)
			{
				credential = Credentials.basic(BotMainCore.config.getEMail(), BotMainCore.config.getPassword());
			}
			else
			{
				credential = BotMainCore.config.getCredential();
			}
			return credential;
		}
		catch (Exception e)
		{
			logger.error("Failed to read config!", e);
			throw new IOException(e);
		}
	}
}
