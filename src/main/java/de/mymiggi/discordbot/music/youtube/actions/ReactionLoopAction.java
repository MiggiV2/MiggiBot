package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.music.youtube.actions.util.core.LoopAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.CustomReactionEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ReactionLoopAction extends AbstractYTAction
{
	private AudioResource audioResource;
	
	public ReactionLoopAction(Queue queue, AudioResource audioResource)
	{
		super(Emojis.LOOP_BUTTON, queue);
		this.audioResource = audioResource;
	}

	@Override
	public void run()
	{
		User user = reactionEvent.getUser().get();
		reactionEvent.getMessage().get().removeReactionsByEmoji(user, Emojis.LOOP_BUTTON.getEmoji());
		if(queue.isLooping())
		{
			new CustomReactionEmbedAction().run(queue, reactionEvent, "stop looping");
		}else 
		{
			new CustomReactionEmbedAction().run(queue, reactionEvent, "loop");
		}
		new LoopAction().run(queue, audioResource);
	}
}
