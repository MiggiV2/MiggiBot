package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.youtube.actions.util.core.StopAction;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class CurrentTrackAction
{
	public AudioTrack get(Queue queue, AudioResource audioResource) throws Exception
	{
		boolean currentTrackNull = true;
		int counter = 0;
		while (currentTrackNull)
		{
			counter++;
			if (queue.getCurrentTrack() == null)
			{
				Thread.sleep(100);
			}
			else
			{
				currentTrackNull = false;
			}
			if (queue.isSearchingFailed())
			{
				new StopAction().run(queue, audioResource);
				throw new Exception("Song not found!");
			}
			if (counter == 100)
			{
				throw new Exception("Time out!");
			}
		}
		return queue.getCurrentTrack();
	}
}
