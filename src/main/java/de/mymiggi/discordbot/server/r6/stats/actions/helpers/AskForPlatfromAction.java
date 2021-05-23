package de.mymiggi.discordbot.server.r6.stats.actions.helpers;

import java.awt.Color;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.music.youtube.util.Emojis;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.server.r6.stats.R6StatsReactionHandler;
import de.mymiggi.discordbot.server.r6.stats.actions.AbstractUpdateR6MessageAction;
import de.mymiggi.r6.stats.wrapper.WrapperManager;

public class AskForPlatfromAction
{
	public void run(TextChannel channel, String username, WrapperManager wrapperManager, AbstractUpdateR6MessageAction abstractAction)
	{
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
				message.addReactionAddListener(reactionAddEvent -> {
					new R6StatsReactionHandler().run(reactionAddEvent, username, wrapperManager, abstractAction);
				});
			});
	}
}
