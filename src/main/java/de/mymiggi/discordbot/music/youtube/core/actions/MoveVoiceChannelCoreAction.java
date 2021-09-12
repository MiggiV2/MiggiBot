package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;
import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class MoveVoiceChannelCoreAction
{
	public void run(MessageCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		event.getServer().ifPresent(server -> {
			if (serverPlayer.containsKey(server))
			{
				ServerPlayer player = serverPlayer.get(server);
				if (player.getUserWhoStartedQueue() != null)
				{
					if (player.getUserWhoStartedQueue().getId() == event.getMessageAuthor().getId() || event.getMessageAuthor().isBotOwner())
					{
						event.getMessageAuthor().getConnectedVoiceChannel().ifPresent(vc -> {
							vc.asServerVoiceChannel().ifPresent(serverVc -> {
								event.addReactionsToMessage("👍");
								player.moveToVoiceChannel(serverVc);
							});
						});
						if (!event.getMessageAuthor().getConnectedVoiceChannel().isPresent())
						{
							new SendErrorEmbedAction().run(event, "Please join a voice channel first!");
							event.addReactionsToMessage("👎");
						}
					}
					else
					{
						new SendErrorEmbedAction().run(event, String.format("Only %s can move me to your channel!", player.getUserWhoStartedQueue().getName()));
						event.addReactionsToMessage("👎");
					}
				}
				else
				{
					new SendErrorEmbedAction().run(event, "Please add songs first!");
					event.addReactionsToMessage("👎");
				}
			}
		});
	}

	public void run(SlashCommandCreateEvent event, Map<Server, ServerPlayer> serverPlayer)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.getServer().ifPresent(server -> {
			if (serverPlayer.containsKey(server))
			{
				ServerPlayer player = serverPlayer.get(server);
				if (player.getUserWhoStartedQueue() != null)
				{
					if (player.getUserWhoStartedQueue().getId() == interaction.getUser().getId() || interaction.getUser().isBotOwner())
					{
						interaction.getUser().getConnectedVoiceChannel(server).ifPresent(vc -> {
							player.moveToVoiceChannel(vc);
							interaction.createImmediateResponder()
								.setContent("Hallo? Where am I? Anyway, nice be be here!")
								.respond()
								.thenAccept(message -> new RemoveResponseAction().run(message, 10));
						});
						if (!interaction.getUser().getConnectedVoiceChannel(server).isPresent())
						{
							interaction.createImmediateResponder()
								.setContent("Please join a voice channel first!")
								.respond()
								.thenAccept(message -> new RemoveResponseAction().run(message, 5));
						}
					}
					else
					{
						interaction.createImmediateResponder()
							.setContent(String.format("Only %s can move me to your channel!", player.getUserWhoStartedQueue().getName()))
							.respond()
							.thenAccept(message -> new RemoveResponseAction().run(message, 5));
					}
				}
				else
				{
					interaction.createImmediateResponder()
						.setContent("Please add songs first!")
						.respond()
						.thenAccept(message -> new RemoveResponseAction().run(message, 5));
				}
			}
		});
	}
}
