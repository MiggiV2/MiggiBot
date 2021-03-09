package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PowerOff
{
	private Logger logger = LoggerFactory.getLogger("PowerOff");

	public void run(MessageCreateEvent event, DiscordApi api)
	{
		if (event.getMessageAuthor().getId() == 309696934174785556L)
		{
			EmbedBuilder shuttingDownEmbed = new EmbedBuilder()
				.setTitle("Power is off now")
				.setDescription("Why u shut me down?" + System.lineSeparator() + "Why?")
				.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/Circle-icons-power.svg/1200px-Circle-icons-power.svg.png");

			event.getChannel().sendMessage(shuttingDownEmbed);

			try
			{
				User miggi = api.getUserById(309696934174785556L).get();

				new MessageBuilder()
					.setEmbed(new EmbedBuilder()
						.setTitle("Offline")
						.setDescription("See you later Master :pensive:")
						.setColor(Color.ORANGE))
					.send(miggi);
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
			logger.info(event.getMessageAuthor().getName() + " used command!");
			logger.info("System power off! Via Discord");
			System.exit(0);
		}
		else
		{
			EmbedBuilder embed403 = new EmbedBuilder()
				.setTitle("What?")
				.setDescription("You are not my owner!" + System.lineSeparator() + "What are you doing there? :octagonal_sign:")
				.setThumbnail("https://pngimg.com/uploads/denied/denied_PNG2.png");

			logger.info(event.getMessageAuthor().getDisplayName() + " tryed to shutdown our Bot!");
			event.getChannel().sendMessage(embed403);
		}
	}
}
