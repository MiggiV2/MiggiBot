package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.ShuffleAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.CustomReactionEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionShuffelAction extends AbstractYTAction
{
	public ReactionShuffelAction(Queue queue)
	{
		super(Emojis.TWISTED_RIGHTWARDS_ARROWS, queue);
	}

	@Override
	public void run()
	{
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.TWISTED_RIGHTWARDS_ARROWS.getEmoji());

		new ShuffleAction().run(queue);
		new CustomReactionEmbedAction().run(queue, reactionEvent, "shuffel");
	}

}
