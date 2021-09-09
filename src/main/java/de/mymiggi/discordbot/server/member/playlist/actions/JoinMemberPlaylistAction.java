package de.mymiggi.discordbot.server.member.playlist.actions;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.member.playlist.ErrorJoinHandlerCore;
import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastAllPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastSongsEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class JoinMemberPlaylistAction
{
	private static Logger logger = LoggerFactory.getLogger(MemberPlayListCore.class.getSimpleName());

	public void run(MessageCreateEvent event, String[] context, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		User user = event.getMessageAuthor().asUser().get();
		String searchQuery = "";
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.ORANGE);
		if (context.length < 2)
		{
			try
			{
				embed.setTitle("Please add a title, to your command!")
					.setColor(Color.BLACK);
				String lastJoinEmbed = event.getChannel().sendMessage(embed).get().getLink().toString();
				MessageCoolDown.del(lastJoinEmbed, event.getChannel(), 12);
			}
			catch (AssertionError | InterruptedException | ExecutionException e)
			{
				logger.error("Error", e);
			}
		}
		else
		{
			for (int i = 1; i < context.length - 1; i++)
			{
				searchQuery += context[i] + " ";
			}
			searchQuery += context[context.length - 1];
			try
			{
				memberPlaylistManager.joinPlayList(user, searchQuery);
				event.addReactionsToMessage("👍");
				embed.setTitle("Joined playlist " + memberPlaylistManager.getCurrentPlayListName(user));
				new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
				new UpdateLastSongsEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
			}
			catch (Exception e)
			{
				if (e.getMessage() != null)
				{
					embed = new ErrorJoinHandlerCore().run(e);
				}
				else
				{
					embed.setTitle(e.getClass().getSimpleName())
						.setColor(Color.RED)
						.setFooter("Error! Please inform Miggi#9895");
					logger.error("Need Help!", e);
				}
			}
			try
			{
				String lastJoinEmbed = event.getChannel().sendMessage(embed).get().getLink().toString();
				MessageCoolDown.del(lastJoinEmbed, event.getChannel(), 12);
			}
			catch (AssertionError | InterruptedException | ExecutionException e)
			{
				logger.warn("Error", e);
			}
		}
	}

	public void run(SlashCommandCreateEvent event, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		User user = interaction.getUser();
		String searchQuery = interaction.getFirstOptionStringValue().orElse("NO_TITLE");
		try
		{
			memberPlaylistManager.joinPlayList(user, searchQuery);
			interaction.createFollowupMessageBuilder()
				.setContent("Joined playlist " + memberPlaylistManager.getCurrentPlayListName(user))
				.send();
			new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
			new UpdateLastSongsEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
		}
		catch (Exception e)
		{
			interaction.createFollowupMessageBuilder()
				.setContent("Failed to join your playlist! Error:" + e.getClass())
				.send();
		}
	}
}
