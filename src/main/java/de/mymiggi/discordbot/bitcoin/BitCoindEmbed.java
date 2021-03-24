package de.mymiggi.discordbot.bitcoin;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class BitCoindEmbed
{
	public EmbedBuilder build(BitCoindResponse bitCoindResponse)
	{
		return new EmbedBuilder()
			.setTitle("Current bitcoin fee")
			.addField("Fast fee", bitCoindResponse.getFastestFee() + " sat/vB", true)
			.addField("Half hour fee", bitCoindResponse.getHalfHourFee() + " sat/vB")
			.addField("Hour fee", bitCoindResponse.getHourFee() + " sat/vB", true)
			.addField("Minimum fee", bitCoindResponse.getMinimumFee() + " sat/vB", true)
			.setColor(Color.YELLOW)
			.setThumbnail("https://pngimg.com/uploads/bitcoin/bitcoin_PNG26.png");
	}
}
