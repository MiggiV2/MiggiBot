package de.mymiggi.discordbot.server.r6.stats.actions;

import java.io.IOException;

import de.mymiggi.discordbot.server.r6.stats.embeds.R6RankedEmbed;
import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;
import de.mymiggi.r6.stats.wrapper.entitys.rank.RankedResponse;

public class UpdateR6RankedMessage extends AbstractUpdateR6MessageAction
{
	public UpdateR6RankedMessage()
	{
		this.needRankedRegion = true;
	}

	@Override
	public void execute(PlayerIDResponse userProfile) throws IOException
	{
		RankedResponse rankedResposne = wrapperManager.getRankedStats(rankedRegion, userProfile.get().getProfileId());
		message.edit(new R6RankedEmbed().build(rankedResposne.getStats(), userProfile.get()));
	}
}
