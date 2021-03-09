package de.mymiggi.discordbot.server.r6.map.create.handler;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

public class MapImageURLHandler extends AbstractHandler
{
	@Override
	public void handle(MessageCreateEvent messageEvent)
	{
		getMapToAdd().setImageURL(messageEvent.getMessageContent());
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle(getMapToAdd().getName())
			.setDescription(":thumbsdown: -> is not in ranked pool\r\n:thumbsup: -> is in ranked pool")
			.setImage(getMapToAdd().getImageURL())
			.setColor(Color.GREEN);
		messageEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(embedMessage -> {
				embedMessage.addReaction("👎");
				embedMessage.addReaction("👍");
				embedMessage.addReactionAddListener(reactionAddEvent -> {
					new IsRankedPoolReactionHandler().run(reactionAddEvent, getMapToAdd(), embedMessage);
				});
			});
	}
}
