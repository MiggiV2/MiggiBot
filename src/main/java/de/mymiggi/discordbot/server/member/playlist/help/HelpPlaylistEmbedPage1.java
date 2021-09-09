package de.mymiggi.discordbot.server.member.playlist.help;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;

public class HelpPlaylistEmbedPage1
{
	public EmbedBuilder run()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Help for playlist")
			.setThumbnail(BotMainCore.api.getYourself().getAvatar())
			.addField("/create [PlayListName]", "Create a new playlist", false)
			.addField("/add [SongeTitle]", "Add a song to your current playlist", false)
			.addField("/join [PlaylistName]", "Select one of your playlists, to join", false)
			.addField("/see", "See all your songs, in your current playlist", false)
			.addField("/rm", "Delete a song by title or track number \r\n Aliases: del, delete", false)
			.setColor(Color.GRAY)
			.setFooter("<3 Miggi");
		return embed;
	}
}
