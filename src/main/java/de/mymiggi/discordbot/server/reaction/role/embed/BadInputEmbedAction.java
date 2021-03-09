package de.mymiggi.discordbot.server.reaction.role.embed;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class BadInputEmbedAction
{
	public void run(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Wrong syntax!")
			.setDescription(String.format("%sreactionRole MessageID", BotMainCore.prefix))
			.addField("Example:", BotMainCore.prefix + "reactionRole 807626412319834113");

		try
		{
			String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(embedLink, event.getChannel(), 120);

		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
