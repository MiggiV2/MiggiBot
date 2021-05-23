package de.mymiggi.discordbot.server.untis.reminder.manager;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
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
		if (!Permissions.isAdmin(event))
		{
			sendNotAdmin(event);
		}
		else if (context.length != 2)
		{
			sendWrongSyntax(event);
		}
		else if (BotMainCore.config.getUntisSchoolName() == null)
		{
			sendNotInCofig(event);
		}
		else
		{
			try
			{
				addChannel(event, context[1]);
			}
			catch (Exception e)
			{
				handleError(event, e);
			}
		}
	}

	private void handleError(MessageCreateEvent event, Exception e)
	{
		if (e.getMessage() != null && e.getMessage().equals("Cant find this Role!"))
		{
			event.getChannel()
				.sendMessage(new CantFindeRoleEmbed().build())
				.thenAccept(embedMessage -> {
					MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 15);
				});
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

	private void addChannel(MessageCreateEvent event, String roleIDStr) throws Exception
	{
		long roleID = Long.parseLong(roleIDStr);
		new ReminderChannelLoader().saveSettingInDB(event, roleID);
		UntisReminderChannelNew temp = new UntisReminderChannelNew(event.getServer().get().getId());
		temp.setChannelID(event.getChannel().getId());
		temp.setServerName(event.getServer().get().getName());
		temp.setRoleID(roleID);
		BotMainCore.getTimeTableReminderCore().startNewThread(temp);
		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 8);
	}

	private void sendNotInCofig(MessageCreateEvent event)
	{
		BotMainCore.api.getOwner().thenAccept(owner -> {
			EmbedBuilder embed = new EmbedBuilder()
				.setTitle("Sorry, but UntisSchoolName is not in my config!")
				.setDescription(String.format("Ask %s to set this in my config!", owner.getName()))
				.setColor(Color.RED);
			event.getChannel().sendMessage(embed);
		});
	}

	private void sendWrongSyntax(MessageCreateEvent event)
	{
		event.getChannel()
			.sendMessage(new WrongSyntaxEmbed().build())
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 15);
			});
	}

	private void sendNotAdmin(MessageCreateEvent event)
	{
		logger.warn(event.getMessageAuthor().getName() + " try to use command, but he is not an admin!");
		event.getChannel()
			.sendMessage(new NotAdminEmbed().build(event))
			.thenAccept(embedMessage -> {
				MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 15);
			});
	}
}
