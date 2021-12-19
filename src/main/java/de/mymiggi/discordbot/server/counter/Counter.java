package de.mymiggi.discordbot.server.counter;

import java.awt.Color;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;

public class Counter
{
	private Optional<Server> server;
	private CompletableFuture<Message> message;
	private Optional<ServerChannel> channel;
	private static Logger logger = LoggerFactory.getLogger(Counter.class.getSimpleName());

	public Counter(long serverID, long channelID, long messageID)
	{
		this.server = BotMainCore.api.getServerById(serverID);
		BotMainCore.api.getChannelById(channelID).ifPresent(channel -> {
			this.channel = channel.asServerChannel();
			channel.asTextChannel().ifPresent(textChannel -> {
				message = (messageID == 0)
					? null
					: BotMainCore.api.getMessageById(messageID, textChannel);
			});
		});

	}

	public void run()
	{
		server.ifPresent(server -> {
			update(server.getMemberCount());
			startListener();
		});
	}

	private void startListener()
	{
		server.ifPresent(server -> {
			server.addServerMemberJoinListener(event -> {
				update(server.getMemberCount());
			});
			server.addServerMemberLeaveListener(event -> {
				update(server.getMemberCount());
			});
		});
	}

	private void update(int memberCount)
	{
		if (message == null)
		{
			updateChannelName(memberCount);
		}
		else
		{
			updateEmbed(memberCount);
		}
		server.ifPresent(server -> {
			logger.info("Updating counter for server " + server.getName() + "! - Members:" + server.getMemberCount());
		});
	}

	private void updateChannelName(int memberCount)
	{
		channel.ifPresent(channel -> {
			channel.asServerChannel().ifPresent(serverChannel -> {
				serverChannel.updateName("Members: " + memberCount);
			});
		});
	}

	private void updateEmbed(int memberCount)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Current members " + memberCount)
			.setColor(Color.GRAY);
		embed.setFooter("Welcome again from the whole server team!");
		message.thenAccept(message -> {
			message.edit(embed);
		});
	}
}
