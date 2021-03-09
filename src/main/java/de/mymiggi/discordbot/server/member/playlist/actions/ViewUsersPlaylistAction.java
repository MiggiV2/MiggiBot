package de.mymiggi.discordbot.server.member.playlist.actions;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.core.embed.PlaylistSongsEmbed;
import de.mymiggi.discordbot.server.member.playlist.core.embed.ReactionEventHandler;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.MemberPlayListInfoNew;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class ViewUsersPlaylistAction
{
	private static Logger logger = LoggerFactory.getLogger(UpdatePlaylistPublishStatusAction.class.getSimpleName());

	public void run(MemberPlaylistManager memberPlaylistManager, MessageCreateEvent event, String[] context, LastEmbedMaps lastEmbedMaps)
	{
		if (context.length >= 3 && !event.getMessage().getMentionedUsers().isEmpty())
		{
			User user = event.getMessage().getMentionedUsers().get(0);
			event.addReactionsToMessage("👁");
			try
			{
				String playListName = "";
				for (int i = 2; i < context.length; i++)
				{
					if (i == context.length - 1)
					{
						playListName += context[i];
					}
					else
					{
						playListName += context[i] + " ";
					}
				}
				if (isPublish(memberPlaylistManager, playListName, user))
				{
					List<NewMemberPlaylistSong> playListSongs = memberPlaylistManager.getSongsByPlayListName(user, playListName);
					EmbedBuilder embed = new PlaylistSongsEmbed().run(user, playListSongs, playListName, 1);
					ReactionEventHandler listener = new ReactionEventHandler(playListSongs, playListName);
					Message lastEmbed = event.getChannel().sendMessage(embed).get();
					String lastEmbedURL = lastEmbed.getLink().toString();
					boolean needFastSkip = playListSongs.size() > 30;
					boolean needSkip = playListSongs.size() > 10;
					if (needFastSkip)
					{
						lastEmbed.addReaction("⏫");
					}
					if (needSkip)
					{
						lastEmbed.addReaction("🔼");
					}
					lastEmbed.addReaction("❌");
					if (needSkip)
					{
						lastEmbed.addReaction("🔽");
					}
					if (needFastSkip)
					{
						lastEmbed.addReaction("⏬");
					}
					lastEmbed.addReactionAddListener(reactionEvent -> listener.run(reactionEvent));
					lastEmbedMaps.getLastSongsEmbedMap().put(user, lastEmbedURL);
				}
				else
				{
					logger.warn("Playlist not found!" + context[2]);
					EmbedBuilder embed = new EmbedBuilder()
						.setTitle("Playlist not found!")
						.setColor(Color.ORANGE)
						.setDescription("Use " + BotMainCore.prefix + " to see all his|her playlists!\r\n");
					event.getChannel().sendMessage(embed);
				}
			}
			catch (AssertionError | Exception e)
			{
				logger.error("Critical error!", e);
				String embedMessage = "Error in line " + e.getLocalizedMessage() + "\r\n" + "Cause: " + e.getCause();
				if (e.getMessage() != null)
				{
					embedMessage += "Message: " + e.getMessage();
				}
				EmbedBuilder embed = new EmbedBuilder()
					.setTitle("An error occured!")
					.setDescription(embedMessage)
					.setColor(Color.RED);
				event.getChannel().sendMessage(embed);
			}
		}
		else
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Something is missing!")
				.setColor(Color.ORANGE)
				.setDescription("Example:\r\n" + BotMainCore.prefix + "view @Miggi BlackAce");
			event.getChannel().sendMessage(embed);
		}
	}

	private boolean isPublish(MemberPlaylistManager memberPlaylistManager, String playListName, User user)
	{
		if (memberPlaylistManager.getAllPlayListInfos().containsKey(user))
		{
			List<MemberPlayListInfoNew> playlistsInfos = memberPlaylistManager.getAllPlayListInfos().get(user);
			for (MemberPlayListInfoNew temp : playlistsInfos)
			{
				if (temp.getPlayListTitle().equals(playListName) && temp.isPublicToAllUseres())
				{
					return true;
				}
			}
			for (MemberPlayListInfoNew temp : playlistsInfos)
			{
				if (temp.getPlayListTitle().equalsIgnoreCase(playListName) && temp.isPublicToAllUseres())
				{
					return true;
				}
			}
		}
		return false;
	}
}
