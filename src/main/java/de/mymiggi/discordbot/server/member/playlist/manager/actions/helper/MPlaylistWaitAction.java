package de.mymiggi.discordbot.server.member.playlist.manager.actions.helper;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public class MPlaylistWaitAction
{
	public void run(List<AudioTrack> playListResult) throws Exception
	{
		int counter = 0;
		while (playListResult == null)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			counter++;
			if (counter == 15)
			{
				throw new Exception("Timeout!");
			}
		}
	}
}
