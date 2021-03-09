package de.mymiggi.discordbot.music.youtube.actions.util.core;

import java.util.List;

import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.music.youtube.util.Song;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class LoadCostumPlaylistAction
{
	public void run(Queue queue, List<NewMemberPlaylistSong> playlist)
	{
		for (NewMemberPlaylistSong temp : playlist)
		{
			Song song = new Song();
			song.setSearchQurry(temp.getSongURL());
			song.setTitel(temp.getTitle());
			queue.getSongs().add(song);
		}
	}
}
