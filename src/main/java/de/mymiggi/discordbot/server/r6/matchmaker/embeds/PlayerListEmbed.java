package de.mymiggi.discordbot.server.r6.matchmaker.embeds;

import java.awt.Color;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class PlayerListEmbed
{
	private String userList = "";
	private int counter = 0;

	public EmbedBuilder build(Map<User, Boolean> vcUserIngoreMap, int currentPage)
	{
		EmbedBuilder embed = new EmbedBuilder();
		vcUserIngoreMap.forEach((user, shouldIgnore) -> {
			counter++;
			if (isCurrentPage(currentPage))
			{
				if (shouldIgnore)
				{
					userList += String.format("[%s] ~~%s~~ \r\n", (counter), user.getName());
				}
				else
				{
					userList += String.format("[%s] %s \r\n", (counter), user.getName());
				}
			}
		});
		embed.setTitle("Unselect user")
			.setColor(Color.ORANGE)
			.addField("Total players " + vcUserIngoreMap.size(), userList)
			.setFooter("Page " + (currentPage + 1));
		return embed;
	}

	private boolean isCurrentPage(int currentPage)
	{
		int startPostion = (currentPage * 7) + 1;
		int endPostion = (currentPage + 1) * 7;
		return counter >= startPostion && counter <= endPostion;
	}
}
