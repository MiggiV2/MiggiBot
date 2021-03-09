package de.mymiggi.discordbot.server.r6.map.update.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class NotAuthorisedToUpdateR6MapEmbed
{
	public EmbedBuilder build(User user)
	{
		return new EmbedBuilder()
			.setTitle(String.format("%s, you are not my ower! So u can't update maps!", user.getName()))
			.setColor(Color.RED);
	}
}
