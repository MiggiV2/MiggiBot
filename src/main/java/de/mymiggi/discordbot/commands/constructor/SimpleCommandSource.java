package de.mymiggi.discordbot.commands.constructor;

public class SimpleCommandSource
{
	private String commandName;
	private String embedTitle;
	private String embedFooter;

	public String getCommandName()
	{
		return commandName;
	}

	public void setCommandName(String commandName)
	{
		this.commandName = commandName;
	}

	public String getEmbedFooter()
	{
		return embedFooter;
	}

	public void setEmbedFooter(String embedFooter)
	{
		this.embedFooter = embedFooter;
	}

	public String getEmbedTitle()
	{
		return embedTitle;
	}

	public void setEmbedTitle(String embedTitle)
	{
		this.embedTitle = embedTitle;
	}
}
