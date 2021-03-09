package de.mymiggi.discordbot.server.member.playlist.manager.actions.delete;

import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAllPlaylistInfosAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetCurrentPlaylistSongsAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetJoinedPlaylistInfo;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistDeletePlaylistAction
{
	public void run(UniversalHibernateClient client, User user, List<MemberPlayListInfoNew> playListInfoList) throws Exception
	{
		List<NewMemberPlaylistSong> songs = new MPlaylistGetCurrentPlaylistSongsAction().run(client, user, playListInfoList);
		Map<User, MemberPlayListInfoNew> getJoinedPlayListInfos = new MPlaylistGetJoinedPlaylistInfo().run(playListInfoList, client);
		if (!getJoinedPlayListInfos.containsKey(user))
		{
			throw new Exception("No playlist found");
		}
		MemberPlayListInfoNew memberPlaylistInfo = getJoinedPlayListInfos.get(user);
		if (songs.isEmpty())
		{
			client.delete(memberPlaylistInfo);
		}
		else
		{
			client.delete(memberPlaylistInfo);
			client.deleteList(songs);
		}
		playListInfoList.remove(memberPlaylistInfo);
		Map<User, List<MemberPlayListInfoNew>> playlistInfoMap = new MPlaylistAllPlaylistInfosAction().run(playListInfoList, client);
		if (playlistInfoMap.get(user) != null && !playlistInfoMap.get(user).isEmpty())
		{
			List<MemberPlayListInfoNew> playlistInfo = playlistInfoMap.get(user);
			MemberPlayListInfoNew lastAddedPlayList = playlistInfo.get(playlistInfo.size() - 1);
			lastAddedPlayList.setJoined(true);
			client.save(lastAddedPlayList);
		}
	}

}
