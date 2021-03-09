package de.mymiggi.discordbot.server.member.playlist.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.MPlaylistCreateNew;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.MPlaylistJoin;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.MPlaylistUpdatePublishStatus;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.delete.MPlaylistDeletePlaylistAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.delete.MPlaylistDeleteSongByIndexAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.delete.MPlaylistDeleteSongeByNameAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAddSongAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAllPlaylistInfosAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAllSongsAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetCurrentPlaylistSongsAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetJoinedPlaylistInfo;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetSongsByPlayListNameAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MemberPlaylistManager
{
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private List<MemberPlayListInfoNew> playListInfoList = new ArrayList<MemberPlayListInfoNew>();

	public MemberPlaylistManager()
	{
		playListInfoList = client.getList(MemberPlayListInfoNew.class);
	}

	public Map<User, List<NewMemberPlaylistSong>> allSongsFromDB()
	{
		return new MPlaylistAllSongsAction().run(client);
	}

	public String addSong(String searchQuery, User user, String playListTitel) throws Exception
	{
		return new MPlaylistAddSongAction().run(client, searchQuery, user, playListTitel);
	}

	public void deleteEntirePlayList(User user) throws Exception
	{
		new MPlaylistDeletePlaylistAction().run(client, user, playListInfoList);
	}

	public void delSongFormCurrentPlayListByName(String songTitle, User user) throws Exception
	{
		new MPlaylistDeleteSongeByNameAction().run(client, playListInfoList, songTitle, user);
	}

	public void delSongFormCurrentPlayListByIndex(int index, User user) throws Exception
	{
		new MPlaylistDeleteSongByIndexAction().run(client, playListInfoList, index, user);
	}

	public String getCurrentPlayListName(User user)
	{
		if (getJoinedPlayListInfos().containsKey(user))
		{
			return getJoinedPlayListInfos().get(user).getPlayListTitle();
		}
		return null;
	}

	public void createNewPlayList(String playListTitel, User user) throws Exception
	{
		new MPlaylistCreateNew().run(client, playListInfoList, playListTitel, user);
	}

	public Map<User, List<MemberPlayListInfoNew>> getAllPlayListInfos()
	{
		return new MPlaylistAllPlaylistInfosAction().run(playListInfoList, client);
	}

	public Map<User, MemberPlayListInfoNew> getJoinedPlayListInfos()
	{
		return new MPlaylistGetJoinedPlaylistInfo().run(playListInfoList, client);
	}

	public void joinPlayList(User user, String playListInfo) throws Exception
	{
		new MPlaylistJoin().run(user, client, playListInfoList, playListInfo);
	}

	public void updatePlayListPublishStatus(User user, boolean publishStatus) throws Exception
	{
		new MPlaylistUpdatePublishStatus().run(user, publishStatus, playListInfoList, client);
	}

	public List<NewMemberPlaylistSong> getSongsByPlayListName(User user, String playListTitle) throws Exception
	{
		return new MPlaylistGetSongsByPlayListNameAction().run(client, playListInfoList, user, playListTitle);
	}

	public List<NewMemberPlaylistSong> getSongsFromCurrentPlayList(User user) throws Exception
	{
		return new MPlaylistGetCurrentPlaylistSongsAction().run(client, user, playListInfoList);
	}

	public int getTotalPlaylists()
	{
		return playListInfoList.size();
	}
}