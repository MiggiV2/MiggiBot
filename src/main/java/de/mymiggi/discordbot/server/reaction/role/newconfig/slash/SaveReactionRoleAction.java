package de.mymiggi.discordbot.server.reaction.role.newconfig.slash;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.server.reaction.role.ReactionRoleSync;
import de.mymiggi.discordbot.tools.database.util.ReactionRoleSetting;

public class SaveReactionRoleAction
{
	private EmbedBuilder responseEmbed = getPrivateEmbed();
	private InteractionOriginalResponseUpdater responseMessage;
	private String messageLink;
	private String emoji;
	private Server server;
	private Role role;
	private SlashCommandInteraction interaction;

	public void run(SlashCommandCreateEvent event)
	{
		this.interaction = event.getSlashCommandInteraction();
		interaction.getFirstOptionRoleValue().ifPresent(role -> {
			interaction.getSecondOptionStringValue().ifPresent(messageLink -> {
				interaction.getServer().ifPresent(server -> {
					responseEmbed = messageLink.startsWith("https://discord.com/channels/")
						? getMessageNotFoundEmbed()
						: getNotURLEmbed();
					if (messageLink.startsWith("https://discord.com/channels/"))
					{
						this.messageLink = messageLink;
						this.server = server;
						this.role = role;
						startListening();
					}
				});
			});
		});
		interaction.createImmediateResponder()
			.addEmbed(responseEmbed)
			.respond()
			.thenAccept(message -> {
				this.responseMessage = message;
			});
	}

	private void startListening()
	{
		BotMainCore.api.getMessageByLink(messageLink).ifPresent(messageComp -> {
			responseEmbed = getStartReactionEmbed();
			messageComp.thenAccept(message -> {
				message.addReactionAddListener(reactionEvent -> {
					if (emoji == null)
					{
						saveRole(message, reactionEvent);
					}
				}).removeAfter(5, TimeUnit.MINUTES);
			});
		});
	}

	private void saveRole(Message message, ReactionAddEvent reactionEvent)
	{
		this.emoji = reactionEvent.getEmoji().isUnicodeEmoji()
			? reactionEvent.getEmoji().asUnicodeEmoji().get()
			: reactionEvent.getEmoji().asCustomEmoji().get().getIdAsString();
		ReactionRoleSetting roleSetting = new ReactionRoleSetting()
			.setMessageLink(messageLink)
			.setReaction(emoji)
			.setServerID(server.getId())
			.setRoleID(role.getIdAsString());
		responseMessage.removeAllEmbeds().update();
		responseEmbed = new ReactionRoleSync().save(roleSetting)
			? getDoneEmbed()
			: getFailedEmbed();
		message.addReaction(emoji).join();
		responseMessage.addEmbed(responseEmbed).update();
		BotMainCore.getReactionRole().syncHashMap();
	}

	private EmbedBuilder getDoneEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Successfully added your message!")
			.setFooter("Btw, thx for using my bot! <3 Miggi")
			.setColor(Color.GREEN);
	}

	private EmbedBuilder getFailedEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Something went wrong!")
			.setFooter("I'm so sry. I will fix this! Contact me -> Miggi#9895")
			.setColor(Color.RED);
	}

	private EmbedBuilder getPrivateEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Something went wrong!")
			.setFooter("You cant use this command here!")
			.setColor(Color.RED);
	}

	private EmbedBuilder getMessageNotFoundEmbed()
	{
		return new EmbedBuilder()
			.setTitle("I can't find your message!")
			.setColor(Color.RED);
	}

	private EmbedBuilder getNotURLEmbed()
	{
		return new EmbedBuilder()
			.setTitle("Your message link have to start with 'https://discord.com/channels/' Try again")
			.setFooter("Right click -> Copy Message Link")
			.setColor(Color.RED);
	}

	private EmbedBuilder getStartReactionEmbed()
	{
		return new EmbedBuilder().setTitle("Now react to your message!");
	}
}
