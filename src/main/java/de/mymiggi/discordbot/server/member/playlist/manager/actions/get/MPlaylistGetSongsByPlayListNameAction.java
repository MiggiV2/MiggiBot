package de.mymiggi.discordbot.server.member.playlist.manager.actions.get;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistGetSongsByPlayListNameAction
{
	public List<NewMemberPlaylistSong> run(UniversalHibernateClient client, List<MemberPlayListInfoNew> playListInfoList, User user, String playListTitle) throws Exception
	{
		Map<User, List<MemberPlayListInfoNew>> allPlaylistInfos = new MPlaylistAllPlaylistInfosAction().run(playListInfoList, client);
		List<NewMemberPlaylistSong> songs = new ArrayList<NewMemberPlaylistSong>();
		if (!allPlaylistInfos.containsKey(user))
		{
			throw new Exception("No playlist exists for user " + user.getName());
		}
		List<MemberPlayListInfoNew> playListInfos = allPlaylistInfos.get(user);
		for (MemberPlayListInfoNew tempPlayListInfo : playListInfos)
		{
			if (tempPlayListInfo.getPlayListTitle().equalsIgnoreCase(playListTitle))
			{
				if (!tempPlayListInfo.isPublicToAllUseres())
				{
					throw new Exception("Playlist " + playListTitle + " is not public! Ask " + user.getName() + " to do " + BotMainCore.prefix + "publish");
				}
				for (NewMemberPlaylistSong tempSong : new MPlaylistAllSongsAction().run(client).get(user))
				{
					if (tempSong.getPlayListName().equalsIgnoreCase(playListTitle))
					{
						songs.add(tempSong);
					}
				}
				return songs;
			}
		}
		throw new Exception("No playlist for user " + user.getName() + " with this name " + playListTitle);
	}
}
