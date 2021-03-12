package de.mymiggi.discordbot.server.r6.matchmaker;

import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.server.r6.matchmaker.embeds.PlayerListEmbed;
import de.mymiggi.discordbot.server.r6.matchmaker.handler.MatchMessageReactionHandler;
import de.mymiggi.discordbot.server.r6.matchmaker.handler.ReactionHandler;

public class DiscordMatchMakerCore
{
	public void run(MessageCreateEvent event)
	{
		DiscordMatchMakerBeginner matchMakerBeginner = new DiscordMatchMakerBeginner();
		ReactionHandler handler = new ReactionHandler();

		User author = event.getMessageAuthor().asUser().get();
		Server authorServer = event.getServer().get();
		Map<User, Boolean> vcUserIngoreMap = new HashMap<User, Boolean>();
		Message eventMessage = event.getMessage();

		eventMessage.addReaction("⚔️");
		eventMessage.addReaction("❌");
		eventMessage.addReactionAddListener(reactionAddEvent -> {
			new MatchMessageReactionHandler().run(reactionAddEvent,
				vcUserIngoreMap, matchMakerBeginner);
		});
		/*
		 * Test USER
		 */
		// BotMainCore.api.getServerById(689382577571627013L).get().getMembers()
		// .stream()
		// .filter(user -> !user.isYourself())
		// .forEach(user -> vcUserIngoreMap.put(user, false));

		author.getConnectedVoiceChannel(authorServer).ifPresent(vc -> {
			vc.getConnectedUsers()
				.stream()
				.filter(user -> !user.isYourself())
				.forEach(user -> vcUserIngoreMap.put(user, false));
		});
		event.getChannel().sendMessage(new PlayerListEmbed().build(vcUserIngoreMap, 0)).thenAccept(message -> {
			message.addReaction("✅");
			if (vcUserIngoreMap.size() > 8)
			{
				message.addReaction("🔼");
				message.addReaction("🔽");
			}
			int limit;
			if (vcUserIngoreMap.size() > 7)
			{
				limit = 7;
			}
			else
			{
				limit = vcUserIngoreMap.size();
			}
			for (int i = 0; i < limit; i++)
			{
				message.addReaction(NumberEmoji.values()[i].getEmoji());
			}
			message.addReactionAddListener(reactionAddEvent -> {
				handler.run(reactionAddEvent, vcUserIngoreMap, matchMakerBeginner);
			});
		});
	}
}
