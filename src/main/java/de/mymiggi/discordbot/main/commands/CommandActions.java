package de.mymiggi.discordbot.main.commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;

public enum CommandActions
{
	PowerOff
	{
		@Override
		public String getName()
		{
			return "ping";
		}

		@Override
		public void run(MessageCreateEvent event, DiscordApi api)
		{

		}
	},
	lookup
	{
		@Override
		public String getName()
		{
			return "lookup";
		}

		@Override
		public void run(MessageCreateEvent event, DiscordApi api)
		{
		}
	};

	public abstract void run(MessageCreateEvent event, DiscordApi api);

	public abstract String getName();
}
