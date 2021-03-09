package de.mymiggi.discordbot.commands.constructor;

import java.util.ArrayList;

import de.mymiggi.discordbot.tools.util.FileManager;

public class SimpleCommandCore
{
	private Builder builder = new Builder();
	private FileManager fileManger = new FileManager();

	public void run()
	{
		for (String temp : sourcesFiles())
		{
			String json = fileManger.read(temp, "Commands");
			builder.build(json);
		}
	}

	private String[] sourcesFiles()
	{
		return fileManger.getFiles("Commands");
	}

	public ArrayList<Command> getCommandList()
	{
		return builder.getCommandlist();
	}
}
