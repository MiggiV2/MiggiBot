package de.mymiggi.discordbot.tools.util;

import org.javacord.api.entity.channel.TextChannel;

import de.mymiggi.discordbot.main.BotMainCore;

public class MessageCoolDown
{

	public static void del(String MessageLink, TextChannel textChannel)
	{
		del(MessageLink, textChannel, 10, true);
	}

	public static void removeEmbed(String MessageLink, TextChannel textChannel)
	{
		del(MessageLink, textChannel, 10, false);
	}

	public static void del(String MessageLink, TextChannel textChannel, int timeInSec)
	{
		del(MessageLink, textChannel, timeInSec, true);
	}

	public static void removeEmbed(String MessageLink, TextChannel textChannel, int timeInSec)
	{
		del(MessageLink, textChannel, timeInSec, false);
	}

	public static void del(String MessageLink, TextChannel textChannel, int timeInSec, boolean del)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(timeInSec * 1000);
				}
				catch (Exception e)
				{
				}
				if (del)
				{
					if (MessageLink != null)
					{
						BotMainCore.api.getMessageByLink(MessageLink).ifPresent(messageOptional -> {
							messageOptional.thenAccept(message -> {
								if (message.canYouDelete())
								{
									message.delete();
								}
							});
						});
					}
				}
				else
				{
					BotMainCore.api.getMessageByLink(MessageLink).ifPresent(messageOptional -> {
						messageOptional.thenAccept(message -> {
							if (message.canYouDelete())
							{
								message.removeEmbed();
							}
						});
					});
				}
			}
		};
		thread.start();
	}
}
