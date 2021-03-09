package de.mymiggi.discordbot.corona.rki.country;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class RKICountry
{
	private RoberKochInstitut rki = new RoberKochInstitut();
	private Map<String, Response> statsMap = new HashMap<String, Response>();
	private Logger logger = LoggerFactory.getLogger("RKICountry");

	public void send(MessageCreateEvent event, String[] context)
	{
		syncData();
		String country = buildQuerry(context);

		if (context.length < 2)
		{
			event.getChannel().sendMessage(noArgumentEmbed());
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 5);
			return;
		}
		try
		{
			handleServerMessages(event);

			Response querryResponse = getCountryByName(country);
			EmbedBuilder embed = buildCountryEmbed(querryResponse);
			event.getMessageAuthor().asUser().get().sendMessage(embed);

			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 5);
			return;
		}
		catch (Exception e)
		{
			if (!e.getMessage().equals("Country not found in map"))
			{
				e.printStackTrace();
				event.getChannel().sendMessage(errorEmbed());
			}
		}
		try
		{
			List<Response> results = rki.moreResults(country);

			if (results.size() == 1)
			{
				Response querryResponse = getCountryByName(results.get(0).getGEN());
				event.getMessageAuthor().asUser().get().sendMessage(buildCountryEmbed(querryResponse));
			}
			else
			{
				String messgeLink = event.getChannel().sendMessage(moreResultsEmbed(results)).get().getLink().toString();
				MessageCoolDown.del(messgeLink, event.getChannel(), 120);
			}
			MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 0);
		}
		catch (Exception e)
		{
			if (e.getMessage().equals("Country not found in map"))
			{
				event.getChannel().sendMessage(notFoundEmbed(country));
				e.getMessage();
			}
			else
			{
				event.getChannel().sendMessage(errorEmbed());
				e.printStackTrace();
			}
		}
		logger.info(event.getMessageAuthor().getName() + " used command!");
	}

	public void sendHelpEmbed(MessageCreateEvent event)
	{
		event.getChannel().sendMessage(helpEmbed());
	}

	public void syncData()
	{
		if (statsMap.isEmpty())
		{
			statsMap = rki.getStatsMap();
		}
	}

	public List<String> getCountrysList()
	{
		return rki.getCountrys();
	}

	public Response getCountryByName(String name) throws Exception
	{
		return rki.getCountryByName(name);
	}

	public EmbedBuilder buildCountryEmbed(Response covidResponse)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle(String.format("Covid stats about %s", covidResponse.getGEN()))
			.addField(covidResponse.getGEN(), String.format("Total cases %s \r\n DeathRate in percent %s \r\n Cases in percent %s \r\n Inzidenz %s",
				formaterInt(covidResponse.getCases()),
				formateDouble(covidResponse.getDeath_rate()),
				formateDouble(covidResponse.getCases_per_population()),
				formateDouble(covidResponse.getCases7_per_100k())))
			.addField("State " + covidResponse.getBL(), String.format("State Inzidenz %s \r\n", formateDouble(covidResponse.getCases7_bl_per_100k())))
			.setFooter("Last update at " + covidResponse.getLast_update())
			.setColor(Color.ORANGE)
			.setThumbnail("https://cdn.pixabay.com/photo/2020/11/17/16/53/mask-5753062_960_720.png");

		return embed;
	}

	private String buildQuerry(String[] context)
	{
		String searchQuery = "";

		if (context.length == 2)
		{
			return context[1];
		}

		for (int i = 1; i < context.length; i++)
		{
			if (i == context.length - 1)
			{
				searchQuery += context[i];
				return searchQuery;
			}
			searchQuery += context[i] + " ";
		}
		return searchQuery;
	}

	private EmbedBuilder sendLookDMEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Please go to your DMs")
			.setDescription("Next time pls enter this command only in my direct message!")
			.setColor(Color.GREEN);

		return embed;
	}

	private EmbedBuilder notFoundEmbed(String country)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Sorry. I can't find your country you entered")
			.setColor(Color.RED)
			.setDescription(country);

		return embed;
	}

	private EmbedBuilder noArgumentEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("You have to enter a country name like *Berlin Spandau*")
			.setColor(Color.RED);

		return embed;
	}

	private EmbedBuilder helpEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Help for " + BotMainCore.prefix + "country!")
			.setDescription("You can use this command to check the stats for your country (aka Landkreis)")
			.addField("Example", BotMainCore.prefix + "country " + getRandomCountry())
			.setColor(Color.WHITE);

		return embed;
	}

	private EmbedBuilder errorEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Internal Error!")
			.setDescription("Something went wrong! I'm sorry")
			.setColor(Color.RED);

		return embed;
	}

	private EmbedBuilder moreResultsEmbed(List<Response> responseList)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Maybe it's one of this " + responseList.size() + "?")
			.setColor(Color.GREEN);

		for (Response temp : responseList)
		{
			embed.addField(temp.getGEN(), "State " + temp.getBL() + "\r\n Resident " + temp.getEWZ());
		}
		return embed;
	}

	public String getRandomCountry()
	{
		int randomInt = (int)(Math.random() * getCountrysList().size());

		return getCountrysList().get(randomInt);
	}

	private String formaterInt(int responseInt)
	{
		DecimalFormat df = new DecimalFormat("#,###.##");
		return df.format(responseInt);
	}

	private String formateDouble(double responseDouble)
	{
		return String.format("%.2f", responseDouble);
	}

	private void handleServerMessages(MessageCreateEvent event)
	{
		if (event.getMessage().isServerMessage())
		{
			try
			{
				String embedLink = event.getChannel().sendMessage(sendLookDMEmbed()).get().getLink().toString();
				MessageCoolDown.del(embedLink, event.getChannel(), 30);
			}
			catch (InterruptedException | ExecutionException e)
			{
				e.printStackTrace();
			}
		}
	}

	/*
	 * Not finished yet private static void closeDM(MessageCreateEvent event) {
	 * long userID = event.getMessageAuthor().getId();
	 * BotMainCore.api.getPrivateChannelById(userID).ifPresent(c -> c.remove); }
	 */
}
