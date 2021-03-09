package de.mymiggi.discordbot.server.reaction.role.embed;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class DoneEmbedAction
{
	public void run(MessageCreateEvent event, String channelName)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Successfuly added reaction " + channelName)
			.setFooter("Btw, thx for using my bot! <3 Miggi");
		try
		{
			String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(embedLink, event.getChannel(), 60);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
