package de.mymiggi.discordbot.server.reaction.role.newconfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ListenForEmoji
{
	private boolean wasSuccessful = false;

	public void run(MessageCreateEvent messageEvent, Message message, String embedLink)
	{
		message.addReactionAddListener(reactionEvent -> {
			if (!wasSuccessful)
			{
				try
				{
					String emojiStrOrID;
					if (reactionEvent.getEmoji().isUnicodeEmoji())
					{
						emojiStrOrID = reactionEvent.getEmoji().asUnicodeEmoji().get();
					}
					else
					{
						emojiStrOrID = reactionEvent.getEmoji().asCustomEmoji().get().getIdAsString();
					}
					MessageCoolDown.del(embedLink, messageEvent.getChannel(), 2);
					EmbedBuilder embed = new EmbedBuilder().setTitle("Now, pls enter your role. Like '@User'");
					String embedLink2 = messageEvent.getChannel().sendMessage(embed).get().getLink().toString();
					new ListenForRole().run(messageEvent, message, emojiStrOrID, embedLink2);
					wasSuccessful = true;
				}
				catch (AssertionError | InterruptedException | ExecutionException e)
				{
					e.printStackTrace();
				}
			}
		}).removeAfter(5, TimeUnit.MINUTES);
	}
}
