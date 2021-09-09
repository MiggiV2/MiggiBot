package de.mymiggi.discordbot.music.youtube.core.helpers;

import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
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

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer, QueryResponse queryResponse, boolean toAdd, boolean queryIsPlayist, boolean toPushInQueue, boolean suppressEmbeds)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getChannel().ifPresent(channel -> {
			interaction.getServer().ifPresent(server -> {
				boolean isPlayist = queryIsPlayist;
				String EmbedLink = "";
				if (!suppressEmbeds)
				{
					EmbedLink = new SendSearchingEmbed().run(channel);
				}
				ServerPlayer player = serverPlayer.get(server);
				String messageBeginning;
				if (toAdd)
				{
					messageBeginning = "Added ";
					player.addToQueue(queryResponse.getUrl(), isPlayist, toPushInQueue);
				}
				else
				{
					messageBeginning = "Playing ";
					player.run(event, queryResponse.getUrl(), isPlayist);
				}
				if (queryResponse.getUrl().contains("youtube.com/playlist?list="))
				{
					isPlayist = true;
				}
				if (isPlayist)
				{
					new SendQueueEmbedCoreAction().run(event, true, serverPlayer);
				}
				if (!suppressEmbeds)
				{
					if (queryResponse.isWasURL())
					{
						new SendPlayingEmbed().run(player, channel, messageBeginning, toAdd, EmbedLink, isPlayist);
					}
					else
					{
						new SendPlayingMessage().run(player, channel, messageBeginning, toAdd, EmbedLink, isPlayist);
					}
				}
				interaction.createImmediateResponder()
					.setContent("Have fun ;D")
					.respond();
			});
		});
		new DMCheckAction().run(interaction);
	}
}
