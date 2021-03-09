package de.mymiggi.discordbot.commands.constructor;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Command extends SimpleCommandSource
{
	private EmbedBuilder embed = new EmbedBuilder();

	public EmbedBuilder getEmbed()
	{
		return embed;
	}

	public void setEmbed(EmbedBuilder embed)
	{
		this.embed = embed;
	}
}
