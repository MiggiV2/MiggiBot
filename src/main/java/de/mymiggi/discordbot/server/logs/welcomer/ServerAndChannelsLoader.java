package de.mymiggi.discordbot.server.logs.welcomer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.WelcomerSetting;

public class ServerAndChannelsLoader
{
	private Logger logger = LoggerFactory.getLogger(ServerAndChannelsLoader.class.getSimpleName());
	private UniversalHibernateClient client = new UniversalHibernateClient();

	public Map<Server, WelcomerSetting> run()
	{
		List<WelcomerSetting> list = client.getList(WelcomerSetting.class);
		Map<Server, WelcomerSetting> serverAndChannel = new HashMap<Server, WelcomerSetting>();

		for (WelcomerSetting setting : list)
		{
			try
			{
				addServer(setting, serverAndChannel);
			}
			catch (Exception e)
			{
				logger.info("Error", e);
			}
		}

		return serverAndChannel;
	}

	public void buildJsonFile(long ServerID, long ChannelID, long RoleID, MessageCreateEvent event) throws Exception
	{
		WelcomerSetting temp = new WelcomerSetting();
		NewWelcomerCreater newWelcomer = new NewWelcomerCreater();

		temp.setChannelID(ChannelID);
		temp.setServerID(ServerID);
		temp.setRoleID(RoleID);
		temp.setTimeStamp(System.currentTimeMillis());

		if (!BotMainCore.api.getServerById(ServerID).isPresent())
		{
			if (event != null)
			{
				newWelcomer.notFoundServer(event);
			}
			logger.error("[ServerAndChannelsLoader] Server not found!");
			throw new Exception("Server not found!");
		}

		if (!BotMainCore.api.getChannelById(ChannelID).isPresent())
		{
			if (event != null)
			{
				newWelcomer.notFoundChannel(event);
			}
			logger.error("[ServerAndChannelsLoader] Channel not found!");
			throw new Exception("Channel not found!");
		}
		if (!BotMainCore.api.getRoleById(RoleID).isPresent())
		{
			if (event != null)
			{
				newWelcomer.notFoundChannel(event);
			}
			logger.error("[ServerAndChannelsLoader] Role not found!");
			throw new Exception("Role not found!");
		}

		client.save(temp);

		if (event != null)
		{
			newWelcomer.done(event, event.getChannel().asServerTextChannel().get().getName());
		}
	}

	public void buildJsonFile(MessageCreateEvent event, String RoleID) throws Exception
	{
		long channelID = event.getChannel().getId();
		long serverID = event.getServer().get().getId();
		long roleID = Long.parseLong(RoleID);
		buildJsonFile(serverID, channelID, roleID, event);
	}

	private void addServer(WelcomerSetting settings, Map<Server, WelcomerSetting> serverAndChannel) throws Exception
	{
		long channelID = settings.getChannelID();
		long roleID = settings.getRoleID();

		if (!BotMainCore.api.getServerById(settings.getServerID()).isPresent())
		{
			throw new Exception("ServerID is outdated! ID: " + settings.getServerID());
		}
		Server server = BotMainCore.api.getServerById(settings.getServerID()).get();

		if (!BotMainCore.api.getChannelById(channelID).isPresent())
		{
			throw new Exception("Eror in File: " + server.getName() + ", channelID is outdated! ID: " + channelID);
		}
		if (!BotMainCore.api.getRoleById(roleID).isPresent())
		{
			throw new Exception("Eror in File: " + server.getName() + ", roleID is outdated! ID: " + roleID);
		}
		serverAndChannel.put(server, settings);
	}
}
