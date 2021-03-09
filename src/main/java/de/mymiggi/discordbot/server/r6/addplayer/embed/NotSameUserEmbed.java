package de.mymiggi.discordbot.server.r6.addplayer.embed;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class NotSameUserEmbed
{
	public EmbedBuilder build(User failedUser, User rightUser)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry, but just " + rightUser.getName() + " can add new players!")
			.setDescription("Ask him to add new player!")
			.setColor(Color.ORANGE);
		return embed;
	}
}
