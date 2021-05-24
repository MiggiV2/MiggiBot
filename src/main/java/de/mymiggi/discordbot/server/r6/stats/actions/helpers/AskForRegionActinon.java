package de.mymiggi.discordbot.server.r6.stats.actions.helpers;

import java.awt.Color;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.discordbot.server.r6.stats.handler.R6RegionReactionHandler;
import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class AskForRegionActinon
{
	public void run(Message message, String username, WrapperManager wrapperManager, PlatfromType playerPlatfrom, AbstractUpdateR6MessageAction abstractAction)
	{
		R6RegionReactionHandler handler = new R6RegionReactionHandler();
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Where are you from?")
			.setDescription("[1] Europe (EMEA) \r\n[2] America (NCSA) \r\n[3] Asia (APAC)")
			.setColor(Color.ORANGE);
		message.edit(embed);
		message.addReactionAddListener(reactionAddEvent -> {
			handler.run(reactionAddEvent, username, wrapperManager, null);
			if (handler.getRankedRegion() != null)
			{
				abstractAction.run(username, wrapperManager, handler.getRankedRegion(), playerPlatfrom, message);
			}
		});
	}
}
