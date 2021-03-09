package de.mymiggi.discordbot.server.member.playlist.actions.helpers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class AllPublishedPlaylistEmbed
{
	public EmbedBuilder build(User user, MemberPlaylistManager memberPlaylistManager)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(user.getName() + "'s playlists")
			.setColor(Color.GRAY)
			.setFooter("<3 Miggi")
			.setThumbnail("https://cdn.iconscout.com/icon/free/png-512/music-865-475087.png");
		if (memberPlaylistManager.getAllPlayListInfos().containsKey(user))
		{
			List<MemberPlayListInfoNew> allPlaylists = memberPlaylistManager.getAllPlayListInfos().get(user);
			List<MemberPlayListInfoNew> onlyPublishedPlaylists = new ArrayList<MemberPlayListInfoNew>();
			String playLists = "";
			for (MemberPlayListInfoNew temp : allPlaylists)
			{
				if (temp.isPublicToAllUseres())
				{
					onlyPublishedPlaylists.add(temp);
				}
			}
			if (onlyPublishedPlaylists.size() != 0)
			{
				for (MemberPlayListInfoNew temp : onlyPublishedPlaylists)
				{
					playLists += temp.getPlayListTitle() + "\r\n";
				}
			}
			else
			{
				playLists = "no published yet!";
			}
			embed.setDescription(playLists);
		}
		else
		{
			embed.addField("No playlist created yet!", BotMainCore.prefix + "create PLAYLISTNAME");
		}
		return embed;
	}
}
