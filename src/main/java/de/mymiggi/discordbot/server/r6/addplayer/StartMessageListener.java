package de.mymiggi.discordbot.server.r6.addplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.r6.addplayer.embed.StartListeningEmbed;
import de.mymiggi.discordbot.server.r6.addplayer.embed.YouAreNotAnAdminEmbed;
import de.mymiggi.discordbot.tools.database.util.RainbowSixPlayer;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class StartMessageListener
{
	private static Logger logger = LoggerFactory.getLogger(StartMessageListener.class.getSimpleName());
	private List<RainbowSixPlayer> savingList = new ArrayList<RainbowSixPlayer>();

	public void run(MessageCreateEvent event)
	{
		if (!event.getMessageAuthor().isServerAdmin())
		{
			event.getChannel().sendMessage(new YouAreNotAnAdminEmbed().build());
		}
		else
		{
			logger.info("Start listening for server " + event.getServer().get().getName());
			ListenerManager<MessageCreateListener> messageListern = startMessageListenerManager(event);
			event.addReactionsToMessage("✅");
			event.getChannel()
				.sendMessage(new StartListeningEmbed().build())
				.thenAccept(message -> MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 15));
			event.getMessage().addReactionAddListener(embedMessage -> new ReactionHandler().run(embedMessage, messageListern, savingList));
		}
	}

	private ListenerManager<MessageCreateListener> startMessageListenerManager(MessageCreateEvent event)
	{
		ListenerManager<MessageCreateListener> listenerManager = event.getChannel().addMessageCreateListener(newMessage -> {
			if (!newMessage.getMessageAuthor().isYourself())
			{
				String[] context = newMessage.getMessageContent().split(" ");
				User authorized = event.getMessageAuthor().asUser().get();
				new AddNewPlayerAction().run(newMessage, context, authorized, savingList);
			}
		});
		listenerManager.removeAfter(15, TimeUnit.MINUTES);
		return listenerManager;
	}
}
