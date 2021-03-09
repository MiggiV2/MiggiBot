package de.mymiggi.discordbot.server.member.playlist.manager.actions.get;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;
import de.mymiggi.discordbot.tools.ytclients.GetYTPlaylistIDAction;
import de.mymiggi.discordbot.tools.ytclients.YTPlaylistSearcher;
import de.mymiggi.discordbot.tools.ytclients.YTVideoSearcher;

public class MPlaylistAddSongAction
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistAddSongAction.class.getSimpleName());

	public String run(UniversalHibernateClient client, String searchQuery, User user, String playListTitel) throws Exception
	{
		String resultURL;
		if (searchQuery.contains("playlist?list="))
		{
			YTPlaylistSearcher playlistSearcher = new YTPlaylistSearcher();
			String id = new GetYTPlaylistIDAction().get(searchQuery);
			List<PlaylistItem> response = playlistSearcher.search(id);
			List<NewMemberPlaylistSong> songs = new ArrayList<NewMemberPlaylistSong>();
			logger.info("Playlists size: " + response.size());
			resultURL = searchQuery;
			for (PlaylistItem item : response)
			{
				NewMemberPlaylistSong song = new NewMemberPlaylistSong();
				song.setUserID(user.getId());
				song.setPlayListName(playListTitel);
				song.setTitle(item.getSnippet().getTitle());
				song.setSongURL("https://youtu.be/" + item.getContentDetails().getVideoId());
				if (!isAlreadyAdded(song.getSongURL(), playListTitel, client, user))
				{
					songs.add(song);
				}
				else
				{
					logger.warn("Song " + song.getTitle() + " is already in playlist " + playListTitel);
				}
			}
			client.saveList(songs);
		}
		else
		{
			NewMemberPlaylistSong song = new NewMemberPlaylistSong();
			SearchResult result = YTVideoSearcher.search(searchQuery);
			resultURL = "https://youtu.be/" + result.getId().getVideoId();
			song.setUserID(user.getId());
			song.setPlayListName(playListTitel);
			song.setTitle(result.getSnippet().getTitle());
			song.setSongURL(resultURL);
			if (isAlreadyAdded(song.getSongURL(), playListTitel, client, user))
			{
				throw new Exception("Song is already in playlist");
			}
			client.save(song);
		}
		return resultURL;
	}

	private boolean isAlreadyAdded(String songsURL, String playListTitel, UniversalHibernateClient client, User user)
	{
		List<NewMemberPlaylistSong> songs = new MPlaylistAllSongsAction().run(client).get(user);
		if (songs != null)
		{
			for (NewMemberPlaylistSong temp : songs)
			{
				if (temp.getSongURL().equals(songsURL) && temp.getPlayListName().equals(playListTitel))
				{
					return true;
				}
			}
		}
		return false;
	}
}
