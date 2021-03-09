package de.mymiggi.discordbot.music.youtube.actions;

import java.util.List;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.embed.CustomReactionEmbedAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.SendQueueEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReationNewQueueEmbedAction extends AbstractYTAction
{
	private List<AbstractYTAction> actions;

	public ReationNewQueueEmbedAction(Queue queue, List<AbstractYTAction> actions)
	{
		super(Emojis.PENCIL, queue);
		this.actions = actions;
	}

	@Override
	public void run()
	{
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.PENCIL.getEmoji());

		new SendQueueEmbedAction().run(queue, actions);
		new CustomReactionEmbedAction().run(queue, reactionEvent, "show");
	}
}
