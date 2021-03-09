package de.mymiggi.discordbot.server.member.playlist.actions.helpers;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;

public class UpdateLastAllPlaylistEmbedAction
{
	private Logger logger = LoggerFactory.getLogger(UpdateLastAllPlaylistEmbedAction.class.getSimpleName());

	public void run(User user, LastEmbedMaps lastEmbedMaps, MemberPlaylistManager memberPlaylistManager)
	{
		if (lastEmbedMaps.getLastAllPlaylistEmbedMap().containsKey(user))
		{
			String lastAllPlayListEmbedURL = lastEmbedMaps.getLastAllPlaylistEmbedMap().get(user);
			EmbedBuilder allPlayListEmbed = new AllPlaylistEmbed().build(user, memberPlaylistManager);
			BotMainCore.api.getMessageByLink(lastAllPlayListEmbedURL).ifPresent(message -> {
				try
				{
					message.get().edit(allPlayListEmbed);
				}
				catch (InterruptedException | ExecutionException e)
				{
					if (e.getMessage().equals("org.javacord.api.exception.UnknownMessageException: Unknown Message"))
					{
						logger.warn("Unknown Message");
					}
					else
					{
						logger.warn("Error", e);
					}
					lastEmbedMaps.getLastAllPlaylistEmbedMap().remove(user);
				}
			});
		}
		if (lastEmbedMaps.getLastPublishedPlaylistEmbedMap().containsKey(user))
		{
			String lastAllPublishedPlayListEmbedURL = lastEmbedMaps.getLastPublishedPlaylistEmbedMap().get(user);
			EmbedBuilder allPublishedPlayListEmbed = new AllPublishedPlaylistEmbed().build(user, memberPlaylistManager);
			BotMainCore.api.getMessageByLink(lastAllPublishedPlayListEmbedURL).ifPresent(message -> {
				try
				{
					message.get().edit(allPublishedPlayListEmbed);
				}
				catch (InterruptedException | ExecutionException e)
				{
					if (e.getMessage().equals("org.javacord.api.exception.UnknownMessageException: Unknown Message"))
					{
						logger.warn("Unknown Message");
					}
					else
					{
						logger.warn("Error", e);
					}
					lastEmbedMaps.getLastPublishedPlaylistEmbedMap().remove(user);
				}
			});
		}
	}
}
