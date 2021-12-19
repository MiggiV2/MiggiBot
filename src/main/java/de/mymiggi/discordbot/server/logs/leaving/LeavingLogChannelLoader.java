package de.mymiggi.discordbot.server.logs.leaving;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.LeavingLogSetting;

public class LeavingLogChannelLoader
{
	private NewLeavingLog newLeavingLoger = new NewLeavingLog();
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private Logger logger = LoggerFactory.getLogger(LeavingLogChannelLoader.class.getSimpleName());

	public Map<Server, TextChannel> run()
	{
		List<LeavingLogSetting> list = client.getList(LeavingLogSetting.class);
		Map<Server, TextChannel> map = new HashMap<Server, TextChannel>();
		for (LeavingLogSetting setting : list)
		{
			try
			{
				buildMap(setting, map);
			}
			catch (Exception e)
			{
				logger.warn(e.getMessage());
				logger.warn("Removed from data base!");
				client.delete(setting);
			}
		}
		return map;
	}

	@Deprecated
	public void saveSettingInBD(long ServerID, long ChannelID, MessageCreateEvent event) throws Exception
	{
		LeavingLogSetting temp = new LeavingLogSetting();

		temp.setChannelID(ChannelID);
		temp.setServerID(ServerID);

		if (!BotMainCore.api.getServerById(ServerID).isPresent())
		{
			if (event != null)
			{
				newLeavingLoger.notFoundServer(event);
			}
			System.err.println("Server not found!");
			throw new Exception("Server not found!");
		}

		if (!BotMainCore.api.getChannelById(ChannelID).isPresent())
		{
			if (event != null)
			{
				newLeavingLoger.notFoundChannel(event);
			}
			System.err.println("Channel not found!");
			throw new Exception("Channel not found!");
		}

		client.save(temp);

		if (event != null)
		{
			newLeavingLoger.done(event, event.getChannel().asServerTextChannel().get().getName());
		}
	}

	public boolean save(LeavingLogSetting leavingLogSetting)
	{
		return client.save(leavingLogSetting);
	}

	@Deprecated
	public void buildJsonFile(MessageCreateEvent event) throws Exception
	{
		long channelID = event.getChannel().getId();
		long serverID = event.getServer().get().getId();
		saveSettingInBD(serverID, channelID, event);
	}

	@Deprecated
	public void buildJsonFile(String ServerID, String ChannelID) throws Exception
	{
		MessageCreateEvent event = null;
		long channelID = Long.parseLong(ChannelID);
		long serverID = Long.parseLong(ServerID);
		saveSettingInBD(serverID, channelID, event);
	}

	private Map<Server, TextChannel> buildMap(LeavingLogSetting setting, Map<Server, TextChannel> map) throws Exception
	{
		if (!BotMainCore.api.getServerById(setting.getServerID()).isPresent())
		{
			throw new Exception("ServerID is outdated! ID: " + setting.getServerID());
		}
		Server server = BotMainCore.api.getServerById(setting.getServerID()).get();
		if (!BotMainCore.api.getChannelById(setting.getChannelID()).isPresent())
		{
			throw new Exception(String.format("[%s] channelID is outdated! ID: %s", server.getName(), server.getName()));
		}
		TextChannel channel = BotMainCore.api.getChannelById(setting.getChannelID()).get().asTextChannel().get();
		map.put(server, channel);
		return map;
	}
}
