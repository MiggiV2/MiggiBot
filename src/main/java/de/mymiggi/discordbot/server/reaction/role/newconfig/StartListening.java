package de.mymiggi.discordbot.server.reaction.role.newconfig;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.reaction.role.embed.BadInputEmbedAction;
import de.mymiggi.discordbot.server.reaction.role.embed.NotAdminEmbedAction;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.Permissions;

public class StartListening
{
	private Logger logger = LoggerFactory.getLogger("NewWelcomerCreater");

	public void run(MessageCreateEvent event, String[] context)
	{
		if (!Permissions.isAdmin(event))
		{
			new NotAdminEmbedAction().run(event);
			logger.info(event.getMessageAuthor().getName() + " try to use command, but he is not an admin!");
			return;
		}
		if (context.length != 2)
		{
			new BadInputEmbedAction().run(event);
			return;
		}

		try
		{
			EmbedBuilder embed = new EmbedBuilder().setTitle("Now, pls react to your message");
			String embedLink = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 2);
			Message message = BotMainCore.api.getMessageById(context[1], event.getChannel()).get();
			new ListenForEmoji().run(event, message, embedLink);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
