package de.mymiggi.discordbot.music.youtube.core.embeds;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class SendPlayingEmbed
{
	private static Logger logger = LoggerFactory.getLogger(SendPlayingEmbed.class.getSimpleName());

	public void run(ServerPlayer player, TextChannel textChannel, String messageBeginning, boolean toAdd, String EmbedLink, boolean queryIsPlayist)
	{
		String title = null;
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("I'm sorry. We can't find our song. Try Youtube links or the song title");
		if (toAdd)
		{
			try
			{
				title = player.getLastAddedTrack().getInfo().title;
			}
			catch (Exception e)
			{
				logger.debug(e.getMessage());
			}
		}
		else
		{
			try
			{
				title = player.getCurrentTrack().getInfo().title;
			}
			catch (Exception e)
			{
				logger.debug(e.getMessage());
			}
		}
		if (queryIsPlayist && title != null)
		{
			embed.setTitle(messageBeginning + player.getAddedSongsSize() + " songs " + "!");
		}
		if (!queryIsPlayist && title != null)
		{
			embed.setTitle(messageBeginning + " song " + title + "!");
		}
		if (BotMainCore.api.getMessageByLink(EmbedLink).isPresent())
		{
			try
			{
				BotMainCore.api.getMessageByLink(EmbedLink).get().get().edit(embed);
			}
			catch (IllegalArgumentException | InterruptedException | ExecutionException e)
			{
				logger.error("Error", e);
			}
			MessageCoolDown.del(EmbedLink, textChannel);
			player.updateQueueEmbed();
		}
	}
}
