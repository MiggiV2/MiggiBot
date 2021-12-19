package de.mymiggi.discordbot.server.counter;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.CounterSetting;

public class CounterSync
{
	private NewCounterCreator counterCreate = new NewCounterCreator();
	private static Logger logger = LoggerFactory.getLogger(CounterSync.class.getSimpleName());
	private UniversalHibernateClient client = new UniversalHibernateClient();

	public List<CounterSetting> load()
	{
		List<CounterSetting> checkedList = new ArrayList<CounterSetting>();
		List<CounterSetting> respose = client.getList(CounterSetting.class);

		for (CounterSetting setting : respose)
		{
			try
			{
				checkObj(setting, checkedList);
			}
			catch (Exception e)
			{
				logger.warn(e.getMessage());
				logger.warn("Removed from data base!");
				client.delete(setting);
			}
		}
		return checkedList;
	}

	@Deprecated
	public void saveObjInDB(long MessageID, MessageCreateEvent event) throws Exception
	{
		saveObjInDB(MessageID, event.getServer().get().getId(), event.getChannel().getId(), event);
	}

	@Deprecated
	public void saveObjInDB(long MessageID, long ServerID, long ChannelID, MessageCreateEvent event) throws Exception
	{
		CounterSetting temp = new CounterSetting();
		temp.setServerID(ServerID);
		temp.setMessageID(MessageID);
		temp.setChannelID(ChannelID);

		if (!BotMainCore.api.getCachedMessageById(MessageID).isPresent())
		{
			if (event != null)
			{
				counterCreate.notFoundChannel(event);
			}
			throw new Exception("Message not found!");
		}
		client.save(temp);
	}

	public boolean save(CounterSetting toSave)
	{
		return client.save(toSave);
	}

	private void checkObj(CounterSetting setting, List<CounterSetting> checkedList) throws Exception
	{
		if (!BotMainCore.api.getServerById(setting.getServerID()).isPresent())
		{
			throw new Exception("ServerID is outdated! ID: " + setting.getServerID());
		}
		Server server = BotMainCore.api.getServerById(setting.getServerID()).get();
		if (!BotMainCore.api.getChannelById(setting.getChannelID()).isPresent())
		{
			throw new Exception("ChannelID is outdated! ID: " + setting.getChannelID() + " - Server: " + server.getName());
		}
		if (setting.getMessageID() != 0)
		{
			TextChannel channel = BotMainCore.api.getChannelById(setting.getChannelID()).get().asTextChannel().get();
			BotMainCore.api.getMessageById(setting.getMessageID(), channel).get();
			checkedList.add(setting);
		}
		else
		{
			checkedList.add(setting);
		}
	}
}
