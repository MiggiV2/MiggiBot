package de.mymiggi.discordbot.server.member.playlist.manager.actions;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistAllPlaylistInfosAction;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetJoinedPlaylistInfo;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class MPlaylistCreateNew
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistCreateNew.class.getSimpleName());

	public void run(UniversalHibernateClient client, List<MemberPlayListInfoNew> playListInfoList, String playListTitel, User user) throws Exception
	{
		List<MemberPlayListInfoNew> list = new MPlaylistAllPlaylistInfosAction().run(playListInfoList, client).get(user);
		if (list == null)
		{
			list = new ArrayList<MemberPlayListInfoNew>();
		}
		if (list.size() > 20)
		{
			throw new Exception("Playlist limit reached!");
		}
		boolean contained = false;
		for (MemberPlayListInfoNew temp : list)
		{
			if (temp.getPlayListTitle().equalsIgnoreCase(playListTitel))
			{
				contained = true;
			}
		}
		if (contained)
		{
			logger.warn("Still created");
		}
		else
		{
			MemberPlayListInfoNew newPlayListInfo = new MemberPlayListInfoNew();
			MemberPlayListInfoNew joindPlayListInfo = new MPlaylistGetJoinedPlaylistInfo().run(playListInfoList, client).get(user);
			newPlayListInfo.setJoined(true);
			newPlayListInfo.setPlayListTitel(playListTitel);
			newPlayListInfo.setUserID(user.getId());
			if (joindPlayListInfo != null)
			{
				joindPlayListInfo.setJoined(false);
				client.save(joindPlayListInfo);
			}
			client.save(newPlayListInfo);
			playListInfoList.add(newPlayListInfo);
		}
	}
}
