package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import de.mymiggi.discordbot.music.youtube.util.Queue;

public class QueueStrAction
{
	public String get(Queue queue)
	{
		if (queue.getSongs().size() == 0)
		{
			return "loading ...";
		}
		if (queue.getSongs().size() > 10 && queue.getCurrentTrackPosition() > 5 && queue.getSongs().size() > queue.getCurrentTrackPosition() + 4)
		{
			return getSongTitel(queue.getCurrentTrackPosition() - 5, queue.getCurrentTrackPosition() + 5, queue);
		}
		if (queue.getSongs().size() > 10 && queue.getCurrentTrackPosition() > 5)
		{
			return getSongTitel(queue.getSongs().size() - 11, queue.getSongs().size(), queue);
		}
		else
		{
			int limit = 10;
			if (queue.getSongs().size() < 10)
			{
				limit = queue.getSongs().size();
			}
			return getSongTitel(0, limit, queue);
		}
	}

	private String getSongTitel(int forLoopStart, int forLoopEnd, Queue queue)
	{
		String queueStr = "";

		for (int i = forLoopStart; i < forLoopEnd; i++)
		{
			String title;
			if (queue.getSongs().get(i).getAudioSource() != null)
			{
				title = queue.getSongs().get(i).getAudioSource().getInfo().title;
			}
			else
			{
				title = queue.getSongs().get(i).getTitel();
			}
			if (i == queue.getCurrentTrackPosition())
			{
				title = title.replaceAll("\\*", "x");
				title = "**<" + title + ">**";
			}
			queueStr += title + "\r\n";
		}
		queueStr += String.format("*Song %s - %s from %s*", forLoopStart + 1, forLoopEnd, queue.getSongs().size());
		return queueStr;
	}
}
