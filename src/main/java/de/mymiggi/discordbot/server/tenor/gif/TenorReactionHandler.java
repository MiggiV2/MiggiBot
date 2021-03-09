package de.mymiggi.discordbot.server.tenor.gif;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

public class TenorReactionHandler
{
	public void run(ReactionAddEvent reactionAddEvent, String searchQuery, Message commandMessage)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("🔄"))
			{
				reactionAddEvent.getMessage()
					.ifPresent(message -> {
						try
						{
							message.edit(new RandomGifAction().get(searchQuery));
						}
						catch (Exception e)
						{
							String altGif = "https://tenor.com/view/tonton-tonton-sticker-no-nope-gif-13636081";
							message.edit(altGif);
							e.printStackTrace();
						}
					});
			}
			if (reactionAddEvent.getEmoji().equalsEmoji("❌"))
			{
				reactionAddEvent.getMessage()
					.ifPresent(message -> {
						if (message.canYouDelete())
						{
							message.delete();
							commandMessage.delete();
						}
					});
			}
			reactionAddEvent.removeReactionsByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
		}
	}
}
