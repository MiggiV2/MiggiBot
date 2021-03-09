package de.mymiggi.discordbot.server.member.playlist.manager.actions.helper;

import java.util.List;

import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class MPlaylistGetPlaylistInfoByName
{
	public MemberPlayListInfoNew run(List<MemberPlayListInfoNew> usersPlayList, String playListTitel) throws Exception
	{
		for (MemberPlayListInfoNew temp : usersPlayList)
		{
			if (temp.getPlayListTitle().equalsIgnoreCase(playListTitel))
			{
				return temp;
			}
		}
		throw new Exception("Not found!");
	}
}
