package de.mymiggi.discordbot.server.untis.timetable;

import java.awt.Color;
import java.time.LocalDate;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.untis.timetable.reactions.TimeTableReactionHandler;

public class TimeTableCore
{
	public void run(MessageCreateEvent event)
	{
		if (BotMainCore.config.getUntisSchoolName().isEmpty())
		{
			sendNotInConfig(event);
		}
		else
		{
			sendMessage(event);
		}
	}

	public void run(SlashCommandCreateEvent event)
	{
		if (BotMainCore.config.getUntisSchoolName().isEmpty())
		{
			sendNotInConfig(event);
		}
		else
		{
			sendMessage(event);
		}
	}

	private void sendNotInConfig(SlashCommandCreateEvent event)
	{
		BotMainCore.api.getOwner().thenAccept(owner -> {
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but this command is disabled!")
				.setDescription(String.format("Ask %s to set UntisSchoolName in my config!", owner.getName()))
				.setColor(Color.RED);
			event.getSlashCommandInteraction()
				.createImmediateResponder()
				.addEmbed(embed)
				.respond();
		});
	}

	private void sendNotInConfig(MessageCreateEvent event)
	{
		BotMainCore.api.getOwner().thenAccept(owner -> {
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but this command is disabled!")
				.setDescription(String.format("Ask %s to set UntisSchoolName in my config!", owner.getName()))
				.setColor(Color.RED);
			event.getChannel().sendMessage(embed);
		});
	}

	private void sendMessage(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		LocalDate date = LocalDate.now();
		EmbedBuilder embed = new TimeTableEmbed().build(date);
		TimeTableReactionHandler handler = new TimeTableReactionHandler(date);
		interaction.createFollowupMessageBuilder()
			.addEmbed(embed)
			.send()
			.thenAccept(message -> {
				new EmbedUpdaterThread().run(message, date);
				message.addReactions("◀️", "❌", "▶️");
				message.addReactionAddListener(reactionEvent -> handler.run(reactionEvent));
			});
	}

	private void sendMessage(MessageCreateEvent event)
	{
		LocalDate date = LocalDate.now();
		EmbedBuilder embed = new TimeTableEmbed().build(date);
		TimeTableReactionHandler handler = new TimeTableReactionHandler(date);
		event.getMessage().addReaction("📅");
		event.getChannel()
			.sendMessage(embed)
			.thenAccept(message -> {
				new EmbedUpdaterThread().run(message, date);
				message.addReactions("◀️", "❌", "▶️");
				message.addReactionAddListener(reactionEvent -> handler.run(reactionEvent));
			});
	}
}
