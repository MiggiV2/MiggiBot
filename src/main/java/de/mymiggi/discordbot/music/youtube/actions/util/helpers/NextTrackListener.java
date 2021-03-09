package de.mymiggi.discordbot.music.youtube.actions.util.helpers;

import org.javacord.api.entity.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackState;

import de.mymiggi.discordbot.music.youtube.actions.util.core.NextAction;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class NextTrackListener
{
	private static final Logger logger = LoggerFactory.getLogger(NextTrackListener.class.getSimpleName());
	private Message memeGif;

	public void run(Queue queue, AudioResource audioResource)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				queue.setPlayingStatus(Status.PLAYING);
				int queueSizeLastTime = queue.getSongs().size();
				while (queue.getPlayingStatus() == Status.PLAYING)
				{
					try
					{
						Thread.sleep(2000);
						// Check if new Tack loaded
						if (queue.getCurrentTrack() == null && queueSizeLastTime != queue.getSongs().size() && queue.getPlayingStatus() != Status.PLAYING)
						{
							new NextAction().run(1, true, queue, audioResource);
						}
						else if (queue.getCurrentTrack().getState().equals(AudioTrackState.FINISHED))
						{
							new NextAction().run(1, true, queue, audioResource);
						}
						if (queue.getCurrentTrack() != null && isVibinCatMeme(queue.getCurrentTrack().getInfo().title))
						{
							if (memeGif == null)
							{
								logger.info("Vibing Cat!!!");
								queue.getTextChannel().sendMessage("https://tenor.com/view/cat-drumming-ievan-polkka-vibing-gif-19098972").thenAccept(message -> {
									memeGif = message;
								});
							}
						}
						else
						{
							memeGif.delete();
							memeGif = null;
						}
					}
					catch (Exception e)
					{
						if (memeGif != null)
						{
							memeGif.delete();
							memeGif = null;
						}
					}
				}
			}
		};
		thread.start();
	}

	private boolean isVibinCatMeme(String songTitle)
	{
		return songTitle.toLowerCase().contains("cat") && songTitle.toLowerCase().contains("vibing")
			&& songTitle.toLowerCase().contains("ievan") && songTitle.toLowerCase().contains("polkka");
	}
}
