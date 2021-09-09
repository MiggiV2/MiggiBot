package de.mymiggi.discordbot.drivingschool.lessons.remove;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.util.event.ListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.commands.CommandHandler;
import de.mymiggi.discordbot.server.r6.matchmaker.handler.GetNumberByReaction;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.DrivingLesson;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class DrivingEmbedListener
{
	private Logger logger = LoggerFactory.getLogger(CommandHandler.class.getSimpleName());

	public void run(ReactionAddEvent event, List<DrivingLesson> usersLessons, ListenerManager<ReactionAddListener> reactionListener)
	{
		if (!event.getUser().get().isYourself())
		{
			if (event.getEmoji().isUnicodeEmoji())
			{
				if (event.getEmoji().equalsEmoji("❌"))
				{
					event.getMessage().get().delete();
					sendCancelEmbed(event);
				}
				else
				{
					try
					{
						int number = new GetNumberByReaction().get(event.getEmoji().asUnicodeEmoji().get()) - 1;
						new UniversalHibernateClient().delete(usersLessons.get(number));
						event.getMessage().get().delete();
						sendDoneEmbed(event);
					}
					catch (Exception e)
					{
						logger.error("Failed to delete!", e);
						sendFailedEmbed(event);
					}
				}
			}
			event.removeReactionsByEmojiFromMessage(event.getUser().get(), event.getEmoji());
		}
	}

	private void sendCancelEmbed(ReactionAddEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Canceled!")
			.setColor(Color.GREEN);
		event.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel());
			});
	}

	private void sendDoneEmbed(ReactionAddEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Successfully removed lesson")
			.setColor(Color.GREEN);
		event.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel());
			});
	}

	private void sendFailedEmbed(ReactionAddEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Something went wrong!")
			.setDescription("Please try later again!")
			.setColor(Color.RED);
		event.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel());
			});
	}
}
