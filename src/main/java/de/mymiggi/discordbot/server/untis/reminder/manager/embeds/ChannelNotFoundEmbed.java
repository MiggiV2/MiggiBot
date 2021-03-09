package de.mymiggi.discordbot.server.untis.reminder.manager.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;

public class ChannelNotFoundEmbed
{
	public EmbedBuilder build()
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry Channel not found!")
			.setDescription("Maybe a typing error?" + System.lineSeparator() + BotMainCore.prefix + "add ServerID ChannelID");
		return embed;
	}
}
