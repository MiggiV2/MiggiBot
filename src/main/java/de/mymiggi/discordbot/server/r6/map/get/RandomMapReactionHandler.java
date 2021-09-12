package de.mymiggi.discordbot.server.r6.map.get;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.interaction.MessageComponentInteraction;

import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class RandomMapReactionHandler
{
	public void run(MessageComponentCreateEvent event, boolean isRanedMap, Message mapEmbed, List<R6Map> mapList)
	{
		MessageComponentInteraction interaction = event.getMessageComponentInteraction();
		String customId = interaction.getCustomId();
		switch (customId)
		{
			case "update":
				interaction.getMessage().ifPresent(message -> editEmbed(isRanedMap, message, mapList));
				interaction
					.createImmediateResponder()
					.setContent("Searching new map...")
					.respond()
					.thenAccept(message -> new RemoveResponseAction().run(message, 2));
				break;
			case "remove":
				interaction.getMessage().ifPresent(Message::delete);
				break;
		}
	}

	public void run(ReactionAddEvent reactionAddEvent, boolean isRanedMap, Message mapEmbed, List<R6Map> mapList)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("✅"))
			{
				if (mapEmbed.canYouDelete())
				{
					mapEmbed.delete();
				}
				reactionAddEvent.removeAllReactionsFromMessage();
				reactionAddEvent.addReactionsToMessage("🦾");
			}
			else if (reactionAddEvent.getEmoji().equalsEmoji("🔄"))
			{
				Thread thread = new Thread()
				{
					@Override
					public void run()
					{
						try
						{
							editEmbed(reactionAddEvent, isRanedMap, mapEmbed, mapList);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				};
				thread.start();
			}
			else
			{
				reactionAddEvent.removeReactionByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
			}
		}
	}

	private void editEmbed(boolean isRanedMap, Message mapEmbed, List<R6Map> mapList)
	{
		Thread thread = new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep(1000);
					if (isRanedMap)
					{
						mapEmbed.edit(new MapEmbed(mapList).buildRandomRankedMap());
					}
					else
					{
						mapEmbed.edit(new MapEmbed(mapList).buildRandomMap());
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	private void editEmbed(ReactionAddEvent reactionAddEvent, boolean isRanedMap, Message mapEmbed, List<R6Map> mapList) throws InterruptedException
	{
		reactionAddEvent.getChannel().type();
		Thread.sleep(1500);
		try
		{
			if (isRanedMap)
			{
				mapEmbed.edit(new MapEmbed(mapList).buildRandomRankedMap()).get();
			}
			else
			{
				mapEmbed.edit(new MapEmbed(mapList).buildRandomMap()).get();
			}
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
		reactionAddEvent.getChannel().sendMessage("https://tenor.com/view/jim-carrey-yes-sir-you-got-it-you-rock-yes-boss-gif-15459239").thenAccept(message -> {
			MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 3);
		});
		reactionAddEvent.removeReactionByEmojiFromMessage(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
	}
}
