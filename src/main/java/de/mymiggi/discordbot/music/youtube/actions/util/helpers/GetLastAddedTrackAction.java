package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.youtube.util.Queue;

public class GetLastAddedTrackAction
{
	public AudioTrack run(Queue queue) throws Exception
	{
		boolean currentTrackNull = true;
		int counter = 0;
		while (currentTrackNull)
		{
			counter++;
			if (queue.getLastAddedTrack() == null)
			{
				try
				{
					Thread.sleep(100);
				}
				catch (Exception e)
				{
				}
			}
			else
			{
				currentTrackNull = false;
			}
			if (queue.isSearchingFailed())
			{
				throw new Exception("Song not found!");
			}
			if (counter == 100)
			{
				throw new Exception("Time out!");
			}
		}
		return queue.getLastAddedTrack();
	}
}
