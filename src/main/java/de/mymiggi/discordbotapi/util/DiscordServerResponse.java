package de.mymiggi.discordbotapi.util;

import org.javacord.api.entity.server.Server;

public class DiscordServerResponse
{
	private String name;
	private String iconURL;
	private int memeberSize;

	public DiscordServerResponse()
	{

	}

	public DiscordServerResponse(Server discordServer)
	{
		this.name = discordServer.getName();
		this.iconURL = discordServer.getIcon().get().getUrl().toString();
		this.memeberSize = discordServer.getMemberCount();
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
}
