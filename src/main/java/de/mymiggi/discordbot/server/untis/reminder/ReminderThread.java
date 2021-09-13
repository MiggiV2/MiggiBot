package de.mymiggi.discordbot.server.untis.reminder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.util.UntisReminderChannelNew;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.webuntis.actions.CurrentLessonAction;
import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class ReminderThread
{
	private Elements currentLesson;
	private static Logger logger = LoggerFactory.getLogger(ReminderThread.class.getSimpleName());

	public void run(WebUntisResponse response, List<UntisReminderChannelNew> channels)
	{
		Thread thread = new Thread()
		{
			public void run()
			{
				work(response, channels);
			}
		};
		thread.start();
	}

	public void work(WebUntisResponse response, List<UntisReminderChannelNew> untisReminderChannelList)
	{
		boolean running = true;
		LessonPeriod[] timeTabel = response.getLessons();
		this.currentLesson = new CurrentLessonAction().get(timeTabel, response);
		while (running)
		{
			Elements currentLesson = new CurrentLessonAction().get(timeTabel, response);
			if (currentLesson != null && currentLesson != this.currentLesson && !shouldIgnore(currentLesson.getLongName()))
			{
				logger.info("Next subject in 5 " + currentLesson.getLongName() + " channles " + untisReminderChannelList.size());
				this.currentLesson = currentLesson;
				List<Message> embedList = new ArrayList<Message>();
				untisReminderChannelList.forEach(temp -> {
					sendMessageIfChannelPresent(temp, embedList);
				});
				new EmbedUpdaterThread().run(currentLesson.getLongName(), embedList);
			}
			try
			{
				Thread.sleep(4000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void sendMessageIfChannelPresent(UntisReminderChannelNew temp, List<Message> embedList)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle(currentLesson.getLongName() + " in 5 minuts!")
			.setColor(Color.ORANGE);
		BotMainCore.api.getTextChannelById(temp.getChannelID()).ifPresent(channel -> {
			BotMainCore.api.getRoleById(temp.getRoleID()).ifPresent(role -> {
				channel.sendMessage(role.getMentionTag() + " in 5 minuts " + currentLesson.getLongName()).thenAccept(message -> {
					MessageCoolDown.del(message.getLink().toString(), channel, 1);
				});
			});
			channel.sendMessage(embed).thenAccept(message -> {
				embedList.add(message);
			});
		});
	}

	private boolean shouldIgnore(String subjectName)
	{
		List<String> blackList = new ArrayList<String>();
		blackList.add("Praktikum FOS");
		for (String temp : blackList)
		{
			if (temp.equals(subjectName))
			{
				return true;
			}
		}
		return false;
	}
}