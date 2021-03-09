package de.mymiggi.discordbot.server.member.playlist.core.embed;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class ReactionEventHandler
{
	private List<NewMemberPlaylistSong> currentPlayListSongs;
	private int currentPage = 1;
	private String playListName;

	public ReactionEventHandler(List<NewMemberPlaylistSong> currentPlayListSongs, String playListName)
	{
		this.currentPlayListSongs = currentPlayListSongs;
		this.playListName = playListName;
	}

	public void run(ReactionAddEvent reactionEvent)
	{
		if (reactionEvent.getEmoji().equalsEmoji("🔽") && !reactionEvent.getUser().get().isYourself() && hasNextPage())
		{
			currentPage++;
			try
			{
				reactionEvent.getMessage().get().edit(new PlaylistSongsEmbed().run(reactionEvent.getUser().get(), currentPlayListSongs, playListName, currentPage)).get();
			}
			catch (InterruptedException | ExecutionException e)
			{
			}
		}
		if (reactionEvent.getEmoji().equalsEmoji("🔼") && !reactionEvent.getUser().get().isYourself() && currentPage != 1)
		{
			currentPage--;
			try
			{
				reactionEvent.getMessage().get().edit(new PlaylistSongsEmbed().run(reactionEvent.getUser().get(), currentPlayListSongs, playListName, currentPage)).get();
			}
			catch (InterruptedException | ExecutionException e)
			{
			}
		}
		if (reactionEvent.getEmoji().equalsEmoji("⏫") && !reactionEvent.getUser().get().isYourself() && currentPage != 1)
		{
			for (int i = 0; i < 4; i++)
			{
				currentPage--;
				if (currentPage == 1)
				{
					break;
				}
			}
			try
			{
				reactionEvent.getMessage().get().edit(new PlaylistSongsEmbed().run(reactionEvent.getUser().get(), currentPlayListSongs, playListName, currentPage)).get();
			}
			catch (InterruptedException | ExecutionException e)
			{
			}
		}
		if (reactionEvent.getEmoji().equalsEmoji("⏬") && !reactionEvent.getUser().get().isYourself() && hasNextPage())
		{
			for (int i = 0; i < 4; i++)
			{
				currentPage++;
				if (!hasNextPage())
				{
					break;
				}
			}
			try
			{
				reactionEvent.getMessage().get().edit(new PlaylistSongsEmbed().run(reactionEvent.getUser().get(), currentPlayListSongs, playListName, currentPage)).get();
			}
			catch (InterruptedException | ExecutionException e)
			{
			}
		}
		if (reactionEvent.getEmoji().equalsEmoji("❌") && !reactionEvent.getUser().get().isYourself())
		{
			reactionEvent.getMessage().get().delete();
		}
		else if (!reactionEvent.getUser().get().isYourself())
		{
			reactionEvent.removeReactionByEmojiFromMessage(reactionEvent.getUser().get(), reactionEvent.getEmoji());
		}
	}

	private boolean hasNextPage()
	{
		int nextStartPostion = 10 * (currentPage - 1);
		if (currentPlayListSongs.size() - nextStartPostion > 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
