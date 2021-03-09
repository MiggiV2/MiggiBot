package de.mymiggi.discordbot.server.member.playlist.actions.helpers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class SongsEmbed
{
	public EmbedBuilder build(User user, MemberPlaylistManager memberPlaylistManager)
	{
		List<NewMemberPlaylistSong> currentPlayListSongs;
		try
		{
			currentPlayListSongs = memberPlaylistManager.getSongsFromCurrentPlayList(user);
		}
		catch (Exception e)
		{
			currentPlayListSongs = new ArrayList<NewMemberPlaylistSong>();
		}
		EmbedBuilder embed = new EmbedBuilder()
			.setColor(Color.LIGHT_GRAY);
		String songsStr = "";
		String playListName = memberPlaylistManager.getCurrentPlayListName(user);
		if (!currentPlayListSongs.isEmpty() && playListName != null)
		{
			for (NewMemberPlaylistSong temp : currentPlayListSongs)
			{
				songsStr += temp.getTitle() + "\r\n";
			}
			embed.setTitle("Your own playlist")
				.addField("Playlist: " + playListName, songsStr)
				.setFooter("<3 Miggi");
		}
		else if (currentPlayListSongs.isEmpty() && playListName != null)
		{
			embed.setTitle("Your own playlist")
				.setDescription(playListName)
				.addField("No songs added yet!", BotMainCore.prefix + "add SONGS-TITEL")
				.setThumbnail("https://cdn.iconscout.com/icon/free/png-512/list-191-433790.png")
				.setFooter("<3 Miggi");
		}
		else if (currentPlayListSongs.isEmpty() && playListName == null)
		{
			embed.setTitle("Your own playlist")
				.addField("Not created yet!", BotMainCore.prefix + "create PLAYLISTNAME")
				.setThumbnail("https://cdn.iconscout.com/icon/free/png-512/list-191-433790.png")
				.setFooter("<3 Miggi");
		}
		return embed;
	}
}
