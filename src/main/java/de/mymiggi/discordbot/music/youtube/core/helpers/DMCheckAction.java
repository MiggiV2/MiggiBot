package de.mymiggi.discordbot.music.youtube.core.helpers;

import org.javacord.api.interaction.SlashCommandInteraction;

public class DMCheckAction
{
	public boolean run(SlashCommandInteraction interaction)
	{
		if (!interaction.getServer().isPresent() && !interaction.getChannel().isPresent())
		{
			interaction.createImmediateResponder()
				.setContent("You can't use this Command in DMs!")
				.respond();
			return true;
		}
		return false;
	}
}
