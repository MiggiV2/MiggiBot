package de.mymiggi.discordbot.server.r6.map.create;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class NotAllowedAction
{
	public void run(ReactionAddEvent reactionAddEvent)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle(reactionAddEvent.getMessageAuthor().get().getName() + " you are not my owner! Sorry")
			.setColor(Color.RED);
		reactionAddEvent.getMessage()
			.get()
			.addReaction("📛");
		reactionAddEvent.getChannel()
			.sendMessage(embed);
	}

	public void run(SlashCommandCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle(event.getSlashCommandInteraction().getUser().getName() + " you are not my owner! Sorry")
			.setColor(Color.RED);
		event.getSlashCommandInteraction()
			.createFollowupMessageBuilder()
			.addEmbed(embed)
			.send();
	}

	public void run(MessageCreateEvent messageEvent)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle(messageEvent.getMessageAuthor().getName() + " you are not my owner! Sorry")
			.setColor(Color.RED);
		messageEvent.getChannel()
			.sendMessage(embed)
			.thenAccept(messageEmbed -> {
				MessageCoolDown.del(messageEmbed.getLink().toString(), messageEmbed.getChannel(), 12);
			});
		messageEvent.getMessage()
			.addReaction("📛");
	}
}
