package de.mymiggi.discordbot.server.member.playlist.manager.actions.get;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistGetCurrentPlaylistSongsAction
{
	public List<NewMemberPlaylistSong> run(UniversalHibernateClient client, User user, List<MemberPlayListInfoNew> playListInfoList) throws Exception
	{
		Map<User, MemberPlayListInfoNew> getJoinedPlayListInfos = new MPlaylistGetJoinedPlaylistInfo().run(playListInfoList, client);
		if (!getJoinedPlayListInfos.containsKey(user))
		{
			throw new Exception("No playlist created yet!");
		}
		String currentPlayListTitel = getJoinedPlayListInfos.get(user).getPlayListTitle();
		List<NewMemberPlaylistSong> allSongs = new MPlaylistAllSongsAction().run(client).get(user);
		List<NewMemberPlaylistSong> currentPlayListSongs = new ArrayList<NewMemberPlaylistSong>();
		if (allSongs != null)
		{
			for (NewMemberPlaylistSong temp : allSongs)
			{
				if (temp.getPlayListName().equalsIgnoreCase(currentPlayListTitel))
				{
					currentPlayListSongs.add(temp);
				}
			}
		}
		return currentPlayListSongs;
	}
}
