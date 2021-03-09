package de.mymiggi.discordbot.server.r6.matchmaker.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.server.r6.matchmaker.DiscordMatchMakerBeginner;
import de.mymiggi.discordbot.server.r6.matchmaker.embeds.PlayerListEmbed;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ReactionHandler
{
	private static Logger logger = LoggerFactory.getLogger(ReactionHandler.class.getSimpleName());
	private int currentPage = 0;

	public void run(ReactionAddEvent reactionAddEvent, Map<User, Boolean> vcUserIngoreMap, DiscordMatchMakerBeginner matchMakerBeginner)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			double endStart = Math.ceil(vcUserIngoreMap.size() / 7);
			if (reactionAddEvent.getEmoji().equalsEmoji("✅"))
			{
				reactionAddEvent.getMessage().ifPresent(message -> {
					MessageCoolDown.del(message.getLink().toString(), reactionAddEvent.getChannel(), 1);
				});
				matchMakerBeginner.run(vcUserIngoreMap, reactionAddEvent.getServer().get(), reactionAddEvent.getChannel());
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji("🔼"))
			{
				if (currentPage != 0)
				{
					currentPage--;
				}
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji("🔽"))
			{
				if (currentPage < endStart)
				{
					currentPage++;
				}
			}
			else
			{
				try
				{
					updatePlayerMap(reactionAddEvent, vcUserIngoreMap);
				}
				catch (Exception e)
				{
					if (e.getMessage() != null)
					{
						logger.error(e.getMessage(), e);
					}
					else
					{
						logger.error("GetNumberByReaction failed!", e);
					}
				}
			}
			reactionAddEvent.removeReactionByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
			reactionAddEvent.getMessage().get().edit(new PlayerListEmbed().build(vcUserIngoreMap, currentPage));
		}
	}

	private void updatePlayerMap(ReactionAddEvent reactionAddEvent, Map<User, Boolean> vcUserIngoreMap) throws Exception
	{
		int number = new GetNumberByReaction().get(reactionAddEvent.getEmoji().asUnicodeEmoji().get());
		number = number + ((currentPage) * 7);
		List<User> userList = convertMapToUserList(vcUserIngoreMap);
		List<Boolean> shouldIgnoreList = convertMapToShouldIgnoreList(vcUserIngoreMap);
		if (userList.size() > number - 1)
		{
			boolean shouldIgnore = shouldIgnoreList.get(number - 1);
			User user = userList.get(number - 1);
			vcUserIngoreMap.put(user, !shouldIgnore);
		}
	}

	private List<User> convertMapToUserList(Map<User, Boolean> vcUserIngoreMap)
	{
		List<User> userList = new ArrayList<User>();
		vcUserIngoreMap.forEach((user, shouldIgnore) -> {
			userList.add(user);
		});
		return userList;
	}

	private List<Boolean> convertMapToShouldIgnoreList(Map<User, Boolean> vcUserIngoreMap)
	{
		List<Boolean> shouldIgnoreList = new ArrayList<Boolean>();
		vcUserIngoreMap.forEach((user, shouldIgnore) -> {
			shouldIgnoreList.add(shouldIgnore);
		});
		return shouldIgnoreList;
	}
}
