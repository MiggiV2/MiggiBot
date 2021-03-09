package de.mymiggi.discordbot.server.member.playlist;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorJoinHandlerCore
{
	private Logger logger = LoggerFactory.getLogger(ErrorJoinHandlerCore.class.getSimpleName());

	public EmbedBuilder run(Exception e)
	{
		EmbedBuilder embed = new EmbedBuilder();
		switch (e.getMessage())
		{
			case "Not found!":
				embed.setTitle("Sorry, but I cant find your playlist!")
					.setColor(Color.BLACK);
				break;
			case "Still in this playList!":
				embed.setTitle("I'm sorry, but you are still in this playlist!")
					.setColor(Color.BLACK);
				break;
			case "No playlist for user found!":
				embed.setTitle("Please create first a playlist!")
					.setColor(Color.BLACK);
				break;
			default:
				embed.setTitle(e.getMessage())
					.setColor(Color.RED)
					.setFooter("Error! Please inform Miggi#9895");
				logger.error("Need Help!", e);
				break;
		}
		return embed;
	}
}
