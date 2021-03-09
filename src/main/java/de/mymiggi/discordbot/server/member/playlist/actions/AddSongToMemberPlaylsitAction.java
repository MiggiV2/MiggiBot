package de.mymiggi.discordbot.server.member.playlist.actions;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastAllPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class AddSongToMemberPlaylsitAction
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

		if (memberPlaylistManager.getJoinedPlayListInfos().containsKey(user))
		{
			String playListTitel = memberPlaylistManager.getJoinedPlayListInfos().get(user).getPlayListTitle();
			try
			{
				String resultURL = memberPlaylistManager.addSong(searchQuery, user, playListTitel);
				event.addReactionsToMessage("👍");
				event.getChannel()
					.sendMessage("**Successfully added to playlist " + playListTitel + " **" + resultURL)
					.thenAccept(message -> {
						MessageCoolDown.del(message.getLink().toString(), event.getChannel(), 10);
					});
				new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
			}
			catch (Exception e)
			{
				if (e.getMessage() != null && e.getMessage().equals("Song is already in playlist"))
				{
					EmbedBuilder embed = new EmbedBuilder();
					embed.setTitle(e.getMessage())
						.setColor(Color.ORANGE);
					try
					{
						Message message = event.getChannel().sendMessage(embed).get();
						MessageCoolDown.del(message.getLink().toString(), event.getChannel(), 10);
					}
					catch (InterruptedException | ExecutionException e1)
					{
						String channelName = event.getChannel().asServerTextChannel().get().getName();
						String serverName = event.getServer().get().getName();
						logger.warn("Failed to send message in channel {} in server {}", channelName, serverName);
					}
				}
				else
				{
					logger.error("Error", e);
				}
				event.addReactionsToMessage("👎");
			}
		}
		else
		{
			logger.warn("No created playList!");
			event.addReactionsToMessage("👎");
		}
	}
}
