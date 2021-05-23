package de.mymiggi.discordbot.server.tenor.gif;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;

public class PostRandomGitAction
{

	public void run(MessageCreateEvent event, String[] context)
	{
		if (BotMainCore.config.getTenorAPIKey() == null)
		{
			sendNotInConfig(event);
		}
		else if (context.length >= 2)
		{
			String searchQuery = buildSearchQuerry(context);
			event.getMessage().addReaction("🎞");
			try
			{
				sendGIF(event, searchQuery);
			}
			catch (Exception e)
			{
				event.getChannel().sendMessage("https://tenor.com/view/tonton-tonton-sticker-no-nope-gif-13636081");
			}
		}
		else
		{
			sendMissingArgument(event);
		}
	}

	private String buildSearchQuerry(String[] context)
	{
		String searchQuery = "";
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
		return searchQuery;
	}

	private void sendNotInConfig(MessageCreateEvent event)
	{
		BotMainCore.api.getOwner().thenAccept(owner -> {
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but there is not Tenor api-key in my config!")
				.setDescription(String.format("Ask %s to set one!", owner.getName()))
				.setColor(Color.RED);
			event.getChannel()
				.sendMessage(embed);
		});
	}

	private void sendMissingArgument(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Sorry, but you forgot soemthing!")
			.setDescription(BotMainCore.prefix + "gif cat")
			.setColor(Color.RED);
		event.getChannel()
			.sendMessage(embed);
	}

	private void sendGIF(MessageCreateEvent event, String searchQuery) throws Exception
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
}
