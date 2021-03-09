package de.mymiggi.discordbot.server.member.playlist.manager.actions.get;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.server.member.playlist.manager.actions.helper.MPlaylistBuildMapAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MPlaylistAllSongsAction
{
	public Map<User, List<NewMemberPlaylistSong>> run(UniversalHibernateClient client)
	{
		Map<User, List<NewMemberPlaylistSong>> playListsMap = new HashMap<User, List<NewMemberPlaylistSong>>();
		List<NewMemberPlaylistSong> mixedPlayLists = client.getList(NewMemberPlaylistSong.class);
		for (NewMemberPlaylistSong song : mixedPlayLists)
		{
			new MPlaylistBuildMapAction().run(client, song, playListsMap);
		}
		return playListsMap;
	}
}
