package de.mymiggi.discordbot.corona.rki.province.automessage;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.CovidChannelConfig;

public class NewCovidChannelConfig
{
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private List<CovidChannelConfig> channelConfigs = BotMainCore.getCovidAutoMessage().getConfigs();
	private Logger logger = LoggerFactory.getLogger(NewCovidChannelConfig.class.getSimpleName());

	public void create(MessageCreateEvent event)
	{
		long channelID = event.getChannel().getId();
		long serverID = event.getServer().get().getId();

		CovidChannelConfig config = new CovidChannelConfig();
		config.setChannelID(channelID);
		config.setServerID(serverID);
		config.setServerName(event.getServer().get().getName());
		config.setChannelName(event.getChannel().asServerTextChannel().get().getName());
		EmbedBuilder embed = new EmbedBuilder();

		if (!stillThisConfig(config))
		{
			client.save(config);
			event.addReactionsToMessage("👍");
			embed.setTitle("Added this channel!")
				.setColor(Color.GREEN);
			channelConfigs.add(config);
		}
		else
		{
			embed.setTitle("This channel is already set, as covid channel!")
				.setColor(Color.BLACK);
		}
		event.getChannel().sendMessage(embed);
	}

	public void run(MessageCreateEvent event, String[] context)
	{
		if (context.length == 1)
		{
			try
			{
				create(event);
			}
			catch (Exception e)
			{
				logger.warn("Failed", e);
			}
		}
		if (context.length == 2)
		{
			if (context[1].equals("rm") || context[1].equals("delete"))
			{
				delete(event);
			}
			if (context[1].equals("create"))
			{
				try
				{
					create(event);
				}
				catch (Exception e)
				{
					logger.warn("Failed", e);
				}
			}
		}
	}

	public void delete(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();
		if (configsContainsServerID(event.getServer().get().getId()))
		{
			try
			{
				client.delete(getConfigByServerID(event.getServer().get().getId()));
				channelConfigs.remove(getConfigByServerID(event.getServer().get().getId()));
				embed.setTitle("Removed this channel!")
					.setColor(Color.GREEN);
			}
			catch (Exception e1)
			{
				logger.warn("Failed", e1);
			}
		}
		else
		{
			embed.setTitle("Cant find your server! Maybe still deleted?")
				.setColor(Color.BLACK);
		}
		event.addReactionsToMessage("👍");
		event.getChannel().sendMessage(embed);
	}

	private CovidChannelConfig getConfigByServerID(long ServerID) throws Exception
	{
		for (CovidChannelConfig temp : channelConfigs)
		{
			if (temp.getServerID() == ServerID)
			{
				return temp;
			}
		}
		throw new Exception("not found!");
	}

	private boolean configsContainsServerID(long serverID)
	{
		for (CovidChannelConfig temp : channelConfigs)
		{
			if (temp.getServerID() == serverID)
			{
				return true;
			}
		}
		return false;
	}

	private boolean stillThisConfig(CovidChannelConfig config)
	{
		if (configsContainsServerID(config.getServerID()))
		{
			try
			{
				if (getConfigByServerID(config.getServerID()).getChannelID() == config.getChannelID())
				{
					return true;
				}
			}
			catch (Exception e)
			{
				logger.warn("Failed", e);
			}
		}
		return false;
	}
}
