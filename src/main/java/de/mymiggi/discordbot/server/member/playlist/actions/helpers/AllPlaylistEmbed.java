package de.mymiggi.discordbot.server.member.playlist.actions.helpers;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

public class AllPlaylistEmbed
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
			List<MemberPlayListInfoNew> list = memberPlaylistManager.getAllPlayListInfos().get(user);
			String playLists = "";
			for (MemberPlayListInfoNew temp : list)
			{
				String publishStatus;
				if (temp.isPublicToAllUseres())
				{
					publishStatus = " [publish]";
				}
				else
				{
					publishStatus = " [hiden]";
				}
				if (temp.getPlayListTitle().equals(memberPlaylistManager.getCurrentPlayListName(user)))
				{
					playLists += "**" + temp.getPlayListTitle() + publishStatus + "**\r\n";
				}
				else
				{
					playLists += temp.getPlayListTitle() + publishStatus + "\r\n";
				}
			}
			embed.setDescription(playLists);
		}
		else
		{
			embed.addField("No created yet!", BotMainCore.prefix + "create PLAYLISTNAME");
		}
		return embed;
	}
}
