package de.mymiggi.discordbot.server.reaction.role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.ReactionRoleSetting;

public class ReactionRoleSync
{
	private Logger logger = LoggerFactory.getLogger(ReactionRoleSync.class.getSimpleName());
	private UniversalHibernateClient client = new UniversalHibernateClient();

	public Map<Message, List<ReactionRoleSetting>> run()
	{
		Map<Message, List<ReactionRoleSetting>> map = new HashMap<Message, List<ReactionRoleSetting>>();
		List<ReactionRoleSetting> list = client.getList(ReactionRoleSetting.class);

		for (ReactionRoleSetting setting : list)
		{
			if (checkObjPassed(setting))
			{
				BotMainCore.api.getMessageByLink(setting.getMessageLink()).ifPresent(message -> {
					try
					{
						if (map.containsKey(message.get()))
						{
							map.get(message.get()).add(setting);
						}
						else
						{
							List<ReactionRoleSetting> newConfigList = new ArrayList<ReactionRoleSetting>();
							newConfigList.add(setting);
							map.put(message.get(), newConfigList);
						}
					}
					catch (InterruptedException | ExecutionException e)
					{
						client.delete(setting);
						logger.info("Error", e);
					}
				});
			}
			else
			{
				client.delete(setting);
				logger.warn("Removed from data base!");
			}
		}
		return map;
	}

	public void saveObjInDB(String reaction, String roleID, String MessageLink, long ServerID)
	{
		ReactionRoleSetting setting = new ReactionRoleSetting();

		setting.setMessageLink(MessageLink);
		setting.setServerID(ServerID);
		setting.setRoleID(roleID);
		setting.setReaction(reaction);

		if (checkObjPassed(setting))
		{
			client.save(setting);
		}
	}

	public boolean checkObjPassed(ReactionRoleSetting data)
	{
		if (!BotMainCore.api.getServerById(data.getServerID()).isPresent())
		{
			logger.warn("Server not found! ID: " + data.getServerID());
			return false;
		}
		String serverName = BotMainCore.api.getServerById(data.getServerID()).get().getName();
		if (!BotMainCore.api.getMessageByLink(data.getMessageLink()).isPresent())
		{
			logger.warn("Message not present! Link: " + data.getMessageLink() + " Server: " + serverName);
			return false;
		}
		if (!BotMainCore.api.getRoleById(data.getRoleID()).isPresent())
		{
			logger.warn("Role not present! Server: " + serverName);
			return false;
		}
		return true;
	}
}
