package de.mymiggi.discordbot.server.member.playlist.manager.actions.delete;

import java.util.List;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetCurrentPlaylistSongsAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistDeleteSongeByNameAction
{
	private Logger logger = LoggerFactory.getLogger(MemberPlaylistManager.class.getSimpleName());

	public void run(UniversalHibernateClient client, List<MemberPlayListInfoNew> playListInfoList, String songTitle, User user) throws Exception
	{
		List<NewMemberPlaylistSong> songs = new MPlaylistGetCurrentPlaylistSongsAction().run(client, user, playListInfoList);
		boolean found = false;
		for (NewMemberPlaylistSong temp : songs)
		{
			if (temp.getTitle().equals(songTitle))
			{
				client.delete(temp);
				found = true;
			}
		}
		logger.info("Was found " + found);
	}
}
