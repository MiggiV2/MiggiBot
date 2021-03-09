package de.mymiggi.discordbot.server.r6.map.update.actions;

import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.r6.map.update.embeds.SavedEmbed;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class SaveAction
{
	private static Logger logger = LoggerFactory.getLogger(SaveAction.class.getSimpleName());

	public void run(R6Map updatedMap, String originalMapName, TextChannel channel)
	{
		UniversalHibernateClient client = new UniversalHibernateClient();
		if (updatedMap.getName().equals(originalMapName))
		{
			client.save(updatedMap);
			logger.info("Updated map " + updatedMap.getName());
		}
		else
		{
			R6Map originalMap = new R6Map();
			originalMap.setName(originalMapName);
			client.delete(originalMap);
			client.save(updatedMap);
			logger.info("Replaced map " + updatedMap.getName());
		}
		channel
			.sendMessage(new SavedEmbed().build())
			.thenAccept(embed -> {
				MessageCoolDown.del(embed.getLink().toString(), channel, 6);
			});
	}
}
