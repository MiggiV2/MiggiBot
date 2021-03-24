package de.mymiggi.discordbot.bitcoin;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;

public class BitCoinCommand
{
	private boolean isCoolDown = false;
	private static Logger logger = LoggerFactory.getLogger(BitCoinCommand.class.getSimpleName());

	public void run(MessageCreateEvent event)
	{
		try
		{
			String jsonResponse = new Client().sendGetRequest("https://mempool.space/api/v1/fees/recommended").getJsonResult();
			BitCoindResponse bitCoindResponse = new Gson().fromJson(jsonResponse, BitCoindResponse.class);
			event.getMessage().addReaction("🤑");
			event.getChannel().sendMessage(new BitCoindEmbed().build(bitCoindResponse))
				.thenAccept(message -> {
					message.addReaction("🔄");
					message.addReaction("❌");
					message.addReactionAddListener(addEvent -> {
						if (new BitCoinReactionHandler().runAndGetCoolDownNeeded(addEvent, isCoolDown))
						{
							startCoolDown();
						}
					});
				});
			startCoolDown();
		}
		catch (Exception e)
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Something went wrong!")
				.setDescription("Try later again!")
				.setColor(Color.RED);
			event.getChannel().sendMessage(embed);
			logger.error("Send failed!", e);
		}

	}

	private void startCoolDown()
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				isCoolDown = true;
				try
				{
					Thread.sleep(30 * 1000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				isCoolDown = false;
			}
		};
		thread.start();
	}
}
