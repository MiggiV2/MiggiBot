package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.NextAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.ReactionEmbedSkipAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionSkipAction extends AbstractYTAction
{
	private AudioResource audioResource;

	public ReactionSkipAction(Queue queue, AudioResource audioResource)
	{
		super(Emojis.FAST_FORWARD, queue);
		this.audioResource = audioResource;
	}

	@Override
	public void run()
	{
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.FAST_FORWARD.getEmoji());

		new NextAction().run(1, false, queue, audioResource);
		new ReactionEmbedSkipAction().run(queue, reactionEvent);
	}
}
