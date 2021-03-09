package de.mymiggi.discordbot.server.counter;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;

public class Counter
{
	private Server server;
	private Message message;
	private TextChannel channel;
	private static Logger logger = LoggerFactory.getLogger(Counter.class.getSimpleName());

	public Counter(long serverID, long channelID, long messageID)
	{
		server = BotMainCore.api.getServerById(serverID).get();
		channel = BotMainCore.api.getChannelById(channelID).get().asTextChannel().get();
		try
		{
			message = BotMainCore.api.getMessageById(messageID, channel).get();
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.getClass().getSimpleName();
		}
	}

	public void run()
	{
		updateEmbed(server.getMemberCount());
		startListener();
	}

	private void startListener()
	{
		server.addServerMemberJoinListener(event -> {
			updateEmbed(server.getMemberCount());
		});
		server.addServerMemberLeaveListener(event -> {
			updateEmbed(server.getMemberCount());
		});
	}

	private void updateEmbed(int memberCount)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Current members " + memberCount)
			.setColor(Color.GRAY);

		if (server.getId() == 605083050770038787L)
		{
			embed.setFooter("Welcome again from the whole B4A Squard!");
		}
		else
		{
			embed.setFooter("Welcome again from the whole server team!");
		}
		if (message == null)
		{
			logger.error("Message is null | Not found");
			return;
		}
		message.edit(embed);
	}
}
