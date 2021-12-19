package de.mymiggi.discordbot.server.logs;

import java.awt.Color;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.logs.leaving.LeavingLogChannelLoader;
import de.mymiggi.discordbot.server.logs.leaving.LeavingLogCore;
import de.mymiggi.discordbot.server.logs.welcomer.ServerAndChannelsLoader;
import de.mymiggi.discordbot.server.logs.welcomer.WelcomerRunner;
import de.mymiggi.discordbot.tools.database.util.LeavingLogSetting;
import de.mymiggi.discordbot.tools.database.util.WelcomerSetting;

public class NewLoggerCreater
{
	private Logger logger = LoggerFactory.getLogger(NewLoggerCreater.class);
	private EmbedBuilder responseEmbed = getPrivateEmbed();
	private SlashCommandInteraction interaction;
	private Server server;
	private ServerChannel serverChannelValue;
	private User user;

	public NewLoggerCreater(SlashCommandCreateEvent event)
	{
		this.interaction = event.getSlashCommandInteraction();
		this.user = event.getInteraction().getUser();
		interaction.getServer().ifPresent(server -> {
			interaction.getChannel().ifPresent(channel -> {
				channel.asServerChannel().ifPresent(serverChannel -> {
					if (!server.isAdmin(user))
					{
						logger.info(user + " try to use command, but he is not an admin!");
						responseEmbed = getNotAdminEmbed();
					}
					else
					{
						this.serverChannelValue = interaction.getSecondOptionChannelValue().orElse(serverChannel);
						this.server = server;
					}
				});
			});
		});
	}

	public void addLeavingLog()
	{
		LeavingLogCore leavingLogger = BotMainCore.getLeavingLogger();
		LeavingLogChannelLoader manager = new LeavingLogChannelLoader();
		LeavingLogSetting logSetting = new LeavingLogSetting()
			.setChannelID(serverChannelValue.getId())
			.setServerID(server.getId())
			.setTimeStamp(System.currentTimeMillis());
		responseEmbed = manager.save(logSetting)
			? getDoneEmbed()
			: getFailedEmbed();
		leavingLogger.syncHashMap();
		interaction.createImmediateResponder()
			.addEmbed(responseEmbed)
			.respond();
	}

	public void addWelcomer()
	{
		ServerAndChannelsLoader manager = new ServerAndChannelsLoader();
		WelcomerRunner welcomer = BotMainCore.getWelcomer();
		interaction.getFirstOptionRoleValue().ifPresent(role -> {
			WelcomerSetting temp = new WelcomerSetting()
				.setChannelID(serverChannelValue.getId())
				.setRoleID(role.getId())
				.setServerID(server.getId())
				.setTimeStamp(System.currentTimeMillis());
			responseEmbed = manager.save(temp)
				? getDoneEmbed()
				: getFailedEmbed();
			welcomer.syncHashMap();
		});
		interaction.createImmediateResponder()
			.addEmbed(responseEmbed)
			.respond();
	}

	private EmbedBuilder getPrivateEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Something went wrong!")
			.setFooter("You cant use this command here!")
			.setColor(Color.RED);
	}

	private EmbedBuilder getFailedEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Something went wrong!")
			.setFooter("I'm so sry. I will fix this! Contact me -> Miggi#9895")
			.setColor(Color.RED);
	}

	private EmbedBuilder getDoneEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Successfully added channel " + serverChannelValue.getName())
			.setFooter("Btw, thx for using my bot! <3 Miggi")
			.setColor(Color.GREEN);
	}

	private EmbedBuilder getNotAdminEmbed()
	{
		return new EmbedBuilder()
			.setTitle("You are not an admin " + user.getName() + "!")
			.setFooter("Maybe u can aske the owner?")
			.setColor(Color.RED);
	}
}
