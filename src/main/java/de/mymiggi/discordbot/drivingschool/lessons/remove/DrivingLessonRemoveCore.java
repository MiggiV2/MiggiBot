package de.mymiggi.discordbot.drivingschool.lessons.remove;

import java.util.List;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.drivingschool.lessons.remove.actions.GetAllLessonsForUser;
import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.DrivingLesson;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class DrivingLessonRemoveCore
{
	private ListenerManager<ReactionAddListener> reactionListener;

	public void run(MessageCreateEvent event)
	{
		User user = event.getMessageAuthor().asUser().get();
		List<DrivingLesson> allLessons = new UniversalHibernateClient().getList(DrivingLesson.class);
		List<DrivingLesson> usersLessons = new GetAllLessonsForUser().get(user, allLessons);
		if (usersLessons.isEmpty())
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Can't find driving lessons for your!")
				.setDescription("You can add lessons with " + BotMainCore.prefix + "driving");
			event.getMessage()
				.addReaction("🚔");
			event.getChannel()
				.sendMessage(embed)
				.thenAccept(embedMessage -> {
					MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 8);
				});
		}
		else
		{
			event.getMessage()
				.addReaction("🚘");
			event.getChannel()
				.sendMessage(new DrivingLessonEmbed().build(usersLessons))
				.thenAccept(embedMessage -> {
					addReactionsAndListener(embedMessage, usersLessons);
				});
		}
	}

	private void addReactionsAndListener(Message embedMessage, List<DrivingLesson> usersLessons)
	{
		embedMessage.addReaction("❌");
		for (int i = 0; i < usersLessons.size(); i++)
		{
			embedMessage.addReaction(NumberEmoji.values()[i].getEmoji());
		}
		reactionListener = embedMessage.addReactionAddListener(reactionEvent -> {
			new DrivingEmbedListener().run(reactionEvent, usersLessons, reactionListener);
		});
	}
}
