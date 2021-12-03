package de.mymiggi.discordbot.commands.simple;

import java.awt.Color;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import io.ipinfo.api.IPInfo;
import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

public class IPInfoCommand
{
	private Logger logger = LoggerFactory.getLogger(IPInfoCommand.class.getSimpleName());

	public void run(SlashCommandCreateEvent event)
	{
		Optional<String> tokenOp = BotMainCore.config.getIpInfoToken();
		tokenOp.ifPresent(token -> {
			try
			{
				logger.info(event.getSlashCommandInteraction().getUser().getName() + " used command!");
				String param = event.getSlashCommandInteraction().getFirstOptionStringValue().orElse("8.8.8.8");
				String host = getHost(param);
				IPInfo ipInfo = IPInfo.builder().setToken(token).build();
				IPResponse response = ipInfo.lookupIP(host);
				event.getSlashCommandInteraction()
					.createImmediateResponder()
					.addEmbed(getInfoEmbed(response))
					.respond();
			}
			catch (MalformedURLException | UnknownHostException | RateLimitedException e)
			{
				if (!e.getClass().equals(UnknownHostException.class))
				{
					logger.error("Error for IPInfo-Command!", e);
				}
				event.getSlashCommandInteraction()
					.createImmediateResponder()
					.addEmbed(getErrordEmbed(e))
					.respond();
			}
		});
		if (!tokenOp.isPresent())
		{
			event.getSlashCommandInteraction()
				.createImmediateResponder()
				.addEmbed(getNoConfigEmbed())
				.respond();
		}
	}

	private String getHost(String param) throws MalformedURLException, UnknownHostException
	{
		String host = param;
		if (param.split(".").length != 4)
		{
			if (param.startsWith("https") | param.startsWith("http"))
			{
				host = new URL(param).getHost();
			}
			InetAddress domain = InetAddress.getByName(host);
			host = domain.getHostAddress();
		}
		return host;
	}

	private EmbedBuilder getErrordEmbed(Exception e)
	{
		return new EmbedBuilder()
			.setTitle("LookUp Error")
			.setDescription(String.format("Error: %s; Message: %s", e.getClass(), e.getMessage()))
			.setColor(Color.ORANGE);
	}

	private EmbedBuilder getInfoEmbed(IPResponse ipResponse)
	{
		return new EmbedBuilder()
			.setTitle("LookUP " + ipResponse.getIp())
			.setDescription(String.format("Country: %s \n Region: %s \n City: %s %s \n Organization : %s", ipResponse.getCountryCode(), ipResponse.getRegion(), ipResponse.getPostal(), ipResponse.getCity(), ipResponse.getOrg()))
			.setColor(Color.ORANGE);
	}

	@SuppressWarnings("unused")
	private EmbedBuilder getNoConfigEmbed()
	{
		return new EmbedBuilder()
			.setTitle("LookUp Error")
			.setDescription("No API in my Config!")
			.setColor(Color.RED);
	}
}
