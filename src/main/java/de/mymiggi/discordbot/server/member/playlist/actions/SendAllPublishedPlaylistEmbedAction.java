package de.mymiggi.discordbot.server.member.playlist.actions;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.AllPublishedPlaylistEmbed;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;

public class SendAllPublishedPlaylistEmbedAction
{
	public void run(MessageCreateEvent event, LastEmbedMaps lastEmbedMaps, MemberPlaylistManager memberPlaylistManager)
	{
		if (event.getMessage().getMentionedUsers().size() == 1)
		{
			User user = event.getMessage().getMentionedUsers().get(0);
			EmbedBuilder embed = new AllPublishedPlaylistEmbed().build(user, memberPlaylistManager);
			event.addReactionsToMessage("🧐");
			try
			{
				String lastEmbedURL = event.getChannel().sendMessage(embed).get().getLink().toString();
				lastEmbedMaps.getLastPublishedPlaylistEmbedMap().put(user, lastEmbedURL);
			}
			catch (AssertionError | InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Bad argument!")
				.setColor(Color.RED)
				.setDescription(BotMainCore.prefix + "check @USER");
		}
	}

	public void run(SlashCommandCreateEvent event, LastEmbedMaps lastEmbedMaps, MemberPlaylistManager memberPlaylistManager)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		User user = interaction.getFirstOptionUserValue().orElse(null);
		EmbedBuilder embed = new AllPublishedPlaylistEmbed().build(user, memberPlaylistManager);
		interaction.respondLater();
		if (user == null)
		{
			interaction.createFollowupMessageBuilder().setContent("User can't be null! (wtf)").send();
		}
		else
		{
			try
			{
				String lastEmbedURL = interaction.createFollowupMessageBuilder()
					.setContent(user.getName() + "'s playlists:")
					.addEmbed(embed)
					.send()
					.get()
					.getLink()
					.toString();
				lastEmbedMaps.getLastPublishedPlaylistEmbedMap().put(user, lastEmbedURL);
			}
			catch (AssertionError | InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}
	}
}
