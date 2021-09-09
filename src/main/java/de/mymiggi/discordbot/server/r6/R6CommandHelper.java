package de.mymiggi.discordbot.server.r6;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;

public class R6CommandHelper
{
	public void run(SlashCommandCreateEvent event)
	{
		String prefix = BotMainCore.prefix;
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Help for all R6 features!")
			.addField("/r6-stats YOUR-NAME", "See your R6 stats!")
			.addField("/r6-highlight YOUR-NAME", "See your R6 last highlight!")
			.addField("/r6-rank YOR-NAME", "Show you current R6 rank")
			.addField(prefix + "randomMap", "Get a random R6 map")
			.addField(prefix + "listMaps", "See all maps in by database")
			.addField(prefix + "match", "Create tow teams with players in your voice channel")
			.addField(prefix + "newPlayer [ADMIN ONLY]", "Register new R6 player from your server for matchmaking")
			.addField(prefix + "updateMaps [BOTOWNER ONLY]", "My onwer can update maps")
			.addField(prefix + "addNewMap [BOTOWNER ONLY]", "My owner can add new maps")
			.setImage("https://cdn.vox-cdn.com/thumbor/BL30YtnaVVLCX97h8tVkJXMHH-M=/0x0:960x540/1200x800/filters:focal(404x194:556x346)/cdn.vox-cdn.com/uploads/chorus_image/image/58883867/header_game_editions_319850.0.jpg");
		event.getSlashCommandInteraction().createImmediateResponder()
			.setContent("Hope this can help you ;D")
			.addEmbed(embed)
			.respond();
	}
}
