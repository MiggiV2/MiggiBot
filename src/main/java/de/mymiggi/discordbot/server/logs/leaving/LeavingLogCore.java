package de.mymiggi.discordbot.server.logs.leaving;

import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;

import de.mymiggi.discordbot.main.BotMainCore;

public class LeavingLogCore
{
	private Map<Server, TextChannel> serverTextChannelMap = new HashMap<Server, TextChannel>();
	private LeavingLogChannelLoader loader = new LeavingLogChannelLoader();
	private long lastUpdateTimeStamp;

	public void run()
	{
		syncHashMap();
		BotMainCore.api.addServerMemberLeaveListener(event -> {
			checkIfNeedSync();
			if (serverTextChannelMap.containsKey(event.getServer()))
			{
				EmbedBuilder embed = new EmbedBuilder()
					.setAuthor(event.getUser())
					.setDescription("Sorry guys, but I had to go! :wave:");
				serverTextChannelMap.get(event.getServer()).sendMessage(embed);
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
		serverTextChannelMap = loader.run();
		lastUpdateTimeStamp = System.currentTimeMillis();
	}

	public int getListSize()
	{
		return serverTextChannelMap.size();
	}
}
