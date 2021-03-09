package de.mymiggi.discordbot.server.untis.reminder.manager;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.untis.reminder.manager.embeds.ChannelNotFoundEmbed;
import de.mymiggi.discordbot.server.untis.reminder.manager.embeds.DoneEmbed;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.UntisReminderChannelNew;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ReminderChannelLoader
{
	private UniversalHibernateClient client = new UniversalHibernateClient();
	private Logger logger = LoggerFactory.getLogger(ReminderChannelLoader.class.getSimpleName());

	public List<UntisReminderChannelNew> run()
	{
		List<UntisReminderChannelNew> list = client.getList(UntisReminderChannelNew.class);
		List<UntisReminderChannelNew> channels = new ArrayList<UntisReminderChannelNew>();

		for (UntisReminderChannelNew setting : list)
		{
			try
			{
				long channelID = setting.getChannelID();
				BotMainCore.api.getChannelById(channelID).ifPresent(channel -> {
					channel.asTextChannel().ifPresent(textChannel -> {
						channels.add(setting);
					});
				});
				if (!BotMainCore.api.getChannelById(channelID).isPresent())
				{
					client.delete(setting);
					logger.warn("Deleted config! Server " + setting.getServerName() + " Channel " + setting.getChannelName());
				}
			}
			catch (Exception e)
			{
				logger.info("Error", e);
			}
		}
		return channels;
	}

	public void saveSettingInBD(String channelName, long ChannelID, long serverID, long roleID, MessageCreateEvent event) throws Exception
	{
		if (!BotMainCore.api.getRoleById(roleID).isPresent())
		{
			throw new Exception("Cant find this Role!");
		}
		UntisReminderChannelNew temp = new UntisReminderChannelNew(serverID);
		temp.setChannelID(ChannelID);
		temp.setServerName(event.getServer().get().getName());
		temp.setChannelName(channelName);
		temp.setRoleID(roleID);
		if (!BotMainCore.api.getChannelById(ChannelID).isPresent())
		{
			if (event != null)
			{
				Message embed = event.getChannel().sendMessage(new ChannelNotFoundEmbed().build()).get();
				MessageCoolDown.del(embed.getLink().toString(), event.getChannel(), 120);
			}
			logger.warn("Channel not found!");
			throw new Exception("Channel not found!");
		}
		client.save(temp);
		if (event != null)
		{
			Message embed = event.getChannel().sendMessage(new DoneEmbed().build(channelName)).get();
			// MessageCoolDown.removeEmbed(embed.getLink().toString(),
			// event.getChannel(), 2);
			MessageCoolDown.del(embed.getLink().toString(), event.getChannel(), 20);
		}
	}

	public void saveSettingInDB(MessageCreateEvent event, long roleID) throws Exception
	{
		long channelID = event.getChannel().getId();
		long serverID = event.getServer().get().getId();
		String channelName = event.getChannel().asServerTextChannel().get().getName();
		saveSettingInBD(channelName, channelID, serverID, roleID, event);
	}
}
