package de.mymiggi.discordbot.music.youtube.actions.util.embed;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.StartReactionListener;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.QueueStrAction;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class SendQueueEmbedAction
{
	public void run(Queue queue, List<AbstractYTAction> actions)
	{
		EmbedBuilder embed = new EmbedBuilder();

		String server = queue.getTextChannel().asServerTextChannel().get().getServer().getName();
		String queueStr = new QueueStrAction().get(queue);

		embed.setTitle(server + " music queue").addField("Current track " + (queue.getCurrentTrackPosition() + 1), queueStr);

		if (queue.getLastQueueEmbedLink() != null)
		{
			MessageCoolDown.del(queue.getLastQueueEmbedLink(), queue.getTextChannel(), 2);
		}
		try
		{
			queue.setLastQueueEmbedLink(queue.getTextChannel().sendMessage(embed).get().getLink().toString());
			new StartReactionListener().run(queue, actions);
			new AfterOneHouerEmbedAgain().run(queue.getLastQueueEmbedLink(), queue, actions);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
