package de.mymiggi.discordbot.server.r6.map.create;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.server.r6.map.create.handler.StartNewMapHandler;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class NewR6MapCore
{
	@Deprecated
	public void run(MessageCreateEvent messageEvent)
	{
		new StartNewMapHandler().run(messageEvent, new R6Map(), null);
	}

	public void run(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		String name = interaction.getFirstOptionStringValue().orElse("NO_NAME");
		String imageUrl = interaction.getSecondOptionStringValue().orElse("");
		boolean isRanked = interaction.getThirdOptionIntValue().orElse(0) == 0;
		R6Map map = new R6Map()
			.setName(name)
			.setImageURL(imageUrl)
			.setRankedPool(isRanked);
		new UniversalHibernateClient().save(map);
		interaction.createFollowupMessageBuilder()
			.setContent("Saved! " + Emojis.THUMPS_UP.getEmoji())
			.addEmbed(getMapEmbed(map))
			.send();
	}

	private EmbedBuilder getMapEmbed(R6Map map)
	{
		return new EmbedBuilder()
			.setTitle(map.getName())
			.setImage(map.getImageURL())
			.setDescription("Is in ranked pool: " + map.isRankedPool())
			.setColor(Color.GREEN);
	}
}
