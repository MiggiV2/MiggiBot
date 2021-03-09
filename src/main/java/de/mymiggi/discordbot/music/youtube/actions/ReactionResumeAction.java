package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.ResumeAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.CustomReactionEmbedAction;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionResumeAction extends AbstractYTAction
{
	private AudioResource audioResource;

	public ReactionResumeAction(Queue queue, AudioResource audioResource)
	{
		super(Emojis.ARROW_FORWARD, queue);
		this.audioResource = audioResource;
	}

	@Override
	public void run()
	{
		if (queue.getPlayingStatus() == Status.PAUSED)
		{
			new ResumeAction().run(queue, audioResource);
			new CustomReactionEmbedAction().run(queue, reactionEvent, "resume");
		}
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.ARROW_FORWARD.getEmoji());
	}
}
