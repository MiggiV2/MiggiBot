package de.mymiggi.discordbot.server.counter;

import java.awt.Color;

import org.javacord.api.entity.channel.ServerChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.util.CounterSetting;

public class NewCounterAction
{
	private EmbedBuilder responseEmbed = getPrivateEmbed();

	public void run(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		User user = interaction.getUser();
		interaction.getServer().ifPresent(server -> {
			interaction.getFirstOptionChannelValue().ifPresent(channel -> {
				if (server.isAdmin(user))
				{
					addNewCounter(server, channel);
				}
				else
				{
					responseEmbed = getNotAdminEmbed(user);
				}
			});
		});
		interaction.createImmediateResponder()
			.addEmbed(responseEmbed)
			.respond();
	}

	private void addNewCounter(Server server, ServerChannel channel)
	{
		MemberCounterCore counter = BotMainCore.getCounter();
		CounterSetting toSave = new CounterSetting()
			.setChannelID(channel.getId())
			.setMessageID(0)
			.setServerID(server.getId())
			.setTimeStamp(System.currentTimeMillis());
		responseEmbed = counter.add(toSave)
			? getDoneEmbed(channel)
			: getFailedEmbed();
		counter.syncList();
		counter.runLatestCounter();
		channel.createUpdater()
			.addPermissionOverwrite(server.getEveryoneRole(),
				new PermissionsBuilder()
					.setDenied(PermissionType.CONNECT)
					.build())
			.update();
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

	private EmbedBuilder getDoneEmbed(ServerChannel serverChannelValue)
	{
		return new EmbedBuilder()
			.setTitle("Successfully added channel " + serverChannelValue.getName())
			.setFooter("Btw, thx for using my bot! <3 Miggi")
			.setColor(Color.GREEN);
	}

	private EmbedBuilder getNotAdminEmbed(User user)
	{
		return new EmbedBuilder()
			.setTitle("You are not an admin " + user.getName() + "!")
			.setFooter("Maybe u can aske the owner?")
			.setColor(Color.RED);
	}
}
