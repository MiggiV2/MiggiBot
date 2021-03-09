package de.mymiggi.discordbot.server.member.playlist.manager.actions.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class MPlaylistGetJoinedPlaylistInfo
{
	private Logger logger = LoggerFactory.getLogger(MemberPlaylistManager.class.getSimpleName());

	public Map<User, MemberPlayListInfoNew> run(List<MemberPlayListInfoNew> playListInfoList, UniversalHibernateClient client)
	{
		Map<User, MemberPlayListInfoNew> userAndPlayListInfo = new HashMap<User, MemberPlayListInfoNew>();
		if (playListInfoList.isEmpty())
		{
			playListInfoList = client.getList(MemberPlayListInfoNew.class);
		}
		for (MemberPlayListInfoNew temp : playListInfoList)
		{
			if (temp.isJoined())
			{
				User user;
				try
				{
					user = BotMainCore.api.getUserById(temp.getUserID()).get();
					userAndPlayListInfo.put(user, temp);
				}
				catch (InterruptedException | ExecutionException e)
				{
					logger.warn("Error", e);
					client.delete(temp);
				}
			}
		}
		return userAndPlayListInfo;
	}
}
