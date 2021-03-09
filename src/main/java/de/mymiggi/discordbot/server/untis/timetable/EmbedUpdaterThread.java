package de.mymiggi.discordbot.server.untis.timetable;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedUpdaterThread
{
	private static Logger logger = LoggerFactory.getLogger(EmbedUpdaterThread.class.getSimpleName());
	private boolean running = true;
	private EmbedBuilder embed;

	public void run(CompletableFuture<Message> cMessage, LocalDate date)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				work(cMessage, date);
			}
		};
		thread.start();
	}

	private void work(CompletableFuture<Message> cMessage, LocalDate date)
	{
		try
		{
			int counter = 0;
			while (running)
			{
				Thread.sleep(60 * 1000);
				embed = new TimeTableEmbed().build(date);
				cMessage.thenAccept(message -> {
					message.edit(embed).toCompletableFuture();
				});
				counter++;
				if (counter == 300)
				{
					running = false;
				}
			}
			Thread.sleep(5 * 60 * 1000);
			cMessage.thenAccept(message -> {
				message.edit("https://tenor.com/view/shiba-gif-19210829").toCompletableFuture();
				message.removeEmbed().toCompletableFuture();
				message.addReaction("👋");
			});
			Thread.sleep(5 * 60 * 1000);
			cMessage.thenAccept(message -> {
				message.delete();
			});
		}
		catch (InterruptedException e)
		{
			logger.error("Sleep InterruptedException", e);
		}
	}
}
