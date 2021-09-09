package de.mymiggi.discordbot.server.member.playlist.actions;

import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.server.member.playlist.actions.helpers.AllPlaylistEmbed;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;

public class SendAllPlaylistsEmbedAction
{
	public void run(MessageCreateEvent event, LastEmbedMaps lastEmbedMaps, MemberPlaylistManager memberPlaylistManager)
	{
		User user = event.getMessageAuthor().asUser().get();
		EmbedBuilder embed = new AllPlaylistEmbed().build(user, memberPlaylistManager);
		event.addReactionsToMessage("🧐");
		try
		{
			String lastEmbedURL = event.getChannel().sendMessage(embed).get().getLink().toString();
			lastEmbedMaps.getLastAllPlaylistEmbedMap().put(user, lastEmbedURL);
		}
		catch (AssertionError | InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	public void run(SlashCommandCreateEvent event, LastEmbedMaps lastEmbedMaps, MemberPlaylistManager memberPlaylistManager)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		User user = interaction.getUser();
		EmbedBuilder embed = new AllPlaylistEmbed().build(user, memberPlaylistManager);
		try
		{
			String lastEmbedURL = interaction.createFollowupMessageBuilder()
				.setContent("Your playlists:")
				.addEmbed(embed)
				.send()
				.get()
				.getLink()
				.toString();
			lastEmbedMaps.getLastAllPlaylistEmbedMap().put(user, lastEmbedURL);
		}
		catch (AssertionError | InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
