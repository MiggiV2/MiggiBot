package de.mymiggi.discordbot.server.r6.stats.actions.helpers;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.discordbot.server.r6.stats.handler.R6PlatformReactionHandler;
import de.mymiggi.discordbot.tools.database.util.R6AndDiscordUser;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class AskForPlatformAction
{
	private ListenerManager<ReactionAddListener> listener;

	public void run(SlashCommandInteraction interaction, String username, WrapperManager wrapperManager, AbstractUpdateR6MessageAction abstractAction)
	{
		R6PlatformReactionHandler handler = new R6PlatformReactionHandler();
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Are you pc/xbox/playstation gamer?")
			.setDescription("[1] PC \r\n[2] XBOX \r\n[3] PLAYSTAION")
			.setColor(Color.ORANGE);
		interaction
			.createFollowupMessageBuilder()
			.addEmbed(embed)
			.send()
			.thenAccept(message -> {
				message.addReactions(
					NumberEmoji.ONE.getEmoji(),
					NumberEmoji.THREE.getEmoji(),
					NumberEmoji.TOW.getEmoji(),
					Emojis.NO_ENTRY_SIGN.getEmoji());
				listener = message.addReactionAddListener(reactionAddEvent -> {
					handler.setShouldRemoveAllEmojis(!abstractAction.isNeedRankedRegion());
					handler.run(reactionAddEvent, username, wrapperManager, abstractAction);
					if (handler.getPlayerPlatfrom() != null)
					{
						if (abstractAction.isNeedRankedRegion())
						{
							new AskForRegionActinon().run(message, username, wrapperManager, handler.getPlayerPlatfrom(), abstractAction);
						}
						else
						{
							abstractAction.run(username, wrapperManager, handler.getPlayerPlatfrom(), message);
						}
						listener.remove();
					}
				});
			});
	}

	public void skipAsking(SlashCommandInteraction interaction, R6AndDiscordUser user, WrapperManager wrapperManager, AbstractUpdateR6MessageAction abstractAction)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Loading...");
		interaction
			.createFollowupMessageBuilder()
			.addEmbed(embed)
			.send()
			.thenAccept(message -> {
				if (abstractAction.isNeedRankedRegion())
				{
					abstractAction.run(user.getR6Name(), wrapperManager, user.getRankedRegion(), user.getPlatform(), message);
				}
				else
				{
					abstractAction.run(user.getR6Name(), wrapperManager, user.getPlatform(), message);
				}
			});
	}
}
