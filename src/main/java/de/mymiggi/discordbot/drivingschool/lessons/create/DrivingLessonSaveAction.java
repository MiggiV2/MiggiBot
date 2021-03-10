package de.mymiggi.discordbot.drivingschool.lessons.create;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.DrivingLesson;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.tenor.TenorClientCore;
import de.mymiggi.tenor.util.TenorGif;

public class DrivingLessonSaveAction
{
	private static Logger logger = LoggerFactory.getLogger(DrivingLessonSaveAction.class.getSimpleName());

	public void run(MessageCreateEvent event)
	{
		run(event.getMessageContent(), event.getChannel(), event.getMessageAuthor().getId());
		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 1);
		try
		{
			TenorGif[] gifs = new TenorClientCore().run("driving").getResutls();
			int random = (int)(gifs.length * Math.random());
			event.getChannel()
				.sendMessage(gifs[random].getUrl())
				.thenAccept(message -> {
					MessageCoolDown.del(message.getLink().toString(), event.getChannel(), 6);
				});
		}
		catch (Exception e)
		{
			logger.warn("Cant send gif!", e);
		}
	}

	public void run(String message, TextChannel channel, long userID)
	{
		try
		{
			DrivingLesson lesson = new DrivingLessonBuilder().run(message);
			lesson.setUserID(userID);
			new UniversalHibernateClient().save(lesson);
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Successfully saved!")
				.setColor(Color.GREEN);
			channel
				.sendMessage(embed)
				.thenAccept(embedMessage -> {
					MessageCoolDown.del(embedMessage.getLink().toString(), channel, 6);
				});
		}
		catch (Exception e)
		{
			logger.error("Cant build and save lesson!", e);
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Something went wrong!")
				.setColor(Color.RED);
			if (e.getMessage() != null)
			{
				embed.setDescription(e.getMessage());
			}
			channel
				.sendMessage(embed)
				.thenAccept(embedMessage -> {
					MessageCoolDown.del(embedMessage.getLink().toString(), channel, 6);
				});
		}
	}
}
