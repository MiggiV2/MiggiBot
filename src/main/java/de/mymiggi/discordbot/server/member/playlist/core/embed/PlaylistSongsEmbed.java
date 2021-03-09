package de.mymiggi.discordbot.server.member.playlist.core.embed;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class PlaylistSongsEmbed
{
	public EmbedBuilder run(User user, List<NewMemberPlaylistSong> currentPlayListSongs, String playListName, int page)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setColor(Color.LIGHT_GRAY);
		String songsStr = "";
		if (!currentPlayListSongs.isEmpty() && playListName != null)
		{
			int startPostion = 10 * (page - 1);
			if (currentPlayListSongs.size() - startPostion > 10)
			{
				for (int i = startPostion; i < page * 10; i++)
				{
					songsStr += currentPlayListSongs.get(i).getTitle() + "\r\n";
				}
			}
			else
			{
				for (int i = 10 * (page - 1); i < currentPlayListSongs.size(); i++)
				{
					songsStr += currentPlayListSongs.get(i).getTitle() + "\r\n";
				}
			}
			embed.setTitle("Your own playlist. Page " + page)
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
