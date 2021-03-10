package de.mymiggi.discordbot.drivingschool.lessons.reminder;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.DrivingLesson;
import de.mymiggi.discordbot.tools.util.TimeCalculator;

public class ReminderThread
{
	private List<DrivingLesson> lessoList = new ArrayList<DrivingLesson>();
	private static Logger logger = LoggerFactory.getLogger(ReminderThread.class.getSimpleName());

	public void run()
	{
		syncList();
		logger.info("Started driving reminder thread!");
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				boolean running = true;
				try
				{
					new TimeCalculator().sleepTill(19);
				}
				catch (InterruptedException e)
				{
					logger.warn("Sleep failed!", e);
				}
				while (running)
				{
					try
					{
						work();
						Thread.sleep(24 * 60 * 60 * 1000);
					}
					catch (InterruptedException e)
					{
						logger.warn("Sleep failed! Stopping thread!", e);
						running = false;
					}
				}
			}
		};
		thread.start();
	}

	public void work()
	{
		LocalDate tomorrow = LocalDate.now().plusDays(1);
		List<DrivingLesson> lessosToRemove = new ArrayList<DrivingLesson>();
		for (DrivingLesson temp : lessoList)
		{
			if (temp.getDate().equals(tomorrow))
			{
				BotMainCore.api.getUserById(temp.getUserID()).thenAccept(user -> {
					EmbedBuilder embed = new EmbedBuilder()
						.setTitle("Driving lesson tomorrow!")
						.setDescription(String.format("%s at %s", temp.getDate().getDayOfWeek(), temp.getTime()))
						.setThumbnail("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWOE1sDUX4iZ-1MJrYRxcdkJUF2AyigTjE8Q&usqp=CAU")
						.setColor(Color.WHITE);
					user.sendMessage(embed);
					lessosToRemove.add(temp);
				});
			}
			else
			{
				logger.info("Not same! " + temp.getDate() + "!=" + tomorrow);
			}
		}
		if (!lessosToRemove.isEmpty())
		{
			new UniversalHibernateClient().deleteList(lessosToRemove);
		}
	}

	private void syncList()
	{
		this.lessoList = new UniversalHibernateClient().getList(DrivingLesson.class);
	}
}
