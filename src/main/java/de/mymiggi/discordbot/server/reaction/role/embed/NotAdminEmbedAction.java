package de.mymiggi.discordbot.server.reaction.role.embed;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class NotAdminEmbedAction
{
	public void run(MessageCreateEvent event)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("You are not an admin " + event.getMessageAuthor().getName() + "!")
				.setFooter("Maybe u can aske the owner?")
				.setColor(Color.RED));
		try
		{
			String lastBotMessageLink = m.send(event.getChannel()).get().getLink().toString();
			MessageCoolDown.del(lastBotMessageLink, event.getChannel(), 30);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
