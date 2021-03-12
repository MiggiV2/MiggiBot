package de.mymiggi.discordbot.server.r6.map.update;

import java.util.List;

import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.server.r6.map.update.actions.AbstractUpdateAction;
import de.mymiggi.discordbot.server.r6.map.update.actions.CloseEmbedAction;
import de.mymiggi.discordbot.server.r6.map.update.actions.DeleteAction;
import de.mymiggi.discordbot.server.r6.map.update.actions.SaveAction;
import de.mymiggi.discordbot.server.r6.map.update.embeds.ChangeSelectionEmbed;
import de.mymiggi.discordbot.tools.database.util.R6Map;

public class ChangeSelectionHandler
{
	private String originalMapName;

	public ChangeSelectionHandler(String originalMapName)
	{
		this.originalMapName = originalMapName;
	}

	public void run(ReactionAddEvent reactionAddEvent, R6Map map)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("✅"))
			{
				new SaveAction().run(map, originalMapName, reactionAddEvent.getChannel());
				originalMapName = map.getName();
				reactionAddEvent.getMessage()
					.ifPresent(message -> {
						message.edit(new ChangeSelectionEmbed().build(map));
					});
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji("❌"))
			{
				new CloseEmbedAction().run(reactionAddEvent.getMessage().get());
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji("👋"))
			{
				new CloseEmbedAction().run(reactionAddEvent.getMessage().get());
				new DeleteAction().run(reactionAddEvent.getChannel(), map);
			}
			else
			{
				try
				{
					List<AbstractUpdateAction> actions = new BuildActionList().build();
					new GetUpdateActionByEmoji()
						.run(actions, reactionAddEvent.getEmoji())
						.run(map, reactionAddEvent.getChannel());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			reactionAddEvent.removeReactionsByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
		}
	}
}
