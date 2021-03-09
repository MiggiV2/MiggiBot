package de.mymiggi.discordbot.server.r6.addplayer;

import java.util.List;

import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.r6.addplayer.embed.ClosedEmbed;
import de.mymiggi.discordbot.server.r6.addplayer.embed.SuccessfullySavedEmbed;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.RainbowSixPlayer;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ReactionHandler
{
	private static Logger logger = LoggerFactory.getLogger(ReactionHandler.class.getSimpleName());

	public void run(ReactionAddEvent reactionAddEvent, ListenerManager<MessageCreateListener> messageListern, List<RainbowSixPlayer> savingList)
	{
		if (reactionAddEvent.getEmoji().equalsEmoji("✅") && !reactionAddEvent.getUser().get().isYourself())
		{
			messageListern.remove();
			reactionAddEvent.getChannel().sendMessage(new ClosedEmbed().build()).thenAccept(message -> {
				new UniversalHibernateClient().saveList(savingList);
				message.edit(new SuccessfullySavedEmbed().build());
				MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 12);
			});
			String messageURL = reactionAddEvent.getMessage().get().getLink().toString();
			MessageCoolDown.del(messageURL, reactionAddEvent.getChannel(), 1);
			logger.info("Closed listener for server " + reactionAddEvent.getServer().get().getName());
		}
	}
}
