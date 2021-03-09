package de.mymiggi.discordbot.music.youtube.actions.util.embed;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.youtube.actions.util.helpers.CurrentTrackAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.server.r6.matchmaker.handler.ReactionHandler;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class PlayingNextEmbedAction
{
	private static Logger logger = LoggerFactory.getLogger(ReactionHandler.class.getSimpleName());

	public void run(Queue queue, AudioResource audioResource)
	{
		String currentTrack;
		try
		{
			AudioTrack track = new CurrentTrackAction().get(queue, audioResource);
			currentTrack = track.getInfo().title;
		}
		catch (Exception e1)
		{
			logger.error("Cant find the song!", e1);
			currentTrack = "Sorry. Song not found";
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Playing next song! " + currentTrack);
		try
		{
			String embedLink = queue.getTextChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(embedLink, queue.getTextChannel());
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
