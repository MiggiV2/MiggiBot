package de.mymiggi.discordbot.server.r6.stats.actions;

import java.awt.Color;
import java.io.IOException;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;
import de.mymiggi.r6.stats.wrapper.PlatfromType;
import de.mymiggi.r6.stats.wrapper.WrapperManager;
import de.mymiggi.r6.stats.wrapper.entitys.PlayerIDResponse;

/**
 * Contains: playerName, wrapperManager, playerPlatfrom, message}
 */
public abstract class AbstractUpdateR6MessageAction
{
	protected String playerName;
	protected WrapperManager wrapperManager;
	protected PlatfromType playerPlatfrom;
	protected Message message;
	protected Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	public abstract void execute(PlayerIDResponse userProfile) throws IOException;

	public void run(String username, WrapperManager wrapperManager, PlatfromType playerPlatfrom, Message message)
	{
		this.playerName = username;
		this.wrapperManager = wrapperManager;
		this.playerPlatfrom = playerPlatfrom;
		this.message = message;
		try
		{
			sendLoadingEmbed(message);
			PlayerIDResponse userProfile = wrapperManager.getPlayerID(playerPlatfrom, username);
			if (userProfile == null || userProfile.get() == null)
			{
				sendPlayerNotFoundEmbed(message);
				logger.warn("Player not found! Player: " + username);
			}
			else
			{
				execute(userProfile);
			}
		}
		catch (Exception e)
		{
			if (e.getMessage() != null && e.getMessage().equals("No player found!"))
			{
				sendPlayerNotFoundEmbed(message);
				logger.warn("Player not found! Player: " + username);
			}
			else
			{
				sendErrorEmbed(message, e);
				logger.error("Failed to send stats!", e);
			}
		}
	}

	private void sendLoadingEmbed(Message message)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Loading...");
		message.edit(embed);
	}

	private void sendErrorEmbed(Message message, Exception e)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Something went totally wrong!")
			.setDescription(e.getClass().getName())
			.setColor(Color.RED);
		message.edit(embed);
	}

	private void sendPlayerNotFoundEmbed(Message message)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Sorry, but no player found!")
			.setColor(Color.RED);
		message.edit(embed);
		MessageCoolDown.del(message.getLink().toString(), message.getChannel(), 15);
	}
}
