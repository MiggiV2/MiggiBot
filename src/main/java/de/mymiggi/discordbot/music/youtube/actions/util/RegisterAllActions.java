package de.mymiggi.discordbot.music.youtube.actions.util;

import java.util.ArrayList;
import java.util.List;

import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionGoBackAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionLoopAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionPauseAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionResumeAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionShuffelAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionSkipAction;
import de.mymiggi.discordbot.music.youtube.actions.ReactionStopAction;
import de.mymiggi.discordbot.music.youtube.actions.ReationNewQueueEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class RegisterAllActions
{
	/*
	 * Maybe bag in line 25
	 */

	public List<AbstractYTAction> run(Queue queue, AudioResource audioResource)
	{
		List<AbstractYTAction> list = new ArrayList<AbstractYTAction>();
		list.add(new ReactionGoBackAction(queue, audioResource));
		list.add(new ReactionResumeAction(queue, audioResource));
		list.add(new ReactionPauseAction(queue, audioResource));
		list.add(new ReactionSkipAction(queue, audioResource));
		list.add(new ReactionShuffelAction(queue));
		list.add(new ReactionLoopAction(queue, audioResource));
		list.add(new ReationNewQueueEmbedAction(queue, list));
		list.add(new ReactionStopAction(queue, audioResource));
		return list;
	}
}
