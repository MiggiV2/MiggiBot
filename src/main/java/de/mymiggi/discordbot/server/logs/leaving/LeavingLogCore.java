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
	private LogChannelLoader loader = new LogChannelLoader();

	public void run()
	{
		syncHashMap();
		BotMainCore.api.addServerMemberLeaveListener(event -> {

			if (!serverTextChannelMap.containsKey(event.getServer()))
			{
				System.err.println(event.getServer() + " not in list for leavingLogs!");
				return;
			}
			EmbedBuilder embed = new EmbedBuilder();

			embed.setAuthor(event.getUser())
				.setDescription("Sorry guys, but I had to go! :wave:");

			TextChannel channel = serverTextChannelMap.get(event.getServer());
			channel.sendMessage(embed);
		});
	}

	public void syncHashMap()
	{
		serverTextChannelMap = loader.run();
	}
	
	public int getListSize()
	{
		return serverTextChannelMap.size();
	}
}
