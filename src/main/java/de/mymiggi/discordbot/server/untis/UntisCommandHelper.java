package de.mymiggi.discordbot.server.untis;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;

public class UntisCommandHelper
{
	public void run(MessageCreateEvent event)
	{
		String prefix = BotMainCore.prefix;
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Help for untis commands")
			.addField(prefix + "addUntisReminder [RoleID]", "Set current channel as untis reminder channel")
			.addField("/untis-next", "Get next lesson for today")
			.addField("/untis-timetable", "See our timetabel for this week")
			.setThumbnail("http://lh3.googleusercontent.com/6lUhld8gFhB0_b-lpce_crw-gdH70lDnXot5ckVmOFMh91jag56whanU-Q30nLt68sr5=w300");
		event.getChannel()
			.sendMessage(embed);
	}

	public void run(SlashCommandCreateEvent event)
	{
		String prefix = BotMainCore.prefix;
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Help for untis commands")
			.addField(prefix + "addUntisReminder [RoleID]", "Set current channel as untis reminder channel")
			.addField("/untis-next", "Get next lesson for today")
			.addField("/untis-timetable", "See our timetabel for this week")
			.setThumbnail("http://lh3.googleusercontent.com/6lUhld8gFhB0_b-lpce_crw-gdH70lDnXot5ckVmOFMh91jag56whanU-Q30nLt68sr5=w300");
		event.getSlashCommandInteraction()
			.createImmediateResponder()
			.addEmbed(embed)
			.respond();
	}
}
