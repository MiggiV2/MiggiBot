package de.mymiggi.discordbot.server.r6.stats.actions;

import java.io.IOException;

import de.mymiggi.discordbot.server.r6.stats.embeds.R6StatsEmbed;
import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;
import de.mymiggi.r6.stats.wrapper.entitys.stats.SmartStatsResponse;

public class UpdateR6StatsMessage extends AbstractUpdateR6MessageAction
{
	@Override
	public void execute(PlayerIDResponse userProfile) throws IOException
	{
		SmartStatsResponse statsResponse = wrapperManager.getStats(playerPlatfrom, userProfile.get().getProfileId());
		message.edit(new R6StatsEmbed().build(statsResponse, userProfile.get()));
	}
}
