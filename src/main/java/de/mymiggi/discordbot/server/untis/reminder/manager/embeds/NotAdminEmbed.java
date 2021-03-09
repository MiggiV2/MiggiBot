package de.mymiggi.discordbot.server.untis.reminder.manager.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class NotAdminEmbed
{
	public EmbedBuilder build(MessageCreateEvent event)
	{
		return new EmbedBuilder()
			.setTitle("You are not an admin " + event.getMessageAuthor().getName() + "!")
			.setFooter("Maybe u can aske the owner?")
			.setColor(Color.RED);
	}
}
