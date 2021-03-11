package de.mymiggi.discordbotapi.util;

import org.javacord.api.entity.server.Server;

import de.mymiggi.discordbot.main.BotMainCore;

public class DiscordServerResponse
{
	private String name;
	private String iconURL;
	private int memeberSize;
	private String connectedVC;

	public DiscordServerResponse(Server discordServer)
	{
		this.name = discordServer.getName();
		this.iconURL = discordServer.getIcon().get().getUrl().toString();
		this.memeberSize = discordServer.getMemberCount();
		if (BotMainCore.api != null)
		{
			discordServer.getConnectedVoiceChannel(BotMainCore.api.getYourself()).ifPresent(vc -> {
				this.connectedVC = vc.getName();
			});
		}
		else
		{
			this.connectedVC = "Bot is starting!";
		}
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIconURL()
	{
		return iconURL;
	}

	public void setIconURL(String iconURL)
	{
		this.iconURL = iconURL;
	}

	public int getMemeberSize()
	{
		return memeberSize;
	}

	public void setMemeberSize(int memeberSize)
	{
		this.memeberSize = memeberSize;
	}

	public String getConnectedVC()
	{
		return connectedVC;
	}

	public void setConnectedVC(String connectedVC)
	{
		this.connectedVC = connectedVC;
	}
}
