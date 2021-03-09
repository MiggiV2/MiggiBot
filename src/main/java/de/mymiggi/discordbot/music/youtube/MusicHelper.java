package de.mymiggi.discordbot.music.youtube;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class MusicHelper
{
	private Message lastEmbed;
	private Logger logger = LoggerFactory.getLogger("MusicHelper");

	public void send(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Help for music commands")
			.setThumbnail(event.getApi().getYourself().getAvatar())
			.setDescription("8 important commands")
			.addField(String.format("%splay " + Emojis.ARROW_FORWARD.getEmoji(), BotMainCore.prefix), "Play song title or Youtube link.")
			.addField(String.format("%spause " + Emojis.PAUSE_BUTTON.getEmoji(), BotMainCore.prefix), "Pause current song.")
			.addField(String.format("%sskip " + Emojis.FAST_FORWARD.getEmoji(), BotMainCore.prefix), "Skip current song. You can also say how many songs to skip!")
			.addField(String.format("%sshuffle " + Emojis.TWISTED_RIGHTWARDS_ARROWS.getEmoji(), BotMainCore.prefix), "Mix the current playlist.")
			.addField(String.format("%sstop " + Emojis.WAVE.getEmoji(), BotMainCore.prefix), "Stop the current song.")
			.addField(String.format("%sloop " + Emojis.LOOP_BUTTON.getEmoji(), BotMainCore.prefix), "Loop the current queue.")
			.addField(String.format("%squeue " + Emojis.PENCIL.getEmoji(), BotMainCore.prefix), "Show the current queue.")
			.addField(String.format("%sparty", BotMainCore.prefix), "Play your own create playlist! **" + BotMainCore.prefix + "helpPlaylist**")
			.setFooter("Miggi <3")
			.setColor(Color.GRAY);
		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 0);
		if (lastEmbed != null)
		{
			MessageCoolDown.del(lastEmbed.getLink().toString(), lastEmbed.getChannel(), 2);
		}
		try
		{
			lastEmbed = event.getChannel().sendMessage(embed).get();
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		logger.info(event.getMessageAuthor().getName() + " used command!");
	}
}
