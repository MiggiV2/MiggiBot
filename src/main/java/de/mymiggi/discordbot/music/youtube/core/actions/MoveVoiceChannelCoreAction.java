package de.mymiggi.discordbot.music.youtube.core.actions;

import java.util.Map;

import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.music.youtube.ServerPlayer;
import de.mymiggi.discordbot.music.youtube.core.embeds.SendErrorEmbedAction;

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
							interaction.createImmediateResponder().setContent("Have fun ;D").respond();
						});
						if (!interaction.getUser().getConnectedVoiceChannel(server).isPresent())
						{
							interaction.createImmediateResponder().setContent("Please join a voice channel first!").respond();
						}
					}
					else
					{
						interaction.createImmediateResponder()
							.setContent(String.format("Only %s can move me to your channel!", player.getUserWhoStartedQueue().getName()))
							.respond();
					}
				}
				else
				{
					interaction.createImmediateResponder().setContent("Please add songs first!").respond();
				}
			}
		});
	}
}
