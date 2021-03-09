package de.mymiggi.discordbot.server.untis.timetable;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.server.untis.timetable.reactions.TimeTableReactionHandler;

public class TimeTableCore
{
	public void run(MessageCreateEvent event)
	{
		LocalDate date = LocalDate.now();
		EmbedBuilder embed = new TimeTableEmbed().build(date);
		TimeTableReactionHandler handler = new TimeTableReactionHandler(date);
		CompletableFuture<Message> cMessage = event.getChannel().sendMessage(embed);
		new EmbedUpdaterThread().run(cMessage, date);
		event.getMessage().addReaction("📅");

		cMessage.thenAccept(message -> {
			message.addReaction("◀️");
			message.addReaction("❌");
			message.addReaction("▶️");
			message.addReactionAddListener(reactionEvent -> handler.run(reactionEvent));
		});
	}
}
