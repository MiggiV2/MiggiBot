package de.mymiggi.discordbot.server.untis.reminder;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedUpdaterThread
{
	private static Logger logger = LoggerFactory.getLogger(EmbedUpdaterThread.class.getSimpleName());

	public void run(String lesson, List<Message> embedList)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				work(lesson, embedList);
			}
		};
		thread.start();
	}

	private void work(String lesson, List<Message> embedList)
	{
		EmbedBuilder embed = new EmbedBuilder();
		try
		{
			for (int i = 1; i < 6; i++)
			{
				Thread.sleep(60 * 1000);

				embed.setTitle(lesson + " in " + (5 - i) + " minuts!")
					.setColor(Color.ORANGE);

				embedList.forEach(message -> {
					try
					{
						if (message.canYouAddNewReactions())
						{
							message.edit(embed).get();
						}
					}
					catch (InterruptedException | ExecutionException e)
					{
						logger.error("Edit InterruptedException", e);
						embedList.remove(message);
					}
				});
			}
			Thread.sleep(2 * 1000);
			embed.setTitle("Have fun in " + lesson)
				.setColor(Color.ORANGE);

			embedList.forEach(message -> {
				try
				{
					if (message.canYouAddNewReactions())
					{
						message.edit(embed).get();
					}
				}
				catch (InterruptedException | ExecutionException e)
				{
					logger.error("Edit InterruptedException", e);
					embedList.remove(message);
				}
			});
			Thread.sleep(5 * 60 * 1000);
			embedList.forEach(message -> {
				try
				{
					if (message.canYouDelete())
					{
						message.delete().get();
					}
				}
				catch (InterruptedException | ExecutionException e)
				{
					logger.error("Delete InterruptedException", e);
					embedList.remove(message);
				}
			});
		}
		catch (InterruptedException e)
		{
			logger.error("Sleep InterruptedException", e);
		}
	}
}
