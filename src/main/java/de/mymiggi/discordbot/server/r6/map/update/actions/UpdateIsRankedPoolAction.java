package de.mymiggi.discordbot.server.r6.map.update.actions;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class UpdateIsRankedPoolAction extends AbstractUpdateAction
{
	public UpdateIsRankedPoolAction()
	{
		super(NumberEmoji.THREE.getEmoji());
	}

	@Override
	public void run(R6Map map, TextChannel channel)
	{
		map.setRankedPool(!map.isRankedPool());
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Ranked map = " + map.isRankedPool())
			.setColor(Color.GREEN);
		channel
			.sendMessage(embed)
			.thenAccept(message -> {
				MessageCoolDown.del(message.getLink().toString(), channel, 4);
			});
	}
}
