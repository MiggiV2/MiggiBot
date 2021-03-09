package de.mymiggi.discordbot.test.db;

import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.MPlaylistCreateNew;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAddSongAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfo;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

class MemberPlayListTest
{
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private List<MemberPlayListInfoNew> savedPlayLists = new ArrayList<MemberPlayListInfoNew>();
	private List<MemberPlayListInfoNew> oldPlaylists;
	private DiscordApi api = BotMainCore.api;
	private String playListTitle;

	@Test
	void test() throws Exception
	{
		convert();
		oldPlaylists = client.getList(MemberPlayListInfoNew.class);
		for (int i = 0; i < 8; i++)
		{
			playListTitle = getRandomString();
			createPlaylist();
			Thread.sleep(100);
		}
		savePlaylist();
		buildSavedPlayLists();
		deleteEntirePlayList();
		deleteSavedPlayLists();
	}

	private void convert()
	{
		List<MemberPlayListInfo> oldPlaylists = client.getList(MemberPlayListInfo.class);
		List<MemberPlayListInfoNew> convertedPlaylists = new ArrayList();
		for (MemberPlayListInfo temp : oldPlaylists)
		{
			MemberPlayListInfoNew newPlaylist = new MemberPlayListInfoNew()
				.setJoined(temp.isJoined())
				.setPlayListTitel(temp.getPlayListTitle())
				.setPublicToAllUseres(temp.isPublicToAllUseres())
				.setUserID(temp.getUserID());
			convertedPlaylists.add(newPlaylist);
		}
		client.saveList(convertedPlaylists);
	}

	private void deleteSavedPlayLists()
	{
		int playlistsBefore = client.getList(MemberPlayListInfoNew.class).size();
		client.deleteList(savedPlayLists);
		int playlistsAfter = client.getList(MemberPlayListInfoNew.class).size();
		assertNotSame(playlistsAfter, playlistsBefore);
	}

	private void createPlaylist()
	{
		try
		{
			new MPlaylistCreateNew().run(client, new ArrayList<MemberPlayListInfoNew>(), playListTitle, api.getOwner().get());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void savePlaylist() throws Exception
	{
		String searchString = "playlist?list=PLiLyShvbEK3QIjaI1hfVCmRJ6BZtOGLsX";
		new MPlaylistAddSongAction().run(client, searchString, api.getOwner().get(), playListTitle);
	}

	private void deleteEntirePlayList() throws Exception
	{
		client.delete(savedPlayLists.get(0));
		client.deleteList(getAllSongsFromPlaylist());
		savedPlayLists.remove(0);
	}

	private List<NewMemberPlaylistSong> getAllSongsFromPlaylist() throws Exception
	{
		List<NewMemberPlaylistSong> playlistSongs = new ArrayList<NewMemberPlaylistSong>();
		List<NewMemberPlaylistSong> allSongs = client.getList(NewMemberPlaylistSong.class);
		for (NewMemberPlaylistSong temp : allSongs)
		{
			if (temp.getPlayListName().equals(playListTitle))
			{
				playlistSongs.add(temp);
			}
		}
		if (playlistSongs.isEmpty())
		{
			throw new Exception("No songs found!");
		}
		return playlistSongs;
	}

	private void buildSavedPlayLists()
	{
		List<MemberPlayListInfoNew> allPlaylists = client.getList(MemberPlayListInfoNew.class);
		for (MemberPlayListInfoNew temp : allPlaylists)
		{
			if (!oldPlaylists.contains(temp))
			{
				savedPlayLists.add(temp);
			}
		}
	}

	long getRandomID()
	{
		char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		char[] randomID = new char[15];
		for (int i = 0; i < 15; i++)
		{
			int randomInt = (int)(Math.random() * chars.length);
			randomID[i] = chars[randomInt];
		}
		return Long.parseLong(new String(randomID));
	}

	private String getRandomString()
	{
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'X', 'Z' };
		char[] randomStr = new char[8];
		for (int i = 0; i < randomStr.length; i++)
		{
			int randomIndex = (int)(Math.random() * chars.length);
			randomStr[i] = chars[randomIndex];
		}
		return new String(randomStr);
	}
}
