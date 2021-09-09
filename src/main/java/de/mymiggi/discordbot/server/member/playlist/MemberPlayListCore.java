package de.mymiggi.discordbot.server.member.playlist;

import java.util.List;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.server.member.playlist.actions.AddSongToMemberPlaylsitAction;
import de.mymiggi.discordbot.server.member.playlist.actions.CreateNewPlayListInfoAction;
import de.mymiggi.discordbot.server.member.playlist.actions.DeleteEntirePlaylistAction;
import de.mymiggi.discordbot.server.member.playlist.actions.DeleteSongFromPlayListAction;
import de.mymiggi.discordbot.server.member.playlist.actions.GetShardPartyAction;
import de.mymiggi.discordbot.server.member.playlist.actions.JoinMemberPlaylistAction;
import de.mymiggi.discordbot.server.member.playlist.actions.SendAllPlaylistsEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.actions.SendAllPublishedPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.actions.SendCurrentPlaylistEmbedAction;
import de.mymiggi.discordbot.server.member.playlist.actions.UpdatePlaylistPublishStatusAction;
import de.mymiggi.discordbot.server.member.playlist.actions.ViewUsersPlaylistAction;
import de.mymiggi.discordbot.server.member.playlist.actions.helpers.LastEmbedMaps;
import de.mymiggi.discordbot.server.member.playlist.manager.MemberPlaylistManager;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class MemberPlayListCore
{
	private MemberPlaylistManager memberPlaylistManager;
	private LastEmbedMaps lastEmbedMaps;

	public void load()
	{
		memberPlaylistManager = new MemberPlaylistManager();
		lastEmbedMaps = new LastEmbedMaps();
	}

	public int loadedPlaylists()
	{
		return memberPlaylistManager.getTotalPlaylists();
	}

	public void createNewPlayListInfo(MessageCreateEvent event, String[] context)
	{
		new CreateNewPlayListInfoAction().run(event, context, memberPlaylistManager, lastEmbedMaps);
	}

	public void createNewPlayListInfo(SlashCommandCreateEvent event)
	{
		new CreateNewPlayListInfoAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void addSong(MessageCreateEvent event, String[] context)
	{
		new AddSongToMemberPlaylsitAction().run(event, context, memberPlaylistManager, lastEmbedMaps);
	}

	public void addSong(SlashCommandCreateEvent event)
	{
		new AddSongToMemberPlaylsitAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void joinPlaylist(MessageCreateEvent event, String[] context)
	{
		new JoinMemberPlaylistAction().run(event, context, memberPlaylistManager, lastEmbedMaps);
	}

	public void joinPlaylist(SlashCommandCreateEvent event)
	{
		new JoinMemberPlaylistAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void deleteSongFromPlayList(MessageCreateEvent event, String[] context)
	{
		new DeleteSongFromPlayListAction().run(event, context, memberPlaylistManager, lastEmbedMaps);
	}

	public void deleteSongFromPlayList(SlashCommandCreateEvent event)
	{
		new DeleteSongFromPlayListAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void deleteEntirePlayList(MessageCreateEvent event)
	{
		new DeleteEntirePlaylistAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void deleteEntirePlayList(SlashCommandCreateEvent event)
	{
		new DeleteEntirePlaylistAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void sendAllPlayListsEmbed(MessageCreateEvent event)
	{
		new SendAllPlaylistsEmbedAction().run(event, lastEmbedMaps, memberPlaylistManager);
	}

	public void sendAllPlayListsEmbed(SlashCommandCreateEvent event)
	{
		new SendAllPlaylistsEmbedAction().run(event, lastEmbedMaps, memberPlaylistManager);
	}

	public void sendAllPublishedPlayListsEmbed(MessageCreateEvent event)
	{
		new SendAllPublishedPlaylistEmbedAction().run(event, lastEmbedMaps, memberPlaylistManager);
	}

	public void sendAllPublishedPlayListsEmbed(SlashCommandCreateEvent event)
	{
		new SendAllPublishedPlaylistEmbedAction().run(event, lastEmbedMaps, memberPlaylistManager);
	}

	public void sendCurrentPlayList(MessageCreateEvent event)
	{
		new SendCurrentPlaylistEmbedAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public void sendCurrentPlayList(SlashCommandCreateEvent event)
	{
		new SendCurrentPlaylistEmbedAction().run(event, memberPlaylistManager, lastEmbedMaps);
	}

	public List<NewMemberPlaylistSong> getCurrentPlayList(User user) throws Exception
	{
		return memberPlaylistManager.getSongsFromCurrentPlayList(user);
	}

	public void getSharedParty(MessageCreateEvent event, String[] context)
	{
		new GetShardPartyAction().run(event, context, memberPlaylistManager);
	}

	public void getSharedParty(SlashCommandCreateEvent event, String[] context)
	{
		new GetShardPartyAction().run(event, memberPlaylistManager);
	}

	public List<NewMemberPlaylistSong> getSharedParty(User user, String searchQuery) throws Exception
	{
		return memberPlaylistManager.getSongsByPlayListName(user, searchQuery);
	}

	public void updatePlayListPublishStatus(MessageCreateEvent event, boolean publishStatus)
	{
		new UpdatePlaylistPublishStatusAction().run(event, publishStatus, memberPlaylistManager, lastEmbedMaps);
	}

	public void updatePlayListPublishStatus(SlashCommandCreateEvent event, boolean publishStatus)
	{
		new UpdatePlaylistPublishStatusAction().run(event, publishStatus, memberPlaylistManager, lastEmbedMaps);
	}

	public void viewForeignPlaylist(MessageCreateEvent event, String[] context)
	{
		new ViewUsersPlaylistAction().run(memberPlaylistManager, event, context, lastEmbedMaps);
	}

	public void viewForeignPlaylist(SlashCommandCreateEvent event)
	{
		new ViewUsersPlaylistAction().run(memberPlaylistManager, event, lastEmbedMaps);
	}
}
