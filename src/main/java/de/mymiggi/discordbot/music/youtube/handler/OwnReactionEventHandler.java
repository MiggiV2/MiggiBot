package de.mymiggi.discordbot.music.youtube.handler;

import java.util.List;

import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.music.youtube.actions.AbstractYTAction;
import de.mymiggi.discordbot.music.youtube.actions.util.GetActionByEmoji;
import de.mymiggi.discordbot.music.youtube.actions.util.helpers.IsAllowedCheck;
import de.mymiggi.discordbot.music.youtube.state.StateMaschine;
import de.mymiggi.discordbot.music.youtube.state.Status;
import de.mymiggi.discordbot.music.youtube.util.Queue;

public class OwnReactionEventHandler
{
	private static final Logger logger = LoggerFactory.getLogger(OwnReactionEventHandler.class.getSimpleName());

	public void run(ReactionAddEvent reactionEvent, Queue queue, List<AbstractYTAction> actions)
	{
		try
		{
			if (!reactionEvent.getUser().get().isYourself())
			{
				User user = reactionEvent.getUser().get();

				if (!new IsAllowedCheck().run(queue, user))
				{
					Emoji notAllowdOne = reactionEvent.getEmoji();
					reactionEvent.getMessage().get().removeReactionsByEmoji(user, notAllowdOne);
					throw new Exception("User is not in this VoicChannel!");
				}
				String reactionEmjoi = reactionEvent.getReaction().get().getEmoji().asUnicodeEmoji().get();

				new GetActionByEmoji()
					.get(reactionEmjoi, reactionEvent, actions)
					.run();
				try
				{
					Status status = new StateMaschine().getStatusByEmoji(reactionEmjoi);
					queue.setPlayingStatus(status);
				}
				catch (Exception e)
				{
					if (!e.getMessage().equals("Emoji not in status List!"))
					{
						logger.warn("Error", e);
					}
				}
			}
		}
		catch (Exception e)
		{
			if (!e.getMessage().equals("Emoji not in Emojis.java") && !e.getMessage().equals("User is not in this VoicChannel!"))
			{
				logger.warn("Error", e);
			}
		}
	}
}
