package de.mymiggi.discordbot.music.youtube.actions;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public abstract class AbstractYTAction
{
	private Emojis emoji;
	protected Queue queue;
	protected ReactionAddEvent reactionEvent;

	public AbstractYTAction(Emojis emoji, Queue queue)
	{
		this.emoji = emoji;
		this.setQueue(queue);
	}

	public abstract void run();

	public boolean matchesEmoji(String emojiStr)
	{
		return emoji.getEmoji().equals(emojiStr);
	}

	public void setQueue(Queue queue)
	{
		this.queue = queue;
	}

	public ReactionAddEvent getReactionEvent()
	{
		return reactionEvent;
	}

	public void setReactionEvent(ReactionAddEvent reactionEvent)
	{
		this.reactionEvent = reactionEvent;
	}

	public String getEmoji()
	{
		return emoji.getEmoji();
	}
}
