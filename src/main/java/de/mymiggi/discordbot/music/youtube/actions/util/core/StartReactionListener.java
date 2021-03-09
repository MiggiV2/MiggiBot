package de.mymiggi.discordbot.music.youtube.actions.util.core;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.Message;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;
import de.mymiggi.discordbot.music.youtube.handler.OwnReactionEventHandler;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class StartReactionListener
{
	public void run(Queue queue, List<AbstractYTAction> actions)
	{
		try
		{
			Message message = BotMainCore.api.getMessageByLink(queue.getLastQueueEmbedLink()).get().get();
			for (AbstractYTAction oneAction : actions)
			{
				message.addReaction(oneAction.getEmoji());
			}
			message.addReactionAddListener(reactionEvent -> new OwnReactionEventHandler().run(reactionEvent, queue, actions)).removeAfter(60, TimeUnit.MINUTES);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
