package de.mymiggi.discordbot.server.member.playlist.actions;

import java.awt.Color;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.UpdateLastAllPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class CreateNewPlayListInfoAction
{
	private static Logger logger = LoggerFactory.getLogger(BotMainCore.class.getSimpleName());

	public void run(MessageCreateEvent event, String[] context, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		EmbedBuilder embed = new EmbedBuilder();
		if (context.length != 1)
		{
			User user = event.getMessageAuthor().asUser().get();
			String searchQuery = "";
			for (int i = 1; i < context.length - 1; i++)
			{
				searchQuery += context[i] + " ";
			}
			searchQuery += context[context.length - 1];
			try
			{
				memberPlaylistManager.createNewPlayList(searchQuery, user);
				event.addReactionsToMessage("👍");
				if (memberPlaylistManager.getCurrentPlayListName(user) != null)
				{
					embed.setTitle("Current playlist " + memberPlaylistManager.getCurrentPlayListName(user))
						.setColor(Color.ORANGE);
				}
				new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
			}
			catch (Exception e)
			{
				if (e.getMessage() != null && e.getMessage().equals("Playlist limit reached!"))
				{
					embed.setTitle("Sorry but, you have reached the playlist limit!")
						.setDescription("Maybe you delete an old playlist?")
						.setColor(Color.ORANGE);
				}
				else
				{
					embed.setTitle("An error has occurred!")
						.setColor(Color.ORANGE);
					if (e.getMessage() != null)
					{
						embed.setDescription(e.getMessage());
					}
				}
				logger.error("Failed to create", e);
				event.addReactionsToMessage("👎");
			}
		}
		else
		{
			embed.setTitle("Please enter a name for your playlist!")
				.setColor(Color.ORANGE);
			event.addReactionsToMessage("👎");
		}
		try
		{
			Message embedMessage = event.getChannel().sendMessage(embed).get();
			MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 10);
		}
		catch (InterruptedException | ExecutionException e)
		{
		}
	}

	public void run(SlashCommandCreateEvent event, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		User user = interaction.getUser();
		String searchQuery = interaction.getFirstOptionStringValue().orElse("NO_PLAYLIST");
		try
		{
			memberPlaylistManager.createNewPlayList(searchQuery, user);
			if (memberPlaylistManager.getCurrentPlayListName(user) != null)
			{
				interaction.createFollowupMessageBuilder()
					.setContent("Current playlist " + memberPlaylistManager.getCurrentPlayListName(user))
					.send();
			}
			new UpdateLastAllPlaylistEmbedAction().run(user, lastEmbedMaps, memberPlaylistManager);
		}
		catch (Exception e)
		{
			if (e.getMessage() != null && e.getMessage().equals("Playlist limit reached!"))
			{
				interaction.createFollowupMessageBuilder().setContent("Sorry but, you have reached the playlist limit!").send();
			}
			else
			{
				interaction.createFollowupMessageBuilder().setContent("An error has occurred! Error:" + e.getClass()).send();
			}
			logger.error("Failed to create!", e);
		}
	}
}
