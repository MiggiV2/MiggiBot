package de.mymiggi.discordbot.server.r6.map.update.actions;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;

import de.mymiggi.discordbot.tools.database.util.R6Map;

public abstract class AbstractUpdateAction
{
	private String emoji;

	public AbstractUpdateAction(String emoji)
	{
		this.emoji = emoji;
	}

	public abstract void run(R6Map map, TextChannel channel);

	public boolean equalsEmoji(Emoji emoji)
	{
		return emoji.equalsEmoji(this.emoji);
	}
}
