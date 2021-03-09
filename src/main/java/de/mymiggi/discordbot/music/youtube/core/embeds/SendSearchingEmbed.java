package de.mymiggi.discordbot.music.youtube.core.embeds;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class SendSearchingEmbed
{
	public String run(TextChannel textChannel)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Searching your song ...");
		try
		{
			return textChannel.sendMessage(embed).get().getLink().toString();
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
