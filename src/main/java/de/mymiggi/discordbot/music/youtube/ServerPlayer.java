package de.mymiggi.discordbot.music.youtube;

import java.util.List;

import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import de.mymiggi.discordbot.music.fresh.entitys.FreshSong;
import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;
import de.mymiggi.discordbot.music.youtube.actions.util.RegisterAllActions;
import de.mymiggi.discordbot.music.youtube.actions.util.core.LoadCostumPlaylistAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.LoopAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.MoveVoiceChannelAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.NextAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.PauseAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.ResumeAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.ShuffleAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.StartPlayingAction;
import de.mymiggi.discordbot.music.youtube.actions.util.core.StopAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.PlayingNextEmbedAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.SendQueueEmbedAction;
import de.mymiggi.discordbot.music.youtube.actions.util.embed.UpdateQueueEmbedAction;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.AddSongToQueueAction;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.CurrentTrackAction;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.GetAddedSongsSizeAction;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.GetLastAddedTrackAction;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.IsAllowedCheck;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.QueueStrAction;
import de.mymiggi.discordbot.music.youtube.handler.InstantHandler;
import de.mymiggi.discordbot.music.youtube.util.AudioResource;
import de.mymiggi.discordbot.music.youtube.util.Queue;
import de.mymiggi.discordbot.tools.database.util.NewMemberPlaylistSong;

public class ServerPlayer
{
	private InstantHandler instantHandler;
	private List<AbstractYTAction> actions;
	private Queue queue;
	private AudioResource audioResource;

	public void run(MessageCreateEvent event, String searchQuery, boolean queryIsPlayist)
	{
		initializeLocalVariable(event);
		new StartPlayingAction().run(queue, audioResource, instantHandler, event, searchQuery, queryIsPlayist);
	}

	public void run(MessageCreateEvent event, List<NewMemberPlaylistSong> playlist, boolean queryIsPlayist)
	{
		initializeLocalVariable(event);
		new StartPlayingAction().run(queue, audioResource, instantHandler, event, playlist, queryIsPlayist);
	}

	public void runFreshPlaylist(MessageCreateEvent event, List<FreshSong> playlist, boolean queryIsPlayist)
	{
		initializeLocalVariable(event);
		new StartPlayingAction().runFreshPlaylist(queue, audioResource, instantHandler, event, playlist, queryIsPlayist);
	}

	public void next(int skipTo, boolean sendEmbed)
	{
		new NextAction().run(skipTo, sendEmbed, queue, audioResource);
	}

	public void pause()
	{
		new PauseAction().run(queue, audioResource);
	}

	public void resume()
	{
		new ResumeAction().run(queue, audioResource);
	}

	public void loadMemberPlaylist(List<NewMemberPlaylistSong> playlist)
	{
		new LoadCostumPlaylistAction().run(queue, playlist);
	}

	public void loadFreshPlaylist(List<FreshSong> playlist)
	{
		new LoadCostumPlaylistAction().runFreshPlaylist(queue, playlist);
	}

	public AudioTrack getLastAddedTrack() throws Exception
	{
		return new GetLastAddedTrackAction().run(queue);
	}

	public AudioTrack getCurrentTrack() throws Exception
	{
		return new CurrentTrackAction().get(queue, audioResource);
	}

	public void addToQueue(String url, boolean queryIsPlayist, boolean toPush)
	{
		new AddSongToQueueAction().run(queue, audioResource, url, queryIsPlayist, toPush);
	}

	public String getQueueStr()
	{
		return new QueueStrAction().get(queue);
	}

	public void sendPlayingNext()
	{
		new PlayingNextEmbedAction().run(queue, audioResource);
	}

	public void shuffle()
	{
		new ShuffleAction().run(queue);
	}

	public void stop()
	{
		new StopAction().run(queue, audioResource);
	}

	public void loop()
	{
		new LoopAction().run(queue, audioResource);
	}

	public void sendQueueEmbed()
	{
		new SendQueueEmbedAction().run(queue, actions);
	}

	public int getAddedSongsSize()
	{
		return new GetAddedSongsSizeAction().run(queue);
	}

	public void updateQueueEmbed()
	{
		new UpdateQueueEmbedAction().run(queue);
	}

	public boolean isLooping()
	{
		return queue.isLooping();
	}

	public boolean isAllowed(User user)
	{
		return new IsAllowedCheck().run(queue, user);
	}

	public ServerVoiceChannel getConnectedChannel()
	{
		return queue.getVoicChannel();
	}

	public void moveToVoiceChannel(ServerVoiceChannel channelToMove)
	{
		new MoveVoiceChannelAction().run(channelToMove, audioResource, queue);
	}

	public User getUserWhoStartedQueue()
	{
		return queue.getUserWhoStartedQueue();
	}

	private void initializeLocalVariable(MessageCreateEvent event)
	{
		queue = new Queue();
		audioResource = new AudioResource();
		instantHandler = new InstantHandler(queue, audioResource);
		queue.setVoicChannel(event.getMessageAuthor().getConnectedVoiceChannel().get());
		queue.setUserWhoStartedQueue(event.getMessageAuthor().asUser().get());
		actions = new RegisterAllActions().run(queue, audioResource);
	}
}
