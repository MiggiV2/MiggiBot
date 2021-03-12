package de.mymiggi.discordbot.server.r6.map.update.actions;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.server.r6.map.update.embeds.DeleteConfirmEmbed;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class DeleteAction
{
	public void run(TextChannel channel, R6Map map)
	{
		channel
			.sendMessage(new DeleteConfirmEmbed().build())
			.thenAccept(embedMessage -> {
				embedMessage.addReaction("👍");
				embedMessage.addReaction("👎");
				embedMessage.addReactionAddListener(reactionAddEvent -> {
					handleReaction(reactionAddEvent, map);
				});
			});
	}

	private void handleReaction(ReactionAddEvent reactionAddEvent, R6Map map)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("👍"))
			{
				deleteMap(reactionAddEvent, map);
			}
			if (reactionAddEvent.getEmoji().equalsEmoji("👎"))
			{
				cancelAction(reactionAddEvent);
			}
			reactionAddEvent.removeReactionByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
		}
	}

	private void deleteMap(ReactionAddEvent reactionAddEvent, R6Map map)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Deleted map!")
			.setColor(Color.GREEN);
		reactionAddEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), reactionAddEvent.getChannel(), 8);
			});
		new UniversalHibernateClient().delete(map);
		new CloseEmbedAction().run(reactionAddEvent.getMessage().get());
	}

	private void cancelAction(ReactionAddEvent reactionAddEvent)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Canceled!")
			.setColor(Color.GREEN);
		reactionAddEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), reactionAddEvent.getChannel(), 8);
			});
		new CloseEmbedAction().run(reactionAddEvent.getMessage().get());
	}
}
