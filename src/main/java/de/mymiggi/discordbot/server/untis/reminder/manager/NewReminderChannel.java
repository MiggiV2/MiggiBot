package de.mymiggi.discordbot.server.untis.reminder.manager;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.untis.reminder.TimeTableReminderCore;
import de.mymiggi.discordbot.server.untis.reminder.manager.embeds.CantFindeRoleEmbed;
import de.mymiggi.discordbot.server.untis.reminder.manager.embeds.NotAdminEmbed;
import de.mymiggi.discordbot.server.untis.reminder.manager.embeds.WrongSyntaxEmbed;
import de.mymiggi.discordbot.tools.database.util.UntisReminderChannelNew;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.Permissions;

public class NewReminderChannel
{
	private Logger logger = LoggerFactory.getLogger(NewReminderChannel.class.getSimpleName());

	public void add(MessageCreateEvent event, String[] context)
	{
		TimeTableReminderCore timeTableReminderCore = BotMainCore.getTimeTableReminderCore();
		ReminderChannelLoader loader = new ReminderChannelLoader();
		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 8);

		if (!Permissions.isAdmin(event))
		{
			logger.warn(event.getMessageAuthor().getName() + " try to use command, but he is not an admin!");
			event.getChannel()
				.sendMessage(new NotAdminEmbed().build(event))
				.thenAccept(embedMessage -> {
					MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 15);
				});
		}
		else if (context.length != 2)
		{
			event.getChannel()
				.sendMessage(new WrongSyntaxEmbed().build())
				.thenAccept(embedMessage -> {
					MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 15);
				});
		}
		else
		{
			try
			{
				long roleID = Long.parseLong(context[1]);
				loader.saveSettingInDB(event, roleID);
				UntisReminderChannelNew temp = new UntisReminderChannelNew(event.getServer().get().getId());
				temp.setChannelID(event.getChannel().getId());
				temp.setServerName(event.getServer().get().getName());
				temp.setRoleID(roleID);
				timeTableReminderCore.startNewThread(temp);
			}
			catch (Exception e)
			{
				if (e.getMessage() != null)
				{
					if (e.getMessage().equals("Cant find this Role!"))
					{
						event.getChannel()
							.sendMessage(new CantFindeRoleEmbed().build())
							.thenAccept(embedMessage -> {
								MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 15);
							});
					}
				}
				else
				{
					EmbedBuilder embed = new EmbedBuilder()
						.setTitle("Something went wrong!")
						.setDescription(e.getClass().getSimpleName())
						.setColor(Color.RED);
					event.getChannel().sendMessage(embed);
				}
				logger.error("Saving failed!", e);
			}
		}
	}
}
