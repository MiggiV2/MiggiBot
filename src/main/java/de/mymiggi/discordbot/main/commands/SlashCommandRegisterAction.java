package de.mymiggi.discordbot.main.commands;

import java.util.Arrays;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOptionBuilder;
import org.javacord.api.interaction.SlashCommandOptionType;

/**
 * You have to update your Commands manually!
 */
public class SlashCommandRegisterAction
{
	public void run(DiscordApi api)
	{
		// long yourDevServer = 743800306827001958L;
		// api.getServerById(yourDevServer).ifPresent(server -> {
		// api.bulkOverwriteServerSlashCommands(server, Arrays.asList())
		// .join();
		// });

		api.bulkOverwriteGlobalSlashCommands(Arrays.asList(
			new SlashCommandBuilder()
				.setName("server")
				.setDescription("Show all infos about this server!"),
			new SlashCommandBuilder()
				.setName("music")
				.setDescription("Show all music commands!"),
			new SlashCommandBuilder()
				.setName("help-admin")
				.setDescription("Show all admin commands!"),
			new SlashCommandBuilder()
				.setName("lookup")
				.setDescription("Check IP-Address or domains!")
				.addOption(new SlashCommandOptionBuilder()
					.setName("ADDRESS")
					.setDescription("What is the target?")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("purge")
				.setDescription("Cleans your channel")
				.addOption(new SlashCommandOptionBuilder()
					.setName("MANY")
					.setDescription("How many message should be purged?")
					.setType(SlashCommandOptionType.INTEGER)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("off")
				.setDescription("Turn off the bot!"),
			new SlashCommandBuilder()
				.setName("play")
				.setDescription("Play music! Add songs to the end of the queue")
				.addOption(new SlashCommandOptionBuilder()
					.setName("SONG")
					.setType(SlashCommandOptionType.STRING)
					.setDescription("Tell me your song!")
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("stop")
				.setDescription("Stop music"),
			new SlashCommandBuilder()
				.setName("skip")
				.setDescription("Skip music")
				.addOption(new SlashCommandOptionBuilder()
					.setName("MANY")
					.setType(SlashCommandOptionType.INTEGER)
					.setDescription("How many songs should be skiped?")
					.setRequired(false)
					.build()),
			new SlashCommandBuilder()
				.setName("pause")
				.setDescription("Pause music"),
			new SlashCommandBuilder()
				.setName("resume")
				.setDescription("Resume music"),
			new SlashCommandBuilder()
				.setName("queue")
				.setDescription("Show the current queue"),
			new SlashCommandBuilder()
				.setName("shuffle")
				.setDescription("Shuffle the current queue"),
			new SlashCommandBuilder()
				.setName("playlist")
				.setDescription("Play a Youtube playlist"),
			new SlashCommandBuilder()
				.setName("push")
				.setDescription("Push a song in the queue. It will be played next")
				.addOption(new SlashCommandOptionBuilder()
					.setName("SONG")
					.setType(SlashCommandOptionType.STRING)
					.setDescription("Tell me your song!")
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("clear")
				.setDescription("Clear the queue and I will leave"),
			new SlashCommandBuilder()
				.setName("loop")
				.setDescription("Loop the current queue"),
			new SlashCommandBuilder()
				.setName("party")
				.setDescription("Play your last selected playlist"),
			new SlashCommandBuilder()
				.setName("sharedparty")
				.setDescription("Play a playlist from another user")
				.addOption(new SlashCommandOptionBuilder()
					.setName("USER")
					.setType(SlashCommandOptionType.USER)
					.setDescription("Who is playlist's owner?")
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("move")
				.setDescription("Move me in another channel"),
			new SlashCommandBuilder()
				.setName("fresh")
				.setDescription("Play the playlist from ANTENNE BAYERN Fresh"),
			new SlashCommandBuilder()
				.setName("help")
				.setDescription("Show all bot commands!"),
			new SlashCommandBuilder()
				.setName("create")
				.setDescription("Create your own music playlist!")
				.addOption(new SlashCommandOptionBuilder()
					.setName("TITLE")
					.setDescription("Enter the name of your playlist!")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("add")
				.setDescription("Add songs to your current playlist!")
				.addOption(new SlashCommandOptionBuilder()
					.setName("SONG")
					.setDescription("Enter the name of your song!")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("join")
				.setDescription("Select one of your playlists, to join")
				.addOption(new SlashCommandOptionBuilder()
					.setName("TITLE")
					.setDescription("Title of your playlist")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("see")
				.setDescription("See all your songs, in your current playlist"),
			new SlashCommandBuilder()
				.setName("delete")
				.setDescription("Delete a song by title")
				.addOption(new SlashCommandOptionBuilder()
					.setName("TITLE")
					.setDescription("Title of your song")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("delete-playlist")
				.setDescription("Delete the entire playlist (current playlist)! Change with /join"),
			new SlashCommandBuilder()
				.setName("playlists")
				.setDescription("List all your playlists"),
			new SlashCommandBuilder()
				.setName("publish")
				.setDescription("Publish your current playlist. Every user see & play 		 published playlists"),
			new SlashCommandBuilder()
				.setName("hide")
				.setDescription("Hide your playlist, from other users [Default]"),
			new SlashCommandBuilder()
				.setName("check")
				.setDescription("See all published playlists from a specific user")
				.addOption(new SlashCommandOptionBuilder()
					.setName("USER")
					.setDescription("Playlist owner")
					.setType(SlashCommandOptionType.USER)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("view")
				.setDescription("See your own playlist"),
			new SlashCommandBuilder()
				.setName("r6-stats")
				.setDescription("Show your Rainbow Six account infos! -> GENERAL, CASUAL, UNRANKED & RANKED")
				.addOption(new SlashCommandOptionBuilder()
					.setName("PLAYER")
					.setDescription("Enter the name of rainbow six player!")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("r6-highlight")
				.setDescription("Show your last Rainbow Six highlight!")
				.addOption(new SlashCommandOptionBuilder()
					.setName("PLAYER")
					.setDescription("Enter the name of rainbow six player!")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("r6-rank")
				.setDescription("Show your current Rainbow Six rank!")
				.addOption(new SlashCommandOptionBuilder()
					.setName("PLAYER")
					.setDescription("Enter the name of rainbow six player!")
					.setType(SlashCommandOptionType.STRING)
					.setRequired(true)
					.build()),
			new SlashCommandBuilder()
				.setName("r6-help")
				.setDescription("Show help for my Rainbow Six commands!"),
			new SlashCommandBuilder()
				.setName("help-playlist")
				.setDescription("Show help for my playlist commands!")))
			.join();
	}
}
