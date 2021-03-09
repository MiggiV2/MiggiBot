package de.mymiggi.discordbot.server.r6.map.list;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

public class MapListEmbedReactionHandler
{
	public void run(ReactionAddEvent reactionAddEvent, Message mapEmbed)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("✅"))
			{
				if (mapEmbed.canYouDelete())
				{
					mapEmbed.delete();
				}
				reactionAddEvent.removeAllReactionsFromMessage();
				reactionAddEvent.addReactionsToMessage("🦾");
			}
			else
			{
				reactionAddEvent.removeReactionByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
			}
		}
	}
}
