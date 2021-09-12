package de.mymiggi.discordbot.server.r6.map.get;

import java.util.List;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class GetRandomMapAction
{
	private Message mapEmbed;
	private static Logger logger = LoggerFactory.getLogger(GetRandomMapAction.class.getSimpleName());

	public void run(Message eventMessage, TextChannel channel, String[] context, List<R6Map> mapList)
	{
		EmbedBuilder embed;
		boolean isRanedMap;
		if (context.length == 2 && context[1].equalsIgnoreCase("all"))
		{
			embed = new MapEmbed(mapList).buildRandomMap();
			isRanedMap = false;
		}
		else
		{
			embed = new MapEmbed(mapList).buildRandomRankedMap();
			isRanedMap = true;
		}
		channel.sendMessage(embed).thenAccept(message -> {
			mapEmbed = message;
		});
		eventMessage.addReaction("🔄");
		eventMessage.addReaction("✅");
		eventMessage.addReactionAddListener(reactionAddEvent -> {
			new RandomMapReactionHandler().run(reactionAddEvent, isRanedMap, mapEmbed, mapList);
		});
	}

	public void run(SlashCommandCreateEvent event, List<R6Map> mapList)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		EmbedBuilder embed;
		boolean isRanedMap;
		if (interaction.getFirstOptionIntValue().orElse(0) == 0)
		{
			embed = new MapEmbed(mapList).buildRandomRankedMap();
			isRanedMap = true;
		}
		else
		{
			embed = new MapEmbed(mapList).buildRandomMap();
			isRanedMap = false;
		}
		interaction.createImmediateResponder()
			.addEmbed(embed)
			.addComponents(
				ActionRow.of(
					Button.success("update", "New Map", Emojis.LOOP_BUTTON.getEmoji()),
					Button.danger("remove", "Remove", Emojis.WAVE.getEmoji())))
			.respond();
		event.getApi().addMessageComponentCreateListener(eventButton -> {
			new RandomMapReactionHandler().run(eventButton, isRanedMap, mapEmbed, mapList);
		});
		logger.info("@" + interaction.getUser().getName() + " used my Command!");
	}
}
