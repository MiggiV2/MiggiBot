package de.mymiggi.discordbot.server.member.playlist.manager.actions;

import java.util.List;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAllPlaylistInfosAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetJoinedPlaylistInfo;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class MPlaylistUpdatePublishStatus
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistUpdatePublishStatus.class.getSimpleName());

	public void run(User user, boolean publishStatus, List<MemberPlayListInfoNew> playListInfoList, UniversalHibernateClient client) throws Exception
	{
		List<MemberPlayListInfoNew> getAllPlayListInfos = new MPlaylistAllPlaylistInfosAction().run(playListInfoList, client).get(user);
		if (getAllPlayListInfos.size() == 0)
		{
			logger.warn("User " + user.getName() + " has no playlist! -> cant update status");
		}
		else
		{
			MemberPlayListInfoNew currentlyJoinedPlaylist = new MPlaylistGetJoinedPlaylistInfo().run(playListInfoList, client).get(user);
			playListInfoList.remove(currentlyJoinedPlaylist);
			currentlyJoinedPlaylist.setPublicToAllUseres(publishStatus);
			client.save(currentlyJoinedPlaylist);
			playListInfoList.add(currentlyJoinedPlaylist);
		}
	}
}
