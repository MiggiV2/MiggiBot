package de.mymiggi.discordbot.server.untis.timetable;

import java.awt.Color;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.untis.timetable.reactions.TimeTableReactionHandler;

public class TimeTableCore
{
	public void run(MessageCreateEvent event)
	{
		if (BotMainCore.config.getUntisSchoolName() == null)
		{
			sendNotInConfig(event);
		}
		else
		{
			sendMessage(event);
		}
	}

	private void sendNotInConfig(MessageCreateEvent event)
	{
		BotMainCore.api.getOwner().thenAccept(owner -> {
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but UntisSchoolName is not in my config!")
				.setDescription(String.format("Ask %s to set this in my config!", owner.getName()))
				.setColor(Color.RED);
			event.getChannel().sendMessage(embed);
		});
	}

	private void sendMessage(MessageCreateEvent event)
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
