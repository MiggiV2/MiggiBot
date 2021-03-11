package de.mymiggi.discordbot.server.logs.welcomer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.util.WelcomerSetting;

public class WelcomerRunner
{
	private Map<Server, WelcomerSetting> serverAndChannel = new HashMap<Server, WelcomerSetting>();
	private ServerAndChannelsLoader loader = new ServerAndChannelsLoader();
	private long lastUpdateTimeStamp;

	public void run()
	{
		syncHashMap();
		BotMainCore.api.addServerMemberJoinListener(event -> {
			checkIfNeedSync();
			if (serverAndChannel.containsKey(event.getServer()))
			{
				String messageContent = String.format("Welcome to %s, %s", event.getServer(), event.getUser().getMentionTag());
				long channelID = serverAndChannel.get(event.getServer()).getChannelID();
				TextChannel channel = BotMainCore.api.getChannelById(channelID).get().asTextChannel().get();
				long roleID = serverAndChannel.get(event.getServer()).getRoleID();
				Role role = BotMainCore.api.getRoleById(roleID).get();
				channel.sendMessage(messageContent);
				if (!BotMainCore.api.getYourself().canManageRole(role))
				{
					String missingPermissions = "Hey! \r\nMy role must be over the " + role.getName() + " to give new members this role.";
					User owern = event.getServer().getOwner().get();
					owern.sendMessage(missingPermissions);
				}
				else
				{
					try
					{
						event.getUser().addRole(role).get();
					}
					catch (InterruptedException | ExecutionException e)
					{
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void checkIfNeedSync()
	{
		if (lastUpdateTimeStamp < (System.currentTimeMillis() - 10 * 60 * 1000))
		{
			syncHashMap();
		}
	}

	public void syncHashMap()
	{
		serverAndChannel = loader.run();
		lastUpdateTimeStamp = System.currentTimeMillis();
	}

	public int getListSize()
	{
		return serverAndChannel.size();
	}
}
