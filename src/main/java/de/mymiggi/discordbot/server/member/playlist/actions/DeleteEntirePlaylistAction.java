package de.mymiggi.discordbot.server.member.playlist.actions;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastAllPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class DeleteEntirePlaylistAction
{
	private static Logger logger = LoggerFactory.getLogger(DeleteEntirePlaylistAction.class.getSimpleName());

	public void run(MessageCreateEvent event, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		User user = event.getMessageAuthor().asUser().get();
		try
		{
			memberPlaylistManager.deleteEntirePlayList(user);
			event.addReactionsToMessage("😱");
			if (memberPlaylistManager.getCurrentPlayListName(user) != null)
			{
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Current playlist " + memberPlaylistManager.getCurrentPlayListName(user));
				event.getChannel().sendMessage(embed);
			}
			new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
			if (lastEmbedMaps.getLastSongsEmbedMap().containsKey(user))
			{
				String url = lastEmbedMaps.getLastSongsEmbedMap().get(user);
				MessageCoolDown.del(url, event.getChannel(), 1);
			}
		}
		catch (Exception e)
		{
			logger.error("Error", e);
		}
	}
}
