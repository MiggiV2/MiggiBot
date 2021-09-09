package de.mymiggi.discordbot.server.member.playlist.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.music.youtube.core.helpers.DMCheckAction;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.core.embed.PlaylistSongsEmbed;
import de.mymiggi.discordbot.server.member.playlist.core.embed.ReactionEventHandler;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class SendCurrentPlaylistEmbedAction
{
	private List<NewMemberPlaylistSong> currentPlayListSongs;

	public void run(MessageCreateEvent event, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		User user = event.getMessageAuthor().asUser().get();
		event.addReactionsToMessage("👁");
		try
		{
			List<NewMemberPlaylistSong> currentPlayListSongs;
			try
			{
				currentPlayListSongs = memberPlaylistManager.getSongsFromCurrentPlayList(user);
			}
			catch (Exception e)
			{
				currentPlayListSongs = new ArrayList<NewMemberPlaylistSong>();
			}
			String playListName = memberPlaylistManager.getCurrentPlayListName(user);
			EmbedBuilder embed = new PlaylistSongsEmbed().run(user, currentPlayListSongs, playListName, 1);
			ReactionEventHandler listener = new ReactionEventHandler(currentPlayListSongs, playListName);
			Message lastEmbed = event.getChannel().sendMessage(embed).get();
			String lastEmbedURL = lastEmbed.getLink().toString();
			boolean needFastSkip = currentPlayListSongs.size() > 30;
			boolean needSkip = currentPlayListSongs.size() > 10;
			if (needFastSkip)
			{
				lastEmbed.addReaction("⏫");
			}
			if (needSkip)
			{
				lastEmbed.addReaction("🔼");
			}
			lastEmbed.addReaction("❌");
			if (needSkip)
			{
				lastEmbed.addReaction("🔽");
			}
			if (needFastSkip)
			{
				lastEmbed.addReaction("⏬");
			}
			lastEmbed.addReactionAddListener(reactionEvent -> listener.run(reactionEvent));
			lastEmbedMaps.getLastSongsEmbedMap().put(user, lastEmbedURL);
		}
		catch (AssertionError | InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	public void run(SlashCommandCreateEvent event, MemberPlaylistManager memberPlaylistManager, LastEmbedMaps lastEmbedMaps)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		User user = interaction.getUser();
		interaction.respondLater();
		try
		{
			currentPlayListSongs = memberPlaylistManager.getSongsFromCurrentPlayList(user);
		}
		catch (Exception e)
		{
			currentPlayListSongs = new ArrayList<NewMemberPlaylistSong>();
		}
		String playListName = memberPlaylistManager.getCurrentPlayListName(user);
		EmbedBuilder embed = new PlaylistSongsEmbed().run(user, currentPlayListSongs, playListName, 1);
		ReactionEventHandler listener = new ReactionEventHandler(currentPlayListSongs, playListName);
		interaction.getChannel().ifPresent(channel -> {
			channel.sendMessage(embed).thenAccept(lastEmbed -> {
				boolean needFastSkip = currentPlayListSongs.size() > 30;
				boolean needSkip = currentPlayListSongs.size() > 10;
				if (needFastSkip)
				{
					lastEmbed.addReaction("⏫");
				}
				if (needSkip)
				{
					lastEmbed.addReaction("🔼");
				}
				lastEmbed.addReaction("❌");
				if (needSkip)
				{
					lastEmbed.addReaction("🔽");
				}
				if (needFastSkip)
				{
					lastEmbed.addReaction("⏬");
				}
				lastEmbed.addReactionAddListener(reactionEvent -> listener.run(reactionEvent));
				lastEmbedMaps.getLastSongsEmbedMap().put(user, lastEmbed.getLink().toString());
				interaction.createFollowupMessageBuilder().setContent("Have fun ;D").send();
			});
		});
		new DMCheckAction().run(interaction);
	}
}
