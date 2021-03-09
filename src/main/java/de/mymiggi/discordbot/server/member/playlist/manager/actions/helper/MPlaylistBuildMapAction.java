package de.mymiggi.discordbot.server.member.playlist.manager.actions.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistBuildMapAction
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistBuildMapAction.class.getSimpleName());

	public void run(UniversalHibernateClient client, NewMemberPlaylistSong memberPlaylistSong, Map<User, List<NewMemberPlaylistSong>> playLists)
	{
		try
		{
			User user = BotMainCore.api.getUserById(memberPlaylistSong.getUserID()).get();
			if (playLists.containsKey(user))
			{
				playLists.get(user).add(memberPlaylistSong);
			}
			else
			{
				List<NewMemberPlaylistSong> playList = new ArrayList<NewMemberPlaylistSong>();
				playList.add(memberPlaylistSong);
				playLists.put(user, playList);
			}
		}
		catch (InterruptedException | ExecutionException e)
		{
			logger.warn("Error", e);
			client.delete(memberPlaylistSong);
		}
	}
}
