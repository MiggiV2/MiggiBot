package de.mymiggi.discordbot.server.untis.reminder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.untis.reminder.manager.ReminderChannelLoader;
import de.mymiggi.discordbot.tools.database.util.UntisReminderChannelNew;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.webuntis.WebUntisClient;
import de.mymiggi.webuntis.actions.NextLessonForToday;
import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class TimeTableReminderCore
{
	private static Logger logger = LoggerFactory.getLogger(TimeTableReminderCore.class.getSimpleName());
	private List<UntisReminderChannelNew> channels = new ArrayList<UntisReminderChannelNew>();
	private Optional<WebUntisResponse> responseOptional = Optional.empty();
	private String schoolName;

	public TimeTableReminderCore()
	{
		BotMainCore.config.getUntisSchoolName().ifPresent(schoolName -> {
			this.schoolName = schoolName;
			responseOptional = Optional.of(new WebUntisClient().getResponse(schoolName));
		});
	}

	public void run()
	{
		responseOptional.ifPresent(response -> {
			syncChannels();
			logger.info("Loaded channels " + channels.size());
			new UpaterThread().run(response, schoolName);
			new ReminderThread().run(response, channels);
		});
		if (!BotMainCore.config.getUntisSchoolName().isPresent())
		{
			logger.warn("UntisSchoolName is not in config!");
		}
	}

	public void nextSubjectEmbed(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		responseOptional.ifPresent(response -> {
			interaction.respondLater();
			EmbedBuilder embed = new EmbedBuilder();
			Elements nextLesson = new NextLessonForToday().getNextSubjectByTime(response);
			if (nextLesson == null)
			{
				embed.setTitle("Heute keine Schule mehr!");
			}
			else
			{
				embed.setTitle("Nächste Stunde ist " + nextLesson.getLongName());
			}
			embed.setColor(Color.ORANGE);
			interaction.createFollowupMessageBuilder()
				.addEmbed(embed)
				.send()
				.thenAccept(message -> {
					interaction.getChannel().ifPresent(channel -> {
						MessageCoolDown.del(message.getLink().toString(), channel, 30);
					});
				});
		});
		if (!responseOptional.isPresent())
		{
			BotMainCore.api.getOwner().thenAccept(owner -> {
				EmbedBuilder embed = new EmbedBuilder()
					.setTitle("Sorry, but UntisSchoolName is not in my config!")
					.setDescription(String.format("Ask %s to set this in my config!", owner.getName()))
					.setColor(Color.RED);
				interaction.createImmediateResponder()
					.addEmbed(embed)
					.respond();
			});
		}
	}

	public void nextSubjectEmbed(MessageCreateEvent event)
	{
		responseOptional.ifPresent(response -> {
			EmbedBuilder embed = new EmbedBuilder();
			Elements nextLesson = new NextLessonForToday().getNextSubjectByTime(response);
			if (nextLesson == null)
			{
				embed.setTitle("Heute keine Schule mehr!");
				event.getMessage().addReaction("👌");
			}
			else
			{
				embed.setTitle("Nächste Stunde ist " + nextLesson.getLongName());
				event.getMessage().addReaction("👍");
			}
			embed.setColor(Color.ORANGE);
			event.getChannel().sendMessage(embed).thenAccept(message -> {
				MessageCoolDown.del(message.getLink().toString(), event.getChannel(), 30);
			});
		});
		if (!responseOptional.isPresent())
		{
			BotMainCore.api.getOwner().thenAccept(owner -> {
				EmbedBuilder embed = new EmbedBuilder()
					.setTitle("Sorry, but UntisSchoolName is not in my config!")
					.setDescription(String.format("Ask %s to set this in my config!", owner.getName()))
					.setColor(Color.RED);
				event.getChannel().sendMessage(embed);
			});
		}
	}

	public void startNewThread(UntisReminderChannelNew newChannel)
	{
		responseOptional.ifPresent(response -> {
			List<UntisReminderChannelNew> channels = new ArrayList<UntisReminderChannelNew>();
			channels.add(newChannel);
			new ReminderThread().run(response, channels);
		});
	}

	public Optional<WebUntisResponse> getResponse()
	{
		return this.responseOptional;
	}

	public void syncChannels()
	{
		this.channels = new ReminderChannelLoader().run();
	}
}
