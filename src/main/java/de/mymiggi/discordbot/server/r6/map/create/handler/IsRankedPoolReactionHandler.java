package de.mymiggi.discordbot.server.r6.map.create.handler;

import java.awt.Color;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.r6.map.create.NotAllowedAction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class IsRankedPoolReactionHandler
{
	private static Logger logger = LoggerFactory.getLogger(IsRankedPoolReactionHandler.class.getSimpleName());

	public void run(ReactionAddEvent reactionAddEvent, R6Map map, Message embedMessage)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getUser().get().isBotOwner())
			{
				if (reactionAddEvent.getEmoji().equalsEmoji("👍"))
				{
					updateMap(reactionAddEvent, map, true);
					updateEmbed(map, embedMessage);
				}
				else if (reactionAddEvent.getEmoji().equalsEmoji("👎"))
				{
					updateMap(reactionAddEvent, map, false);
					updateEmbed(map, embedMessage);
				}
				else
				{
					User eventUser = reactionAddEvent.getUser().get();
					Emoji eventEmoji = reactionAddEvent.getEmoji();
					reactionAddEvent.getMessage()
						.get()
						.removeReactionsByEmoji(eventUser, eventEmoji);
				}
			}
			else
			{
				new NotAllowedAction().run(reactionAddEvent);
			}
		}
	}

	private void updateMap(ReactionAddEvent reactionAddEvent, R6Map map, boolean isInRankedPool)
	{
		map.setRankedPool(isInRankedPool);
		new UniversalHibernateClient().save(map);
		reactionAddEvent.getMessage()
			.get()
			.removeAllReactions();
		sendSavedEmbed(reactionAddEvent);
		logger.info("Saved new map " + map.getName() + "!");
	}

	private void updateEmbed(R6Map map, Message embedMessage)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle(map.getName())
			.setImage(map.getImageURL())
			.setDescription("Is in ranked pool: " + map.isRankedPool())
			.setColor(Color.GREEN);
		embedMessage.edit(embed);
	}

	private void sendSavedEmbed(ReactionAddEvent reactionAddEvent)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Successfully saved!")
			.setColor(Color.GREEN);
		reactionAddEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(message -> {
				MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 6);
			});
	}
}
