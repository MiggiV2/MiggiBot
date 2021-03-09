package de.mymiggi.discordbot.server.r6.matchmaker.embeds;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;

public class NeedToRegisterEmbed
{
	public EmbedBuilder build(List<User> notRegisterdUser)
	{
		String playerList = "";
		for (User temp : notRegisterdUser)
		{
			playerList += temp.getName() + "\r\n";
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("The following players aren't registered!")
			.setColor(Color.ORANGE)
			.setDescription(playerList + "**Use " + BotMainCore.prefix + "newPlayer to register new R6Player!");
		return embed;
	}
}
