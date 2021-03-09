package de.mymiggi.discordbot.music.youtube.core.embeds;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class SendPlayingMessage
{
	private static Logger logger = LoggerFactory.getLogger(SendPlayingMessage.class.getSimpleName());

	public void run(ServerPlayer player, TextChannel textChannel, String messageBeginning, boolean toAdd, String EmbedLink, boolean queryIsPlayist)
	{
		AudioTrack track = null;
		try
		{
			if (toAdd)
			{
				track = player.getLastAddedTrack();
			}
			else
			{
				track = player.getCurrentTrack();

			}
		}
		catch (Exception e)
		{
			logger.debug(e.getMessage());
		}
		String messageContent = "I'm sorry. We can't find our song. Try Youtube links or the song title";
		if (queryIsPlayist && track != null)
		{
			messageContent = messageBeginning + player.getAddedSongsSize() + " songs " + "!";
		}
		if (!queryIsPlayist && track != null)
		{
			String title = track.getInfo().title;
			String url = track.getInfo().uri;
			title = title.replaceAll("\\*", "x");
			messageContent = messageBeginning + " song **" + title + "**" + System.lineSeparator() + url;
		}
		if (BotMainCore.api.getMessageByLink(EmbedLink).isPresent())
		{
			try
			{
				BotMainCore.api.getMessageByLink(EmbedLink).get().get().edit(messageContent);
				BotMainCore.api.getMessageByLink(EmbedLink).get().get().removeEmbed();
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
			MessageCoolDown.del(EmbedLink, textChannel);
		}
		player.updateQueueEmbed();
	}
}
