package de.mymiggi.discordbot.commands.constructor;

import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.main.BotMainCore;

public class Builder
{

	private final Gson gson = new Gson();
	private ArrayList<Command> commandlist = new ArrayList<Command>();
	private Logger logger = LoggerFactory.getLogger("CommandBuilder");

	public void build(String json)
	{
		SimpleCommandSource source = new SimpleCommandSource();
		source = gson.fromJson(json, SimpleCommandSource.class);
		Command command = new Command();
		EmbedBuilder embed = new EmbedBuilder();

		embed
			.setTitle(source.getEmbedTitle())
			.setDescription(source.getEmbedFooter())
			.setFooter("succesfuly created!");

		command.setCommandName(source.getCommandName());
		command.setEmbed(embed);

		commandlist.add(command);
		logger.info("Command: " + BotMainCore.prefix + source.getCommandName() + " loaded!");
	}

	public ArrayList<Command> getCommandlist()
	{
		return commandlist;
	}
}
