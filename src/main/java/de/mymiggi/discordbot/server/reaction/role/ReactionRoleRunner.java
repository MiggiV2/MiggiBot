package de.mymiggi.discordbot.server.reaction.role;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.database.util.ReactionRoleSetting;

public class ReactionRoleRunner
{
	private Map<Message, List<ReactionRoleSetting>> messagesAndSettings = new HashMap<Message, List<ReactionRoleSetting>>();
	private Logger logger = LoggerFactory.getLogger("ReactionRoleRunner");
	private ReactionRoleSync loader = new ReactionRoleSync();

	public void run()
	{
		syncHashMap();
		BotMainCore.api.addReactionAddListener(event -> {

			event.getMessage().ifPresent(message -> {

				if (messagesAndSettings.containsKey(event.getMessage().get()))
				{
					List<ReactionRoleSetting> configs = messagesAndSettings.get(event.getMessage().get());
					for (ReactionRoleSetting temp : configs)
					{
						if (temp.getReaction().length() > 15)
						{
							KnownCustomEmoji customEmojio = BotMainCore.api.getCustomEmojiById(temp.getReaction()).get();
							if (event.getEmoji().equalsEmoji(customEmojio))
							{
								Role role = BotMainCore.api.getRoleById(temp.getRoleID()).get();
								if (event.getUser().get().getId() != BotMainCore.api.getYourself().getId())
								{
									event.getUser().get().addRole(role);
									logger.info("Added role to @" + event.getUser().get().getName() + " role " + role.getName());
								}
							}
						}
						else
						{
							if (event.getEmoji().equalsEmoji(temp.getReaction()))
							{
								Role role = BotMainCore.api.getRoleById(temp.getRoleID()).get();
								if (event.getUser().get().getId() != BotMainCore.api.getYourself().getId())
								{
									event.getUser().get().addRole(role);
									logger.info("Added role to @" + event.getUser().get().getName() + " role " + role.getName());
								}
							}
						}
					}
				}
			});
		});
		BotMainCore.api.addReactionRemoveListener(event -> {

			event.getMessage().ifPresent(message -> {

				if (messagesAndSettings.containsKey(event.getMessage().get()))
				{
					List<ReactionRoleSetting> configs = messagesAndSettings.get(event.getMessage().get());
					for (ReactionRoleSetting temp : configs)
					{
						if (temp.getReaction().length() > 15)
						{
							KnownCustomEmoji customEmojio = BotMainCore.api.getCustomEmojiById(temp.getReaction()).get();
							if (event.getEmoji().equalsEmoji(customEmojio))
							{
								Role role = BotMainCore.api.getRoleById(temp.getRoleID()).get();
								if (event.getUser().get().getId() != BotMainCore.api.getYourself().getId())
								{
									event.getUser().get().removeRole(role);
									logger.info("Added role to @" + event.getUser().get().getName() + " role " + role.getName());
								}
							}
						}
						else
						{
							if (event.getEmoji().equalsEmoji(temp.getReaction()))
							{
								Role role = BotMainCore.api.getRoleById(temp.getRoleID()).get();
								if (event.getUser().get().getId() != BotMainCore.api.getYourself().getId())
								{
									event.getUser().get().removeRole(role);
									logger.info("Added role to @" + event.getUser().get().getName() + " role " + role.getName());
								}
							}
						}
					}
				}
			});
		});
	}

	public void syncHashMap()
	{
		messagesAndSettings = loader.run();
	}

	public int getListSize()
	{
		return messagesAndSettings.size();
	}
}
