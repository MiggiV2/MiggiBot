package de.mymiggi.discordbot.server.member.playlist.help;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;

public class HelpPlaylistEmbedPage2
{
	public EmbedBuilder run()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Help for playlist")
			.setThumbnail(BotMainCore.api.getYourself().getAvatar())
			.addField(String.format("%srmPlayList", BotMainCore.prefix, BotMainCore.prefix, BotMainCore.prefix), "Delete the entire playlist (current playlist)\r\n Aliases: delPlayList, deletePlayList", false)
			.addField(String.format("%splaylists", BotMainCore.prefix), "List all your playlists", false)
			.addField(String.format("%spublish", BotMainCore.prefix), "Publish your current playlist.\r\nEvery user can then, see & play your playlist", false)
			.addField(String.format("%shide", BotMainCore.prefix), "Hide your playlist, from other users [Default]", false)
			.addField(String.format("%scheck @USER", BotMainCore.prefix), "See all published playlists from a specific user", false)
			.setColor(Color.GRAY)
			.setFooter("<3 Miggi");
		return embed;
	}
}
