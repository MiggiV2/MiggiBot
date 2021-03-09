package de.mymiggi.discordbot.server.member.playlist.actions;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastAllPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;

public class UpdatePlaylistPublishStatusAction
{
	private Logger logger = LoggerFactory.getLogger(UpdatePlaylistPublishStatusAction.class.getSimpleName());

	public void run(MessageCreateEvent event, boolean publishStatus, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		User user = event.getMessageAuthor().asUser().get();
		if (memberPlaylistManager.getJoinedPlayListInfos().containsKey(user))
		{
			try
			{
				memberPlaylistManager.updatePlayListPublishStatus(user, publishStatus);
				event.addReactionsToMessage("👍");
				new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
			}
			catch (Exception e)
			{
				logger.error("Error", e);
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
