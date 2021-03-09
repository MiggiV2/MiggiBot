package de.mymiggi.discordbot.server.member.playlist.manager.actions.delete;

import java.util.List;

import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.get.MPlaylistGetCurrentPlaylistSongsAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistDeleteSongByIndexAction
{
	private Logger logger = LoggerFactory.getLogger(MPlaylistDeleteSongByIndexAction.class.getSimpleName());

	public void run(UniversalHibernateClient client, List<MemberPlayListInfoNew> playListInfoList, int index, User user) throws Exception
	{
		List<NewMemberPlaylistSong> songs = new MPlaylistGetCurrentPlaylistSongsAction().run(client, user, playListInfoList);
		if (songs.size() > index)
		{
			client.delete(songs.get(index));
		}
		else
		{
			logger.warn("Index out of bounce! " + index);
		}
	}
}
