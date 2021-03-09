package de.mymiggi.discordbot.server.untis.reminder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private WebUntisResponse response = new WebUntisClient().getResponse();
	private static Logger logger = LoggerFactory.getLogger(TimeTableReminderCore.class.getSimpleName());

	public void run()
	{
		syncChannels();
		logger.info("Loaded channels " + channels.size());
		new UpaterThread().run(response);
		new ReminderThread().run(response, channels);
	}

	public void nextSubjectEmbed(MessageCreateEvent event)
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
