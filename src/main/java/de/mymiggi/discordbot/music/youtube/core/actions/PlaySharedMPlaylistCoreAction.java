package de.mymiggi.discordbot.music.youtube.core.actions;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class PlaySharedMPlaylistCoreAction
{
	private Logger logger = LoggerFactory.getLogger(PlaySharedMPlaylistCoreAction.class.getSimpleName());

	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, String[] context)
	{
		if (context.length > 2 && event.getMessage().getMentionedUsers().size() == 1)
		{
			User targetedUser = event.getMessage().getMentionedUsers().get(0);
			String searchQuery = "";
			for (int i = 2; i < context.length - 1; i++)
			{
				if (!context[i].isEmpty())
				{
					searchQuery += context[i] + " ";
				}
			}
			searchQuery += context[context.length - 1];
			try
			{
				List<NewMemberPlaylistSong> currentPlayListSongs = BotMainCore.getMemberPlayListCore().getSharedParty(targetedUser, searchQuery);
				new PlayMPlaylistCoreAction().run(event, serverPlayer, currentPlayListSongs);
			}
			catch (Exception e)
			{
				handelErros(event, e);
			}
		}
	}

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		User targetedUser = interaction.getFirstOptionUserValue().orElse(null);
		String searchQuery = interaction.getSecondOptionStringValue().orElse("NO_PARAMENTER");
		if (targetedUser != null)
		{
			try
			{
				List<NewMemberPlaylistSong> currentPlayListSongs = BotMainCore.getMemberPlayListCore().getSharedParty(targetedUser, searchQuery);
				new PlayMPlaylistCoreAction().run(event, serverPlayer, currentPlayListSongs);
				interaction.createImmediateResponder()
					.setContent("Have fun ;D")
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 5));
			}
			catch (Exception e)
			{
				interaction.createImmediateResponder()
					.setContent("Failed! error:" + e.getClass())
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 5));
			}
		}
		else
		{
			interaction.createImmediateResponder().setContent("User can't be null! (wtf)").respond();
		}
	}

	private void handelErros(MessageCreateEvent event, Exception e)
	{
		try
		{
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle(e.getMessage())
				.setColor(Color.RED);
			String lastJoinEmbed = event.getChannel().sendMessage(embed).get().getLink().toString();
			MessageCoolDown.del(lastJoinEmbed, event.getChannel(), 8);
		}
		catch (AssertionError | InterruptedException | ExecutionException e1)
		{
			logger.warn("Faied to send message in server " + event.getServer().get().getName());
		}
		logger.warn("Failed: " + e.getMessage());
	}
}
