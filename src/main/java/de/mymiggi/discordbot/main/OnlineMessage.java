package de.mymiggi.discordbot.main;

import java.awt.Color;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

public class OnlineMessage
{
	public static void send(User user)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("Online")
				.setDescription("Thank you master")
				.setColor(Color.ORANGE));
		m.send(user);
	}
}
