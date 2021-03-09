package de.mymiggi.discordbot.music.youtube.actions.util.core;

import de.mymiggi.discordbot.music.youtube.actions.util.helpers.NextTrackListener;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class ResumeAction
{

	public void run(Queue queue, AudioResource audioResource)
	{
		audioResource.getPlayer().setPaused(false);
		new NextTrackListener().run(queue, audioResource);
	}
}
