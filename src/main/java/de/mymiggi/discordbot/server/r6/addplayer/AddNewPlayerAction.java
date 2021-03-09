package de.mymiggi.discordbot.server.r6.addplayer;

import java.util.List;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.r6.addplayer.embed.BadArgumentEmbed;
import de.mymiggi.discordbot.server.r6.addplayer.embed.NotSameUserEmbed;
import de.mymiggi.discordbot.tools.database.util.RainbowSixPlayer;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class AddNewPlayerAction
{
	private static Logger logger = LoggerFactory.getLogger(AddNewPlayerAction.class.getSimpleName());
	private boolean isDone = false;

	public void run(MessageCreateEvent event, String[] context, User authorized, List<RainbowSixPlayer> savingList)
	{
		if (context.length != 2)
		{
			event.getChannel().sendMessage(new BadArgumentEmbed().build());
		}
		else if (event.getMessageAuthor().asUser().get().getId() != authorized.getId())
		{
			User eventUser = event.getMessageAuthor().asUser().get();
			event.getChannel().sendMessage(new NotSameUserEmbed().build(eventUser, authorized));
		}
		else
		{
			try
			{
				BotMainCore.api.getUserById(context[0]).thenAccept(userToAdd -> {
					RainbowSixPlayer playerToAdd = new RainbowSixPlayer();
					playerToAdd.setUserID(userToAdd.getId());
					playerToAdd.setServerID(event.getServer().get().getId());
					playerToAdd.setSkillIndex(Integer.parseInt(context[1]));
					playerToAdd.setName(userToAdd.getName());
					savingList.add(playerToAdd);
					event.addReactionsToMessage("👍");
					isDone = true;
				});
			}
			catch (Exception e)
			{
				logger.error("Cant build & save new R6player", e);
			}
			if (!isDone)
			{
				event.addReactionsToMessage("👎");
			}
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 6);
		}
	}
}
