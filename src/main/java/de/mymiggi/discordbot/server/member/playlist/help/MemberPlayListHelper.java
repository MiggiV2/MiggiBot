package de.mymiggi.discordbot.server.member.playlist.help;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

public class MemberPlayListHelper
{
	private int currentPage = 1;
	private PlaylistHelpReactionHandler handler = new PlaylistHelpReactionHandler(currentPage);

	public void run(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getChannel().ifPresent(channel -> {
			channel.sendMessage(new HelpPlaylistEmbedPage1().run())
				.thenAccept(message -> {
					message.addReactions("⬅️", "❌", "➡️");
					message.addReactionAddListener(reactionEvent -> handler.run(reactionEvent)).removeAfter(60, TimeUnit.MINUTES);
					deleteEmbedAfterOneHour(message);
					interaction.createImmediateResponder().setContent("Hope this can help ;D").respond();
				});
		});
		if (!interaction.getChannel().isPresent())
		{
			interaction.createImmediateResponder().setContent("Something went wrong! I can't find the channel to post my message?!").respond();
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
