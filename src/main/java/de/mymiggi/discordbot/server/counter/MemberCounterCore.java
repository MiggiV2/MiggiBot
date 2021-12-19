package de.mymiggi.discordbot.server.counter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.util.CounterSetting;

public class MemberCounterCore
{
	private List<CounterSetting> list = new ArrayList<CounterSetting>();
	private CounterSync sync = new CounterSync();
	private static Logger logger = LoggerFactory.getLogger(MemberCounterCore.class.getSimpleName());

	public void run()
	{
		syncList();
		list.forEach(linked -> {
			Counter counter = new Counter(linked.getServerID(), linked.getChannelID(), linked.getMessageID());
			counter.run();
		});
	}

	public void syncList()
	{
		list = sync.load();
	}

	public void runLatestCounter()
	{
		int listSize = list.size();
		if (listSize > 0)
		{
			CounterSetting counterSetting = list.get(listSize - 1);
			Counter newCounter = new Counter(counterSetting.getServerID(), counterSetting.getChannelID(), counterSetting.getMessageID());
			newCounter.run();
		}
	}

	public boolean add(CounterSetting counterSetting)
	{
		return sync.save(counterSetting);
	}

	public void sendStarEmbed(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();
		int membersSize = event.getServer().get().getMemberCount();

		embed.setTitle("Current members " + membersSize)
			.setColor(Color.GRAY)
			.setFooter("Welcome again from the whole server team!");
		try
		{
			long messageID = event.getServerTextChannel().get().sendMessage(embed).get().getId();
			sync.saveObjInDB(messageID, event);
		}
		catch (Exception e)
		{
			logger.debug("Error", e);
		}
	}

	public int getListSize()
	{
		return list.size();
	}
}
