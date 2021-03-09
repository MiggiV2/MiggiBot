package de.mymiggi.discordbot.server.member.playlist.manager.actions.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class MPlaylistAllPlaylistInfosAction
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistAllPlaylistInfosAction.class.getSimpleName());

	public Map<User, List<MemberPlayListInfoNew>> run(List<MemberPlayListInfoNew> playListInfoList, UniversalHibernateClient client)
	{
		Map<User, List<MemberPlayListInfoNew>> userAndPlayListInfo = new HashMap<User, List<MemberPlayListInfoNew>>();
		if (playListInfoList.isEmpty())
		{
			playListInfoList = client.getList(MemberPlayListInfoNew.class);
		}
		for (MemberPlayListInfoNew temp : playListInfoList)
		{
			try
			{
				User user = BotMainCore.api.getUserById(temp.getUserID()).get();
				if (userAndPlayListInfo.containsKey(user))
				{
					userAndPlayListInfo.get(user).add(temp);
				}
				else
				{
					List<MemberPlayListInfoNew> tempList = new ArrayList<MemberPlayListInfoNew>();
					tempList.add(temp);
					userAndPlayListInfo.put(user, tempList);
				}
			}
			catch (InterruptedException | ExecutionException e)
			{
				logger.warn("Error", e);
				client.delete(temp);
			}
		}
		return userAndPlayListInfo;
	}
}
