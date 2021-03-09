package de.mymiggi.discordbot.music.youtube.actions.util;

import java.util.List;

import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;

public class GetActionByEmoji
{
	private Logger logger = LoggerFactory.getLogger("GetActionByEmoji");

	public AbstractYTAction get(String emojiStr, ReactionAddEvent event, List<AbstractYTAction> actions) throws Exception
	{
		for (AbstractYTAction temp : actions)
		{
			if (temp.matchesEmoji(emojiStr))
			{
				temp.setReactionEvent(event);
				return temp;
			}
		}

		event.getEmoji().asUnicodeEmoji().ifPresent(emoji -> {
			event.getMessage().get().removeReactionByEmoji(emoji);
		});

		logger.info("Removed not listed emoji!");
		throw new Exception("Emoji not in Emojis.java");
	}
}
