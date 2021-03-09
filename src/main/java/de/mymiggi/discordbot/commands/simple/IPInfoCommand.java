package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import io.ipinfo.api.IPInfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

public class IPInfoCommand
{
	private long lastID;
	private Logger logger = LoggerFactory.getLogger(IPInfoCommand.class.getSimpleName());

	public boolean send(MessageCreateEvent event, String[] context)
	{
		logger.info(event.getMessageAuthor().getName() + " used command!");

		if (context[1].split(".").length != 4)
		{
			try
			{

				if (context[1].startsWith("https") | context[1].startsWith("http"))
				{
					URL url = new URL(context[1]);
					context[1] = url.getHost();
				}
				InetAddress domain = InetAddress.getByName(context[1]);
				context[1] = domain.getHostAddress();
			}
			catch (MalformedURLException | UnknownHostException e)
			{
				logger.warn("Failed", e);
			}
		}
		sendStartEmbed(event, context);
		boolean failed = true;
		if (context.length != 2)
		{
			sendFailedEmbed(event);
			return failed;
		}
		IPInfo ipInfo = IPInfo.builder().setToken("708ce44b2ea38c").build();
		try
		{
			IPResponse response = ipInfo.lookupIP(context[1]);
			sendInfoEmbed(event, response);
		}
		catch (RateLimitedException ex)
		{
			logger.warn("API ERROR", ex);
			sendFailedEmbed(event);
		}
		failed = false;
		return failed;
	}

	private void sendStartEmbed(MessageCreateEvent event, String[] context)
	{
		MessageBuilder m = new MessageBuilder()
			.setEmbed(new EmbedBuilder()
				.setTitle("pls wait a moment")
				.setDescription(String.format("lookup %s ...", context[1]))
				.setColor(Color.ORANGE));
		try
		{
			lastID = m.send(event.getChannel()).get().getId();
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}

	private void sendFailedEmbed(MessageCreateEvent event)
	{
		EmbedBuilder m = new EmbedBuilder()
			.setTitle("LookUp Help")
			.setDescription("Example: ++lookup 216.58.215.227")
			.setColor(Color.ORANGE);
		try
		{
			BotMainCore.api.getMessageById(lastID, event.getChannel()).get().edit(m);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}

	}

	private void sendInfoEmbed(MessageCreateEvent event, IPResponse ipResponse)
	{
		EmbedBuilder m = new EmbedBuilder()
			.setTitle("LookUP " + ipResponse.getIp())
			.setDescription(String.format("Country: %s \n Region: %s \n City: %s %s \n Organization : %s", ipResponse.getCountryCode(), ipResponse.getRegion(), ipResponse.getPostal(), ipResponse.getCity(), ipResponse.getOrg()))
			.setColor(Color.ORANGE);
		try
		{
			BotMainCore.api.getMessageById(lastID, event.getChannel()).get().edit(m);
		}
		catch (InterruptedException | ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
