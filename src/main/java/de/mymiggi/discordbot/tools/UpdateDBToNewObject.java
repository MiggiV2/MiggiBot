package de.mymiggi.discordbot.tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfo;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;

@SuppressWarnings("deprecation")
public class UpdateDBToNewObject
{
	public void run(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();
		if (event.getMessageAuthor().isBotOwner())
		{
			UniversalHibernateClient client = new UniversalHibernateClient();
			List<MemberPlayListInfo> oldPlaylists = client.getList(MemberPlayListInfo.class);
			List<MemberPlayListInfoNew> convertedPlaylists = new ArrayList<MemberPlayListInfoNew>();
			for (MemberPlayListInfo temp : oldPlaylists)
			{
				MemberPlayListInfoNew newPlaylist = new MemberPlayListInfoNew()
					.setJoined(temp.isJoined())
					.setPlayListTitel(temp.getPlayListTitle())
					.setPublicToAllUseres(temp.isPublicToAllUseres())
					.setUserID(temp.getUserID());
				convertedPlaylists.add(newPlaylist);
			}
			client.saveList(convertedPlaylists);
			embed
				.setTitle("Converted " + oldPlaylists.size() + " playlistinfos!")
				.setColor(Color.GREEN);
		}
		else
		{
			embed
				.setTitle("You are not the bot owner!")
				.setColor(Color.RED);
		}
		event.getChannel().sendMessage(embed);
	}
}
