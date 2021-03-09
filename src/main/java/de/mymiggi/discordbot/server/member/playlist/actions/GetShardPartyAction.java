package de.mymiggi.discordbot.server.member.playlist.actions;

import java.util.List;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class GetShardPartyAction
{
	private static Logger logger = LoggerFactory.getLogger(GetShardPartyAction.class.getSimpleName());

	public void run(MessageCreateEvent event, String[] context, MemberPlaylistManager memberPlaylistManager)
	{
		if (context.length > 2 && event.getMessage().getMentionedUsers().size() == 1)
		{
			User targetedUser = event.getMessage().getMentionedUsers().get(0);
			String searchQuery = "";
			for (int i = 2; i < context.length - 1; i++)
			{
				searchQuery += context[i] + " ";
			}
			searchQuery += context[context.length - 1];
			try
			{
				List<NewMemberPlaylistSong> songs = memberPlaylistManager.getSongsByPlayListName(targetedUser, searchQuery);
				logger.info("Done! Found " + songs.size() + " Songs!");
			}
			catch (Exception e)
			{
				logger.warn("Failed", e);
			}
		}
	}
}
