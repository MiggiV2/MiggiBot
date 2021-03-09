package de.mymiggi.discordbot.server.reaction.role.newconfig;

import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.reaction.role.ReactionRoleSync;
import de.mymiggi.discordbot.server.reaction.role.embed.DoneEmbedAction;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class ListenForRole
{
	private boolean wasSuccessful = false;

	public void run(MessageCreateEvent messageEvent, Message message, String emoji, String embedLink)
	{
		ServerTextChannel channel = message.getServerTextChannel().get();
		channel.addMessageCreateListener(newMessage -> {
			if (!newMessage.getMessage().getMentionedRoles().isEmpty() && !wasSuccessful)
			{
				Role role = newMessage.getMessage().getMentionedRoles().get(0);
				String roleID = role.getIdAsString();
				String messageLink = message.getLink().toString();

				long serverID = message.getServer().get().getId();
				new ReactionRoleSync().saveObjInDB(emoji, roleID, messageLink, serverID);
				new DoneEmbedAction().run(newMessage, emoji);

				MessageCoolDown.del(embedLink, messageEvent.getChannel(), 2);
				MessageCoolDown.del(newMessage.getMessageLink().toString(), messageEvent.getChannel(), 2);
				BotMainCore.getReactionRole().syncHashMap();

				if (emoji.length() > 15)
				{
					KnownCustomEmoji customEmojio = BotMainCore.api.getCustomEmojiById(emoji).get();
					message.addReaction(customEmojio);
				}
				else
				{
					message.addReaction(emoji);
				}
				wasSuccessful = true;
			}
		}).removeAfter(5, TimeUnit.MINUTES);
	}
}
