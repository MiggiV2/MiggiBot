package de.mymiggi.discordbot.music.youtube.actions.util.embed;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ReactionEmbedSkipAction
{
	public void run(Queue queue, ReactionAddEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Please skip the song [" + queue.getCurrentTrack().getInfo().title + "]")
			.setAuthor(event.getUser().get());
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
