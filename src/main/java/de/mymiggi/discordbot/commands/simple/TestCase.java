package de.mymiggi.discordbot.commands.simple;

import org.javacord.api.event.message.MessageCreateEvent;

public class TestCase
{
	public void testMassageTime(MessageCreateEvent event)
	{
		event.getMessageContent();
	}
}
