package de.mymiggi.discordbot.tools.ytclients;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

public class YTPlaylistSearcher
{
	private final String DEVELOPER_KEY = new LoadMyYTTokenAction().run();
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private final String APPLICATION_NAME = "Miggi's DiscordBot";

	public YouTube getService() throws GeneralSecurityException, IOException
	{
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
			.setApplicationName(APPLICATION_NAME)
			.build();
	}

	public List<PlaylistItem> search(String playlistID) throws GeneralSecurityException, IOException, GoogleJsonResponseException
	{
		YouTube youtubeService = getService();
		YouTube.PlaylistItems.List request = youtubeService.playlistItems().list("snippet,contentDetails");

		PlaylistItemListResponse response = request.setKey(DEVELOPER_KEY)
			.setMaxResults(100L)
			.setPlaylistId(playlistID)
			.execute();
		List<PlaylistItem> items = new ArrayList<PlaylistItem>();
		tryToGetMoreResults(response, request, items);
		return items;
	}

	private void tryToGetMoreResults(PlaylistItemListResponse response, YouTube.PlaylistItems.List request, List<PlaylistItem> items) throws IOException
	{
		String playlistID = response.getItems().get(0).getSnippet().getPlaylistId();
		response.getItems().forEach(item -> items.add(item));
		if (response.getItems().size() == 50)
		{
			PlaylistItemListResponse response2 = request.setKey(DEVELOPER_KEY)
				.setMaxResults(100L)
				.setPlaylistId(playlistID)
				.setPageToken(response.getNextPageToken())
				.execute();
			tryToGetMoreResults(response2, request, items);
		}
	}
}
