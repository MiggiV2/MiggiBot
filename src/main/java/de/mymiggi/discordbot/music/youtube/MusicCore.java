package de.mymiggi.discordbot.music.youtube;

import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.music.fresh.PlayFreshPlayListAction;
import de.mymiggi.discordbot.music.youtube.core.actions.ClearCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.LoopCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.MoveVoiceChannelCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.PauseCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.PlayCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.PlayMPlaylistCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.PlaySharedMPlaylistCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.ResumeCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.ShuffleCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.SkipCoreAction;
import de.mymiggi.discordbot.music.youtube.core.actions.StopCoreAction;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendQueueEmbedCoreAction;

public class MusicCore
{
	private Map<Server, ServerPlayer> serverPlayer = new HashMap<Server, ServerPlayer>();

	public void play(MessageCreateEvent event, String[] context, boolean queryIsPlayist, boolean toPushInQueue, boolean suppressEmbeds)
	{
		new PlayCoreAction().run(event, context, serverPlayer, queryIsPlayist, toPushInQueue, suppressEmbeds);
	}

	public void skip(MessageCreateEvent event, String[] context)
	{
		new SkipCoreAction().run(event, context, serverPlayer);
	}

	public void resume(MessageCreateEvent event)
	{
		new ResumeCoreAction().run(event, serverPlayer);
	}

	public void pause(MessageCreateEvent event)
	{
		new PauseCoreAction().run(event, serverPlayer);
	}

	public void stop(MessageCreateEvent event)
	{
		new StopCoreAction().run(event, serverPlayer);
	}

	public void queue(MessageCreateEvent event, boolean noCheckNeeded)
	{
		new SendQueueEmbedCoreAction().run(event, noCheckNeeded, serverPlayer);
	}

	public void shuffle(MessageCreateEvent event)
	{
		new ShuffleCoreAction().run(event, serverPlayer);
	}

	public void clear(MessageCreateEvent event)
	{
		new ClearCoreAction().run(event, serverPlayer);
	}

	public void loop(MessageCreateEvent event)
	{
		new LoopCoreAction().run(event, serverPlayer);
	}

	public void playMemberPlayList(MessageCreateEvent event)
	{
		new PlayMPlaylistCoreAction().fromEventUser(event, serverPlayer);
	}

	public void playFreshPlayList(MessageCreateEvent event)
	{
		new PlayFreshPlayListAction().run(event, serverPlayer);
	}

	public void playSharedPlayList(MessageCreateEvent event, String[] context)
	{
		new PlaySharedMPlaylistCoreAction().run(event, serverPlayer, context);
	}

	public void moveToVoiceChannel(MessageCreateEvent event)
	{
		new MoveVoiceChannelCoreAction().run(event, serverPlayer);
	}
}
