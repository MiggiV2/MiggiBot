package de.mymiggi.discordbot.server.r6.matchmaker.handler;

import java.util.Map;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.server.r6.matchmaker.DiscordMatchMakerBeginner;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class MatchMessageReactionHandler
{
	public void run(ReactionAddEvent reactionAddEvent, Map<User, Boolean> vcUserIngoreMap, DiscordMatchMakerBeginner matchMakerBeginner)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("❌"))
			{
				matchMakerBeginner.deleteEmbeds();
				reactionAddEvent.getMessage().get().removeAllReactions();
				reactionAddEvent.getMessage().get().addReaction("👍");
			}
			else
			{
				if (reactionAddEvent.getEmoji().equalsEmoji("⚔️"))
				{
					reactionAddEvent.getChannel().type();
					try
					{
						Thread.sleep(2500);
					}
					catch (InterruptedException e)
					{
					}
					matchMakerBeginner.run(vcUserIngoreMap, reactionAddEvent.getServer().get(), reactionAddEvent.getChannel());
					reactionAddEvent.getChannel().sendMessage("https://tenor.com/view/dog-cat-hug-gif-13391634")
						.thenAccept(message -> {
							MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 3);
						});
				}
				reactionAddEvent.getMessage().get()
					.removeReactionsByEmoji(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
			}
		}
	}
}
