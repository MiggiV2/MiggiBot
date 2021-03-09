package de.mymiggi.discordbot.music.youtube.actions.util.embed;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class CustomReactionEmbedAction
{
	public void run(Queue queue, ReactionAddEvent event, String status)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle(String.format("Please %s current song!", status))
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
