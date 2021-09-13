package de.mymiggi.discordbot.server.untis.timetable;

import java.time.LocalDate;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedUpdaterThread
{
	private static Logger logger = LoggerFactory.getLogger(EmbedUpdaterThread.class.getSimpleName());
	private boolean running = true;
	private EmbedBuilder embed;

	public void run(Message message, LocalDate date)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				work(message, date);
			}
		};
		thread.start();
	}

	private void work(Message message, LocalDate date)
	{
		try
		{
			int counter = 0;
			while (running)
			{
				Thread.sleep(60 * 1000);
				embed = new TimeTableEmbed().build(date);
				message.edit(embed).toCompletableFuture();
				counter++;
				if (counter == 300)
				{
					running = false;
				}
			}
			Thread.sleep(5 * 60 * 1000);
			message.edit("https://tenor.com/view/shiba-gif-19210829").toCompletableFuture();
			message.removeEmbed().toCompletableFuture();
			message.addReaction("👋");
			Thread.sleep(5 * 60 * 1000);
			message.delete();
		}
		catch (InterruptedException e)
		{
			logger.error("Sleep InterruptedException", e);
		}
	}
}
