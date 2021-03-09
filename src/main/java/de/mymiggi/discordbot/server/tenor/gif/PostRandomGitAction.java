package de.mymiggi.discordbot.server.tenor.gif;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;

public class PostRandomGitAction
{
	private String searchQuery = "";

	public void run(MessageCreateEvent event, String[] context)
	{
		if (context.length >= 2)
		{
			event.getMessage()
				.addReaction("🎞");
			for (int i = 1; i < context.length; i++)
			{
				if (i == context.length - 1)
				{
					searchQuery += context[i];
				}
				else
				{
					searchQuery += context[i] + "_";
				}
			}
			try
			{
				String gifURL = new RandomGifAction().get(searchQuery);
				event.getChannel()
					.sendMessage(gifURL)
					.thenAccept(message -> {
						message.addReaction("🔄");
						message.addReaction("❌");
						message.addReactionAddListener(reactionAddEvent -> {
							new TenorReactionHandler().run(reactionAddEvent, searchQuery, event.getMessage());
						});
					});
			}
			catch (Exception e)
			{
				String altGif = "https://tenor.com/view/tonton-tonton-sticker-no-nope-gif-13636081";
				event.getChannel()
					.sendMessage(altGif);
			}
		}
		else
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but you forgot soemthing!")
				.setDescription(BotMainCore.prefix + "gif cat")
				.setColor(Color.RED);
			event.getChannel()
				.sendMessage(embed);
		}
	}
}
