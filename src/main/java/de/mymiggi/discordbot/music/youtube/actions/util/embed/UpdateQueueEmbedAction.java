package de.mymiggi.discordbot.music.youtube.actions.util.embed;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.music.youtube.actions.util.helpers.QueueStrAction;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class UpdateQueueEmbedAction
{
	public void run(Queue queue)
	{
		if (queue.getLastQueueEmbedLink() != null)
		{
			EmbedBuilder embed = new EmbedBuilder();

			String server = queue.getTextChannel().asServerTextChannel().get().getServer().getName();
			String queueStr = new QueueStrAction().get(queue);

			embed.setTitle(server + " music queue")
				.addField("Current track " + (queue.getCurrentTrackPosition() + 1), queueStr);
			queue.getTextChannel()
				.getApi()
				.getMessageByLink(queue.getLastQueueEmbedLink())
				.ifPresent(optMessage -> {
					optMessage.thenAccept(message -> {
						message.edit(embed);
					});
				});

		}
	}
}
