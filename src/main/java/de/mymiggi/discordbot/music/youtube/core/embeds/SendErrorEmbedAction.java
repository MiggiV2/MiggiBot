package de.mymiggi.discordbot.music.youtube.core.embeds;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.channel.TextChannelEvent;

public class SendErrorEmbedAction
{
	public void run(TextChannelEvent event, String message)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(message);
		event.getChannel().asTextChannel().get().sendMessage(embed);
	}

	public void run(TextChannel channel, String message)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(message);
		channel.sendMessage(embed);
	}
}
