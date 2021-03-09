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
			.addField(String.format("%screate [PlayListName]", BotMainCore.prefix), "Create a new playlist", false)
			.addField(String.format("%sadd [SongeTitle]", BotMainCore.prefix), "Add a song to your current playlist", false)
			.addField(String.format("%sjoin [PlaylistName]", BotMainCore.prefix), "Select one of your playlists, to join", false)
			.addField(String.format("%ssee", BotMainCore.prefix), "See all your songs, in your current playlist", false)
			.addField(String.format("%srm", BotMainCore.prefix, BotMainCore.prefix, BotMainCore.prefix), "Delete a song by title or track number \r\n Aliases: del, delete", false)
			.setColor(Color.GRAY)
			.setFooter("<3 Miggi");
		return embed;
	}
}
