package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.PauseAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.CustomReactionEmbedAction;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionPauseAction extends AbstractYTAction
{
	private AudioResource audioResource;

	public ReactionPauseAction(Queue queue, AudioResource audioResource)
	{
		super(Emojis.PAUSE_BUTTON, queue);
		this.audioResource = audioResource;
	}

	@Override
	public void run()
	{
		if (queue.getPlayingStatus() == Status.PLAYING)
		{
			new PauseAction().run(queue, audioResource);
			new CustomReactionEmbedAction().run(queue, reactionEvent, "pause");
		}
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.PAUSE_BUTTON.getEmoji());
	}
}
