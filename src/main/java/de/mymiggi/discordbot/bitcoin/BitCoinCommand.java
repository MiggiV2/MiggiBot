package de.mymiggi.discordbot.bitcoin;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;

public class BitCoinCommand
{
	public void run(MessageCreateEvent event)
	{
		try
		{
			String jsonResponse = new Client().sendGetRequest("https://mempool.space/api/v1/fees/recommended").getJsonResult();
			BitCoindResponse bitCoindResponse = new Gson().fromJson(jsonResponse, BitCoindResponse.class);
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Current bitcoin fee")
				.addField("Fast fee", bitCoindResponse.getFastestFee() + " sat/vB", true)
				.addField("Half hour fee", bitCoindResponse.getHalfHourFee() + " sat/vB")
				.addField("Hour fee", bitCoindResponse.getHourFee() + " sat/vB", true)
				.addField("Minimum fee", bitCoindResponse.getMinimumFee() + " sat/vB", true)
				.setColor(Color.YELLOW)
				.setThumbnail("https://pngimg.com/uploads/bitcoin/bitcoin_PNG26.png");
			event.getMessage().addReaction("🤑");
			event.getChannel().sendMessage(embed);
		}
		catch (Exception e)
		{
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Something went wrong!")
				.setDescription("Try later again!")
				.setColor(Color.RED);
			event.getChannel().sendMessage(embed);
			e.printStackTrace();
		}

	}
}
