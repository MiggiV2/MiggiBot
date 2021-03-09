package de.mymiggi.discordbot.server.r6.map.update.actions;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;

import de.mymiggi.discordbot.server.r6.map.update.embeds.EnterUpdateEmbed;
import de.mymiggi.discordbot.server.r6.map.update.listener.NameUpdateListener;
import de.mymiggi.discordbot.server.r6.matchmaker.NumberEmoji;
import de.mymiggi.discordbot.tools.database.util.R6Map;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class UpdateNameAction extends AbstractUpdateAction
{
	private ListenerManager<MessageCreateListener> listener;

	public UpdateNameAction()
	{
		super(NumberEmoji.ONE.getEmoji());
	}

	@Override
	public void run(R6Map map, TextChannel channel)
	{
		channel
			.sendMessage(new EnterUpdateEmbed().build("map name"))
			.thenAccept(message -> {
				MessageCoolDown.del(message.getLink().toString(), channel, 6);
			});
		listener = channel.addMessageCreateListener(event -> {
			new NameUpdateListener().run(event, map, listener);
		});
	}
}
