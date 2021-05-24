package de.mymiggi.discordbot.server.r6.stats.handler;

import java.util.concurrent.ExecutionException;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.r6.stats.wrapper.RankedRegions;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class R6RegionReactionHandler extends AbstracktR6StatsReactionHandler
{
	private RankedRegions rankedRegion;

	public RankedRegions getRankedRegion()
	{
		return rankedRegion;
	}

	@Override
	protected boolean execute(ReactionAddEvent reactionAddEvent, String username, WrapperManager wrapperManager)
	{
		if (reactionAddEvent.getEmoji().equalsEmoji(NumberEmoji.ONE.getEmoji()))
		{
			rankedRegion = RankedRegions.EMEA;
		}
		else if (reactionAddEvent.getEmoji().equalsEmoji(NumberEmoji.TOW.getEmoji()))
		{
			rankedRegion = RankedRegions.NCSA;
		}
		else if (reactionAddEvent.getEmoji().equalsEmoji(NumberEmoji.THREE.getEmoji()))
		{
			rankedRegion = RankedRegions.APAC;
		}
		if (rankedRegion != null)
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
}
