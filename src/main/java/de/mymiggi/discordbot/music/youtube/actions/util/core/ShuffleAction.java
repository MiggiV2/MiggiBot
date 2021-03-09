package de.mymiggi.discordbot.music.youtube.actions.util.core;

import java.util.ArrayList;

import de.mymiggi.discordbot.music.youtube.actions.util.embed.UpdateQueueEmbedAction;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.music.youtube.util.Song;

public class ShuffleAction
{
	public void run(Queue queue)
	{
		ArrayList<Song> toShuffleQueue = new ArrayList<Song>();
		ArrayList<Song> shuffledQueue = new ArrayList<Song>();
		for (int i = 0; i < queue.getCurrentTrackPosition() + 1; i++)
		{
			Song temp = queue.getSongs().get(i);
			shuffledQueue.add(temp);
		}
		int queueStartPostion = queue.getCurrentTrackPosition() + 1;
		for (int i = queueStartPostion; i < queue.getSongs().size(); i++)
		{
			Song temp = queue.getSongs().get(i);
			toShuffleQueue.add(temp);
		}
		int toShuffelQueueSize = toShuffleQueue.size();
		for (int i = 0; i < toShuffelQueueSize; i++)
		{
			int randomIndex = (int)(Math.random() * toShuffleQueue.size());
			Song temp = toShuffleQueue.get(randomIndex);
			shuffledQueue.add(temp);
			toShuffleQueue.remove(randomIndex);
		}
		queue.setSongs(shuffledQueue);
		new UpdateQueueEmbedAction().run(queue);
	}
}
