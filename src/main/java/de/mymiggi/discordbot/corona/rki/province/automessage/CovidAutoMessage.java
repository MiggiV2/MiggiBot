package de.mymiggi.discordbot.corona.rki.province.automessage;

import java.util.List;

import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.corona.rki.province.RKIProvince;
import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.CovidChannelConfig;
import de.mymiggi.discordbot.tools.util.TimeCalculator;

public class CovidAutoMessage
{
	private Logger logger = LoggerFactory.getLogger(CovidAutoMessage.class.getSimpleName());
	private RKIProvince rki = new RKIProvince();
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private List<CovidChannelConfig> configs = client.getList(CovidChannelConfig.class);

	public void startThread()
	{
		configs = client.getList(CovidChannelConfig.class);
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				runTask();
			}
		};
		thread.start();
	}

	private void runTask()
	{
		boolean running = true;
		try
		{
			new TimeCalculator().sleepTill(5);
		}
		catch (InterruptedException e)
		{
			logger.warn("Error", e);
		}
		while (running)
		{
			rki.syncData();
			for (CovidChannelConfig temp : configs)
			{
				if (BotMainCore.api.getChannelById(temp.getChannelID()).isPresent())
				{
					try
					{
						TextChannel channel = BotMainCore.api.getChannelById(temp.getChannelID()).get().asTextChannel().get();
						rki.sendInChannel(channel);
					}
					catch (Exception e)
					{
						client.delete(temp);
						logger.error("Channel ID outdated! -> removed", e);
					}
				}
				else
				{
					client.delete(temp);
					logger.error("Channel ID outdated! -> removed", temp.getServerName());
				}
			}
			try
			{
				Thread.sleep(2 * 24 * 60 * 60 * 1000);
			}
			catch (InterruptedException e)
			{
				logger.error("Error", e);
			}
		}
	}

	public List<CovidChannelConfig> getConfigs()
	{
		if (configs.isEmpty())
		{
			configs = client.getList(CovidChannelConfig.class);
		}
		return configs;
	}
}
