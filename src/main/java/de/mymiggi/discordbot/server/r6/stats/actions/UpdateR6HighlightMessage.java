package de.mymiggi.discordbot.server.r6.stats.actions;

import java.awt.Color;
import java.io.IOException;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.server.r6.stats.embeds.R6HighlightEmbed;
import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;
import de.mymiggi.r6.stats.wrapper.entitys.weekly.highlight.WeeklyHighlightResponse;

public class UpdateR6HighlightMessage extends AbstractUpdateR6MessageAction
{

	@Override
	public void execute(PlayerIDResponse userProfile) throws IOException
	{
		try
		{
			WeeklyHighlightResponse highlight = wrapperManager.getWeeklyHighlight(userProfile.get().getProfileId());
			String[] highlightStr = highlight.getStats().getNarrative();
			message.edit(new R6HighlightEmbed().build(highlightStr, userProfile));
		}
		catch (IOException e)
		{
			if (e.getMessage() != null && e.getMessage().equals("No highlight found!"))
			{
				sendNoHighlight();
			}
			else
			{
				throw e;
			}
		}
	}

	private void sendNoHighlight()
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Oh man! Sorry")
			.setDescription("No highlight this week. Sorry :confused:")
			.setColor(Color.GRAY);
		message.edit(embed);
	}
}
