package de.mymiggi.discordbot.music.youtube.core.actions;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

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
