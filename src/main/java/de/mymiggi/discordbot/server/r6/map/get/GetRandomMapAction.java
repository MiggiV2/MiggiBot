package de.mymiggi.discordbot.server.r6.map.get;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class GetRandomMapAction
{
	private Message mapEmbed;
	private static Logger logger = LoggerFactory.getLogger(GetRandomMapAction.class.getSimpleName());

	public void run(Message eventMessage, TextChannel channel, String[] context, List<R6Map> mapList)
	{
		EmbedBuilder embed;
		boolean isRanedMap;
		if (context.length == 2 && context[1].equalsIgnoreCase("all"))
		{
			embed = new MapEmbed(mapList).buildRandomMap();
			isRanedMap = false;
		}
		else
		{
			embed = new MapEmbed(mapList).buildRandomRankedMap();
			isRanedMap = true;
		}
		channel.sendMessage(embed).thenAccept(message -> {
			mapEmbed = message;
		});
		eventMessage.addReaction("🔄");
		eventMessage.addReaction("✅");
		eventMessage.addReactionAddListener(reactionAddEvent -> {
			new RandomMapReactionHandler().run(reactionAddEvent, isRanedMap, mapEmbed, mapList);
		});
	}

	@Deprecated
	public void initializeDB(MessageCreateEvent event)
	{
		if (event.getMessageAuthor().isBotOwner())
		{
			List<R6Map> mapList = new ArrayList<R6Map>();
			for (Maps temp : Maps.values())
			{
				R6Map newMap = new R6Map();
				newMap.setImageURL(temp.getImageURL());
				newMap.setName(temp.getAliase());
				newMap.setRankedPool(temp.isRankedPool());
				mapList.add(newMap);
			}
			new UniversalHibernateClient().saveList(mapList);
			logger.info("Saved!");
		}
		else
		{
			logger.info("Not Bot owner! " + event.getMessageAuthor().getName());
		}
	}
}
