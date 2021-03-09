package de.mymiggi.discordbot.music.youtube.core.helpers;

import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendPlayingEmbed;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendPlayingMessage;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendQueueEmbedCoreAction;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendSearchingEmbed;
import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.music.youtube.util.QueryResponse;

public class StartPlayingAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer, QueryResponse queryResponse, boolean toAdd, boolean queryIsPlayist, boolean toPushInQueue, boolean suppressEmbeds)
	{
		String EmbedLink = "";
		if (!suppressEmbeds)
		{
			EmbedLink = new SendSearchingEmbed().run(event.getChannel());
		}

		ServerPlayer player = serverPlayer.get(event.getServer().get());
		String messageBeginning;
		TextChannel textChannel = event.getChannel().asTextChannel().get();
		if (toAdd)
		{
			messageBeginning = "Added ";
			player.addToQueue(queryResponse.getUrl(), queryIsPlayist, toPushInQueue);
		}
		else
		{
			messageBeginning = "Playing ";
			player.run(event, queryResponse.getUrl(), queryIsPlayist);
		}
		if (queryResponse.getUrl().contains("youtube.com/playlist?list="))
		{
			queryIsPlayist = true;
		}
		if (queryIsPlayist)
		{
			new SendQueueEmbedCoreAction().run(event, true, serverPlayer);
		}
		if (!suppressEmbeds)
		{
			if (queryResponse.isWasURL())
			{
				new SendPlayingEmbed().run(player, textChannel, messageBeginning, toAdd, EmbedLink, queryIsPlayist);
			}
			else
			{
				new SendPlayingMessage().run(player, textChannel, messageBeginning, toAdd, EmbedLink, queryIsPlayist);
			}
		}
		event.addReactionsToMessage(Emojis.ARROW_FORWARD.getEmoji()).exceptionally(ExceptionLogger.get());
	}
}
