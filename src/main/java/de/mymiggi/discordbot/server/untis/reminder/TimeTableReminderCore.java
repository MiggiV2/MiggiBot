package de.mymiggi.discordbot.server.untis.reminder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	private List<UntisReminderChannelNew> channels = new ArrayList<UntisReminderChannelNew>();
	private WebUntisResponse response;
	private static Logger logger = LoggerFactory.getLogger(TimeTableReminderCore.class.getSimpleName());

	public TimeTableReminderCore()
	{
		if (BotMainCore.config.getUntisSchoolName() != null)
		{
			response = new WebUntisClient().getResponse();
		}
	}

	public void run()
	{
		if (BotMainCore.config.getUntisSchoolName() != null)
		{
			syncChannels();
			logger.info("Loaded channels " + channels.size());
			new UpaterThread().run(response);
			new ReminderThread().run(response, channels);
		}
		else
		{
			logger.warn("UntisSchoolName is not in config!");
		}
	}

	public void nextSubjectEmbed(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		if (BotMainCore.config.getUntisSchoolName() != null)
		{
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
		}
		else
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
		if (BotMainCore.config.getUntisSchoolName() != null)
		{
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
		}
		else
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
		List<UntisReminderChannelNew> channels = new ArrayList<UntisReminderChannelNew>();
		channels.add(newChannel);
		new ReminderThread().run(response, channels);
	}

	public WebUntisResponse getResponse()
	{
		return this.response;
	}

	public void syncChannels()
	{
		this.channels = new ReminderChannelLoader().run();
	}
}
