package de.mymiggi.discordbot.server.r6.stats;

import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class R6StatsReactionHandler
{
	public void run(ReactionAddEvent reactionAddEvent, String username, WrapperManager wrapperManager, AbstractUpdateR6MessageAction abstractAction)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			PlatfromType playerPlatfrom = null;
			if (reactionAddEvent.getEmoji().equalsEmoji(NumberEmoji.ONE.getEmoji()))
			{
				playerPlatfrom = PlatfromType.UPLAY;
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji(NumberEmoji.TOW.getEmoji()))
			{
				playerPlatfrom = PlatfromType.XBOX;
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji(NumberEmoji.THREE.getEmoji()))
			{
				playerPlatfrom = PlatfromType.PLAYSTATION;
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji(Emojis.NO_ENTRY_SIGN.getEmoji()))
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
			if (playerPlatfrom != null)
			{
				try
				{
					reactionAddEvent.removeAllReactionsFromMessage().get();
				}
				catch (InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
				}
				abstractAction.run(username, wrapperManager, playerPlatfrom, reactionAddEvent.getMessage().get());
			}
			else
			{
				reactionAddEvent.removeReactionsByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
			}
		}
	}
}
