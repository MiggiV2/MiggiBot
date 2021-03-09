package de.mymiggi.discordbot.server.member.playlist.help;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;

public class MemberPlayListHelper
{
	private int currentPage = 1;
	private PlaylistHelpReactionHandler handler = new PlaylistHelpReactionHandler(currentPage);

	public void run(MessageCreateEvent event)
	{
		try
		{
			Message message = event.getChannel().sendMessage(new HelpPlaylistEmbedPage1().run()).get();
			message.addReaction("⬅️");
			message.addReaction("❌");
			message.addReaction("➡️");
			message.addReactionAddListener(reactionEvent -> handler.run(reactionEvent)).removeAfter(60, TimeUnit.MINUTES);

			deleteEmbedAfterOneHour(message);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	private void deleteEmbedAfterOneHour(Message message)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(60 * 60 * 1000);
					if (message.canYouDelete())
					{
						message.delete().get();
					}
				}
				catch (InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}
