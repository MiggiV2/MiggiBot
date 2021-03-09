package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.StopAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.CustomReactionEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionStopAction extends AbstractYTAction
{
	private AudioResource audioResource;

	public ReactionStopAction(Queue queue, AudioResource audioResource)
	{
		super(Emojis.WAVE, queue);
		this.audioResource = audioResource;
	}

	@Override
	public void run()
	{
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.WAVE.getEmoji());

		new CustomReactionEmbedAction().run(queue, reactionEvent, "stop");
		new StopAction().run(queue, audioResource);
	}
}
