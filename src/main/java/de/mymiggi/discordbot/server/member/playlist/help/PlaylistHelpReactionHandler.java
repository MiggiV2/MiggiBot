package de.mymiggi.discordbot.server.member.playlist.help;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

public class PlaylistHelpReactionHandler
{
	private int currentPage;

	public PlaylistHelpReactionHandler(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public void run(ReactionAddEvent reactionEvent)
	{
		if (reactionEvent.getEmoji().equalsEmoji("➡️") && !reactionEvent.getUser().get().isYourself() && currentPage == 1)
		{
			reactionEvent.getMessage().get().edit(new HelpPlaylistEmbedPage2().run());
			currentPage = 2;
		}
		if (reactionEvent.getEmoji().equalsEmoji("⬅️") && !reactionEvent.getUser().get().isYourself() && currentPage == 2)
		{
			reactionEvent.getMessage().get().edit(new HelpPlaylistEmbedPage1().run());
			currentPage = 1;
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
}
