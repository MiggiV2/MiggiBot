package de.mymiggi.discordbot.bitcoin;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;

public class BitCoinReactionHandler
{
	private static Logger logger = LoggerFactory.getLogger(BitCoinReactionHandler.class.getSimpleName());

	public boolean runAndGetCoolDownNeeded(ReactionAddEvent addEvent, boolean isCoolDown)
	{
		if (!addEvent.getUser().get().isYourself())
		{
			if (addEvent.getEmoji().equalsEmoji("❌"))
			{
				addEvent.getMessage().ifPresent(message -> {
					if (message.canYouDelete())
					{
						message.delete();
					}
				});
			}
			else
			{
				if (addEvent.getEmoji().equalsEmoji("🔄") && !isCoolDown)
				{
					addEvent.getMessage().ifPresent(message -> {
						logger.info("Updated!");
						updateEmbed(message, addEvent.getChannel());
					});
					addEvent.removeReactionByEmojiFromMessage(addEvent.getUser().get(), addEvent.getEmoji());
					return true;
				}
				addEvent.removeReactionByEmojiFromMessage(addEvent.getUser().get(), addEvent.getEmoji());
			}
		}
		return false;
	}

	private void updateEmbed(Message message, TextChannel textChannel)
	{
		try
		{
			String jsonResponse = new Client().sendGetRequest("https://mempool.space/api/v1/fees/recommended").getJsonResult();
			BitCoindResponse bitCoindResponse = new Gson().fromJson(jsonResponse, BitCoindResponse.class);
			message.edit(new BitCoindEmbed().build(bitCoindResponse));
		}
		catch (Exception e)
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Something went wrong!")
				.setDescription("Try later again!")
				.setColor(Color.RED);
			textChannel.sendMessage(embed);
			logger.error("Update failed!", e);
		}
	}
}