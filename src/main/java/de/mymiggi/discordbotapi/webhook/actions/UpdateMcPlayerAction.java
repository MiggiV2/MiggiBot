package de.mymiggi.discordbotapi.webhook.actions;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import com.google.gson.Gson;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbotapi.util.PlayerStatusMessage;
import de.mymiggi.discordbotapi.webhook.WebHookActions;

public class UpdateMcPlayerAction implements WebHookActions
{

	@Override
	public boolean run(String json)
	{
		PlayerStatusMessage message = new Gson().fromJson(json, PlayerStatusMessage.class);
		System.out.println(json);
		if (message.getOfflinePlayerNames() == null || message.getOnlinePlayerNames() == null)
		{
			return false;
		}
		String offlinePlayers = "";
		String onlinePlayers = "";
		for (String temp : message.getOfflinePlayerNames())
		{
			offlinePlayers += temp + "\r\n";
		}
		for (String temp : message.getOnlinePlayerNames())
		{
			onlinePlayers += temp + "\r\n";
		}
		if (offlinePlayers.equals(""))
		{
			offlinePlayers = "no player!";
		}
		if (onlinePlayers.equals(""))
		{
			onlinePlayers = "no player!";
		}
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Minecraft-Server Player")
			.addField("Online :green_circle:", onlinePlayers)
			.addField("Offline :red_circle:", offlinePlayers)
			.setColor(Color.YELLOW);

		BotMainCore.api.getMessageByLink("https://discord.com/channels/605083050770038787/842114018629517362/842114199290380289")
			.ifPresent(embedMessageOp -> {
				embedMessageOp.thenAccept(embedMessage -> {
					embedMessage.edit(embed).thenAccept(sendMessage -> {
						System.out.println("loaded offlinePlayer " + message.getOfflinePlayerNames().length);
						System.out.println("Done!");
					});
				});
			});
		return true;
	}
}
