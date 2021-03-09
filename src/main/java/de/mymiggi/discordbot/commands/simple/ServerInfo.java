package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;
import java.time.Instant;
import java.util.Date;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerInfo
{
	private Logger logger = LoggerFactory.getLogger("ServerInfo");

	public void get(MessageCreateEvent event)
	{
		logger.info(event.getMessageAuthor().getName() + " used command!");
		Server server = event.getServer().get();
		TextChannel channel = event.getChannel().asTextChannel().get();

		Instant timeStamp = server.getCreationTimestamp();
		Date myDate = Date.from(timeStamp);

		User owner = server.getOwner().get();
		Icon icon = server.getIcon().get();

		int userNr = server.getMemberCount();
		int textChannels = server.getVoiceChannels().size();
		int voicChanels = server.getTextChannels().size();

		EmbedBuilder e = new EmbedBuilder()
			.setTitle("Server info of " + server.getName())
			.setDescription("Short infos")
			.addField("Owner", owner.getName())
			.addField("Created: ", myDate.toString())
			.addField("Mebers: ", String.valueOf(userNr))
			.addInlineField("TextChannles: ", String.valueOf(textChannels))
			.addInlineField("VoicChannles: ", String.valueOf(voicChanels))
			.setThumbnail(icon)
			.setColor(Color.BLUE);

		channel.sendMessage(e);
	}
}
