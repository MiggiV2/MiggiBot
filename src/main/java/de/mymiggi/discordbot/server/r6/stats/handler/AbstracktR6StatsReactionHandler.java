package de.mymiggi.discordbot.server.r6.stats.handler;

import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public abstract class AbstracktR6StatsReactionHandler
{
	public void run(ReactionAddEvent reactionAddEvent, String username, WrapperManager wrapperManager, AbstractUpdateR6MessageAction abstractAction)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			boolean goNext = execute(reactionAddEvent, username, wrapperManager);
			if (!goNext)
			{
				reactionAddEvent.removeReactionsByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
			}
			if (reactionAddEvent.getEmoji().equalsEmoji(Emojis.NO_ENTRY_SIGN.getEmoji()))
			{
				reactionAddEvent.getMessage().ifPresent(message -> {
					try
					{
						message.delete().get();
					}
					catch (InterruptedException | ExecutionException e)
					{
						e.printStackTrace();
					}
				});
			}
		}
	}

	/**
	 * @param reactionAddEvent
	 * @param username
	 * @param wrapperManager
	 * @param abstractAction
	 * @return if all emojis should be removed
	 */
	protected abstract boolean execute(ReactionAddEvent reactionAddEvent, String username, WrapperManager wrapperManager);
}
