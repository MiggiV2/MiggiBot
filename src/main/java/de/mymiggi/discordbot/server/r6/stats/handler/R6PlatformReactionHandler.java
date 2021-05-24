package de.mymiggi.discordbot.server.r6.stats.handler;

import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class R6PlatformReactionHandler extends AbstracktR6StatsReactionHandler
{
	private PlatfromType playerPlatfrom;
	private boolean shouldRemoveAllEmojis = true;

	@Override
	protected boolean execute(ReactionAddEvent reactionAddEvent, String username, WrapperManager wrapperManager)
	{
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
		if (playerPlatfrom != null && shouldRemoveAllEmojis)
		{
			try
			{
				reactionAddEvent.removeAllReactionsFromMessage().get();
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	public PlatfromType getPlayerPlatfrom()
	{
		return playerPlatfrom;
	}

	public boolean isShouldRemoveAllEmojis()
	{
		return shouldRemoveAllEmojis;
	}

	public void setShouldRemoveAllEmojis(boolean shouldRemoveAllEmojis)
	{
		this.shouldRemoveAllEmojis = shouldRemoveAllEmojis;
	}
}
