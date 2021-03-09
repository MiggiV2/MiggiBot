package de.mymiggi.discordbot.server.member.playlist.actions;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastSongsEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class DeleteSongFromPlayListAction
{
	private static Logger logger = LoggerFactory.getLogger(MemberPlayListCore.class.getSimpleName());

	public void run(MessageCreateEvent event, String[] context, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		User user = event.getMessageAuthor().asUser().get();
		String searchQuery = "";
		for (int i = 1; i < context.length - 1; i++)
		{
			searchQuery += context[i] + " ";
		}
		searchQuery += context[context.length - 1];
		try
		{
			int index = Integer.parseInt(searchQuery);
			memberPlaylistManager.delSongFormCurrentPlayListByIndex(index - 1, user);
			event.addReactionsToMessage("😿");
		}
		catch (Exception e)
		{
			try
			{
				memberPlaylistManager.delSongFormCurrentPlayListByName(searchQuery, user);
				event.addReactionsToMessage("👍");
			}
			catch (Exception e1)
			{
				logger.error("Error", e1);
				event.addReactionsToMessage("👎");
			}
		}
		if (memberPlaylistManager.getCurrentPlayListName(user) != null)
		{
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Current playlist " + memberPlaylistManager.getCurrentPlayListName(user));
			try
			{
				String lastJoinEmbed = event.getChannel().sendMessage(embed).get().getLink().toString();
				MessageCoolDown.del(lastJoinEmbed, event.getChannel(), 8);
			}
			catch (AssertionError | InterruptedException | ExecutionException e)
			{
				logger.warn("Error", e);
			}
		}
		new UpdateLastSongsEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
	}
}
