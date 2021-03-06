package de.mymiggi.discordbot.server.r6.map.update;

import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.r6.map.update.embeds.ChangeSelectionEmbed;
import de.mymiggi.discordbot.server.r6.map.update.embeds.NeedArgmunetEmbed;
import de.mymiggi.discordbot.server.r6.map.update.embeds.NotAuthorisedToUpdateR6MapEmbed;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class UpdateR6MapAction
{
	private static Logger logger = LoggerFactory.getLogger(UpdateR6MapAction.class.getSimpleName());

	public void run(MessageCreateEvent event, String[] context, List<R6Map> mapList)
	{
		if (event.getMessageAuthor().isBotOwner())
		{
			if (context.length == 2)
			{
				try
				{
					R6Map map = new GetMapByNameAction().run(mapList, context[1]);
					ChangeSelectionHandler changeSelectionHandler = new ChangeSelectionHandler(map.getName());
					event.getChannel()
						.sendMessage(new ChangeSelectionEmbed().build(map))
						.thenAccept(embed -> {
							embed.addReaction(NumberEmoji.ONE.getEmoji());
							embed.addReaction(NumberEmoji.TOW.getEmoji());
							embed.addReaction(NumberEmoji.THREE.getEmoji());
							embed.addReaction("✅");
							embed.addReaction("❌");
							embed.addReaction("👋");
							embed.addReactionAddListener(reactionevent -> {
								changeSelectionHandler.run(reactionevent, map);
							});
						});
					event.getMessage().addReaction("👍");
				}
				catch (Exception e)
				{
					logger.error("Failed to find map!", e);
				}
			}
			else
			{
				event.getChannel().sendMessage(new NeedArgmunetEmbed().build());
			}
		}
		else
		{
			User author = event.getMessageAuthor().asUser().get();
			EmbedBuilder embed = new NotAuthorisedToUpdateR6MapEmbed().build(author);
			event.getChannel()
				.sendMessage(embed)
				.thenAccept(messageEmbed -> {
					MessageCoolDown.del(messageEmbed.getLink().toString(), messageEmbed.getChannel(), 12);
				});
		}
	}

	public void run(SlashCommandCreateEvent event, List<R6Map> mapList)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		if (interaction.getUser().isBotOwner())
		{
			try
			{
				String mapName = interaction.getFirstOptionStringValue().orElse("NO_NAME");
				R6Map map = new GetMapByNameAction().run(mapList, mapName);
				ChangeSelectionHandler changeSelectionHandler = new ChangeSelectionHandler(map.getName());
				interaction.getChannel()
					.ifPresent(channel -> {
						channel.sendMessage(new ChangeSelectionEmbed().build(map))
							.thenAccept(embed -> {
								embed.addReactions(
									NumberEmoji.ONE.getEmoji(),
									NumberEmoji.TOW.getEmoji(),
									NumberEmoji.THREE.getEmoji(),
									"✅", "❌", "👋");
								embed.addReactionAddListener(reactionevent -> {
									changeSelectionHandler.run(reactionevent, map);
								});
							});
					});
				interaction.createImmediateResponder()
					.setContent("Read to update?")
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 5));
			}
			catch (Exception e)
			{
				logger.error("Failed to find map!", e);
				interaction.createImmediateResponder()
					.setContent("Error: " + e.getClass())
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 60));
			}
		}
		else
		{
			EmbedBuilder embed = new NotAuthorisedToUpdateR6MapEmbed().build(interaction.getUser());
			interaction.createImmediateResponder()
				.addEmbed(embed)
				.respond()
				.thenAccept(message -> new RemoveResponseAction().run(message, 30));
		}
	}
}
