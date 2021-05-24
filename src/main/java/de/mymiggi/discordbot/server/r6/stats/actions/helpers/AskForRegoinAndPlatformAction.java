package de.mymiggi.discordbot.server.r6.stats.actions.helpers;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.discordbot.server.r6.stats.handler.R6PlatformReactionHandler;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class AskForRegoinAndPlatformAction
{
	private ListenerManager<ReactionAddListener> listener;

	public void run(TextChannel channel, String username, WrapperManager wrapperManager, AbstractUpdateR6MessageAction abstractAction)
	{
		R6PlatformReactionHandler handler = new R6PlatformReactionHandler();
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Are you pc/xbox/playstation gamer?")
			.setDescription("[1] PC \r\n[2] XBOX \r\n[3] PLAYSTAION")
			.setColor(Color.ORANGE);
		channel
			.sendMessage(embed)
			.thenAccept(message -> {
				message.addReactions(
					NumberEmoji.ONE.getEmoji(),
					NumberEmoji.THREE.getEmoji(),
					NumberEmoji.TOW.getEmoji(),
					Emojis.NO_ENTRY_SIGN.getEmoji());
				listener = message.addReactionAddListener(reactionAddEvent -> {
					handler.setShouldRemoveAllEmojis(false);
					handler.run(reactionAddEvent, username, wrapperManager, null);
					if (handler.getPlayerPlatfrom() != null)
					{
						listener.remove();
						new AskForRegionActinon().run(message, username, wrapperManager, handler.getPlayerPlatfrom(), abstractAction);
					}
				});
			});
	}
}
