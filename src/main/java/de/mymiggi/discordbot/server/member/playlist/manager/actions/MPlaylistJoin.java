package de.mymiggi.discordbot.server.member.playlist.manager.actions;

import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAllPlaylistInfosAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetJoinedPlaylistInfo;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.helper.MPlaylistGetPlaylistInfoByName;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class MPlaylistJoin
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistJoin.class.getSimpleName());

	public void run(User user, UniversalHibernateClient client, List<MemberPlayListInfoNew> playListInfoList, String playListInfo) throws Exception
	{
		Map<User, List<MemberPlayListInfoNew>> userAndPlayListInfo = new MPlaylistAllPlaylistInfosAction().run(playListInfoList, client);
		List<MemberPlayListInfoNew> usersPlayList = userAndPlayListInfo.get(user);
		if (!userAndPlayListInfo.containsKey(user))
		{
			logger.warn("No playlist for user " + user.getName() + " found!");
			throw new Exception("No playlist for user found!");
		}
		if (userAndPlayListInfo.get(user).size() == 1)
		{
			logger.warn("User " + user.getName() + " has just one! -> cant join");
		}
		else
		{
			MemberPlayListInfoNew result = new MPlaylistGetPlaylistInfoByName().run(usersPlayList, playListInfo);
			Map<User, MemberPlayListInfoNew> userAndJoinedPlaylistMap = new MPlaylistGetJoinedPlaylistInfo().run(playListInfoList, client);
			if (userAndJoinedPlaylistMap.containsKey(user))
			{
				MemberPlayListInfoNew currentlyJoinedPlaylist = userAndJoinedPlaylistMap.get(user);
				if (currentlyJoinedPlaylist.getPlayListTitle().equalsIgnoreCase(playListInfo))
				{
					throw new Exception("Still in this playList!");
				}
				playListInfoList.remove(result);
				playListInfoList.remove(currentlyJoinedPlaylist);
				result.setJoined(true);
				currentlyJoinedPlaylist.setJoined(false);
				client.save(result);
				client.save(currentlyJoinedPlaylist);
				playListInfoList.add(currentlyJoinedPlaylist);
				playListInfoList.add(result);
			}
			else
			{
				playListInfoList.remove(result);
				result.setJoined(true);
				client.save(result);
				playListInfoList.add(result);
				logger.warn("User " + user.getName() + " has no joined playlist! This shouldn't be!");
			}
		}
	}
}
