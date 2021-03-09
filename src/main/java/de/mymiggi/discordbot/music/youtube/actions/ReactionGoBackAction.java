package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.PreviousSongAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionGoBackAction extends AbstractYTAction
{
	private AudioResource audioResource;

	public ReactionGoBackAction(Queue queue, AudioResource audioResource)
	{
		super(Emojis.REWIND, queue);
		this.audioResource = audioResource;
	}

	@Override
	public void run()
	{
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.REWIND.getEmoji());

		new PreviousSongAction().run(false, queue, audioResource);
		// new ReactionEmbedSkipAction().run(queue, reactionEvent);
	}
}
