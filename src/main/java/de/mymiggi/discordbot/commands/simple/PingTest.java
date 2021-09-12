package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.http.Client;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.CounterSetting;

public class PingTest
{
	public void run(MessageCreateEvent event)
	{
		long reactionTimeDiscord = getResponseTimeDiscord(event);
		long reactionTimeForDB = getTimeReactionTimeForDB(event);
		long reactionTimeHttp = getResposeTimeHTTP();
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Response stats")
			.setDescription(
				"Reaction time to discord: " + reactionTimeDiscord + "ms\r\n"
					+ "Reacton time for http request: " + reactionTimeHttp + "ms\r\n"
					+ "Reaction time to data base: " + reactionTimeForDB + "ms")

			.setColor(Color.BLUE);
		event.getChannel().sendMessage(embed);
	}

	public void run(SlashCommandCreateEvent event)
	{
		event.getSlashCommandInteraction().respondLater();
		long reactionTimeDiscord = getResponseTimeDiscord(event);
		long reactionTimeForDB = getTimeReactionTimeForDB();
		long reactionTimeHttp = getResposeTimeHTTP();
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Response stats")
			.setDescription(
				"Reaction time to discord: " + reactionTimeDiscord + "ms\r\n"
					+ "Reacton time for http request: " + reactionTimeHttp + "ms\r\n"
					+ "Reaction time to data base: " + reactionTimeForDB + "ms")

			.setColor(Color.BLUE);
		event.getSlashCommandInteraction()
			.createFollowupMessageBuilder()
			.addEmbed(embed)
			.send();
	}

	private long getResposeTimeHTTP()
	{
		Client client = new Client();
		long timeStampReactionEvent1 = System.currentTimeMillis();
		try
		{
			client.sendGet("https://www.google.de");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long timeStampReactionEvent2 = System.currentTimeMillis();
		return timeStampReactionEvent2 - timeStampReactionEvent1;
	}

	private long getResponseTimeDiscord(MessageCreateEvent event)
	{
		long timeStampReactionEvent1 = System.currentTimeMillis();
		event.getMessage()
			.addReaction("👍")
			.join();
		long timeStampReactionEvent2 = System.currentTimeMillis();
		return timeStampReactionEvent2 - timeStampReactionEvent1;
	}

	private long getResponseTimeDiscord(SlashCommandCreateEvent event)
	{
		long timeStampReactionEvent1 = System.currentTimeMillis();
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Loading...")
			.setColor(Color.BLUE);
		event.getSlashCommandInteraction().getChannel()
			.ifPresent(channel -> {
				channel.sendMessage(embed).join().delete();
			});
		long timeStampReactionEvent2 = System.currentTimeMillis();
		return timeStampReactionEvent2 - timeStampReactionEvent1;
	}

	private long getTimeReactionTimeForDB()
	{
		UniversalHibernateClient client = new UniversalHibernateClient();
		CounterSetting counter = new CounterSetting();
		counter.setChannelID(0L);
		counter.setMessageID(0L);
		counter.setServerID(0L);
		long timeStampDB1 = System.currentTimeMillis();
		client.save(counter);
		long timeStampDB2 = System.currentTimeMillis();
		client.delete(counter);
		return timeStampDB2 - timeStampDB1;
	}

	private long getTimeReactionTimeForDB(MessageCreateEvent event)
	{
		UniversalHibernateClient client = new UniversalHibernateClient();
		CounterSetting counter = new CounterSetting();
		counter.setChannelID(event.getChannel().getId());
		counter.setMessageID(event.getMessageId());
		counter.setServerID(event.getServer().get().getId());
		long timeStampDB1 = System.currentTimeMillis();
		client.save(counter);
		long timeStampDB2 = System.currentTimeMillis();
		client.delete(counter);
		return timeStampDB2 - timeStampDB1;
	}
}
