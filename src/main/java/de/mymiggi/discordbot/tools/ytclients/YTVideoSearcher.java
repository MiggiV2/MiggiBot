package de.mymiggi.discordbot.tools.ytclients;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.samples.youtube.cmdline.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

public class YTVideoSearcher
{

	private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
	private static String ytAPI_Key = new LoadMyYTTokenAction().run();
	private static Logger logger = LoggerFactory.getLogger(YTVideoSearcher.class.getSimpleName());
	private static YouTube youtube;

	public static SearchResult search(String queryTerm)
	{
		try
		{
			youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer()
			{
				@Override
				public void initialize(HttpRequest request) throws IOException
				{
				}
			}).setApplicationName("youtube-cmdline-search-sample").build();
			YouTube.Search.List search = youtube.search().list("id,snippet");
			search.setKey(ytAPI_Key)
				.setQ(queryTerm)
				.setType("video")
				.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
				.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
			SearchListResponse searchResponse = search.execute();
			return searchResponse.getItems().get(0);
		}
		catch (GoogleJsonResponseException e)
		{
			logger.error("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		}
		catch (IOException e)
		{
			logger.error("There was an IO error: " + e.getCause() + " : " + e.getMessage());
		}
		catch (Throwable t)
		{
			logger.error("Failed", t);
		}
		return null;
	}
}
