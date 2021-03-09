package de.mymiggi.discordbot.commands.simple;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;

public class CommandHelp
{
	public void send(MessageCreateEvent event, String[] context)
	{

		EmbedBuilder embed;

		if (context.length == 2 && context[1].equals("admin"))
		{
			embed = adminEmbed();
		}
		else
		{
			embed = normalUserEmbed();
		}

		event.getChannel().sendMessage(embed);
	}

	private EmbedBuilder normalUserEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle(BotMainCore.api.getYourself().getName() + " command help")
			.setImage("https://cdn.pixabay.com/photo/2017/11/10/16/45/faq-2936798_1280.png")
			.setDescription("Some useful commands :nerd:")
			.addField(BotMainCore.prefix + "lookup", "Lookup IPs and Domains")
			.addField(BotMainCore.prefix + "info", "Shows infos about this server")
			.addField(BotMainCore.prefix + "music", "Shows music help")
			.addField(BotMainCore.prefix + "corona", "Shows stats about Corona. Data from Johns Hopkins CSSE")
			.addField(BotMainCore.prefix + "covid19", "Shows stats about Corona. Data from Robert Koch Institute [RECOMMEND]")
			.addField(BotMainCore.prefix + "countryHelp", "Shows country help. Covid stats for your country")
			.addField(BotMainCore.prefix + "help admin", "Shows the bot help for admins");

		return embed;
	}

	private EmbedBuilder adminEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle(BotMainCore.api.getYourself().getName() + " command help")
			.setImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-1YS-wzk_ePlUQNjxS3D6TT0qNh01UT6Y-w&usqp=CAU")
			.setDescription("Some useful commands for admins")
			.addField(BotMainCore.prefix + "purge 10", "Purge / Clear the chat")
			.addField(BotMainCore.prefix + "welcome AUTO-ROLE-ID", "Set the current channel  as welcome channel for new users")
			.addField(BotMainCore.prefix + "counter", "Set the current channel as counter, to see how many useres your server has")
			.addField(BotMainCore.prefix + "leavingLog", "Set the current channel as leaving Log, to see how left the server");

		return embed;
	}
}
