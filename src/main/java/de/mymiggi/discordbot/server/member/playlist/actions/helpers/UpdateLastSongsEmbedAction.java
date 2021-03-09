package de.mymiggi.discordbot.server.member.playlist.actions.helpers;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;

public class UpdateLastSongsEmbedAction
{
	private Logger logger = LoggerFactory.getLogger(UpdateLastSongsEmbedAction.class.getSimpleName());

	public void run(User user, LastEmbedMaps lastEmbedMaps, MemberPlaylistManager memberPlaylistManager)
	{
		if (lastEmbedMaps.getLastSongsEmbedMap().containsKey(user))
		{
			String lastURL = lastEmbedMaps.getLastSongsEmbedMap().get(user);
			EmbedBuilder allPlayListEmbed = new SongsEmbed().build(user, memberPlaylistManager);
			BotMainCore.api.getMessageByLink(lastURL).ifPresent(message -> {
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
					lastEmbedMaps.getLastSongsEmbedMap().remove(user);
				}
			});
		}
	}
}
