package de.mymiggi.discordbot.music.youtube.core.helpers;

import org.javacord.api.interaction.SlashCommandInteraction;

import de.mymiggi.discordbot.tools.util.RemoveResponseAction;

public class DMCheckAction
{
	public boolean run(SlashCommandInteraction interaction)
	{
		if (!interaction.getServer().isPresent() && !interaction.getChannel().isPresent())
		{
			interaction.createImmediateResponder()
				.setContent("You can't use this Command in DMs!")
				.respond()
				.thenAccept(message -> new RemoveResponseAction().run(message, 5));
			return true;
		}
		return false;
	}
}
