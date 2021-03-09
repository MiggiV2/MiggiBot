package de.mymiggi.discordbot.corona.covid19;

import java.awt.Color;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import de.mymiggi.discordbot.http.Client;

public class CoronaAPI
{

	private LocalDate todayDate = LocalDate.now();
	private String thisWeek = todayDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'"));
	private String threeWeeksBefore = todayDate.minusWeeks(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'"));
	private String url = String.format("https://api.covid19api.com/country/de/status/confirmed?from=%s&to=%s", threeWeeksBefore, thisWeek);
	private Gson gson = new Gson();
	private Logger logger = LoggerFactory.getLogger("Covid19API");

	public void get(MessageCreateEvent event)
	{
		try
		{
			CoronaResponse[] coronaResponse = buildRequestObj();
			EmbedBuilder embed = calculateStatsAndBuildEmbed(coronaResponse);
			event.getChannel().sendMessage(embed);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
			event.getChannel().sendMessage(buildFaildEmbed(e.getMessage()));
		}
		logger.info("User " + event.getMessageAuthor().getName() + " used command!");
	}

	public CoronaResponse[] buildRequestObj() throws Exception
	{
		long beginningUnixStamp = System.currentTimeMillis();
		Client client = new Client();
		String jsonResponse = client.sendGet(url);

		long newTimeStamp = System.currentTimeMillis() - beginningUnixStamp;
		System.out.println("[Covid19API] Time till respond: " + newTimeStamp);

		CoronaResponse[] coronaResponse = gson.fromJson(jsonResponse, CoronaResponse[].class);
		return coronaResponse;
	}

	public CoronaList buildCoronaList(CoronaResponse[] coronaResponse) throws Exception
	{
		if (coronaResponse.length == 0)
		{
			throw new Exception("CoronaResponse should not be empty!");
		}
		CoronaList coronaList = new CoronaList();

		coronaList.setToday(coronaResponse[coronaResponse.length - 1]);
		coronaList.setLastWeek(coronaResponse[coronaResponse.length - 8]);
		coronaList.setTowWeeksAgo(coronaResponse[coronaResponse.length - 15]);
		coronaList.setYesterday(coronaResponse[coronaResponse.length - 2]);

		return coronaList;
	}

	public EmbedBuilder calculateStatsAndBuildEmbed(CoronaResponse[] coronaResponse) throws Exception
	{
		CoronaList coronaList = buildCoronaList(coronaResponse);
		LocalDate todayDate = LocalDate.now();

		int moreThanYesterday = coronaList.getToday().getCases() - coronaList.getYesterday().getCases();
		int moreThanLastWeek = coronaList.getToday().getCases() - coronaList.getLastWeek().getCases();
		int moreThan2Week = coronaList.getLastWeek().getCases() - coronaList.getTowWeeksAgo().getCases();

		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle(todayDate.getDayOfWeek() + " " + coronaList.getToday().getFormattedDate())
			.setColor(Color.RED)
			.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/SARS-CoV-2_without_background.png/1200px-SARS-CoV-2_without_background.png")
			.addField(String.format("Difference in %s", coronaResponse[0].getCountry()),
				String.format("More than last week: %s", formaterInt(moreThanLastWeek)) + "\r\n" +
					String.format("From 3 weeks - 2 weeks: %s", formaterInt(moreThan2Week)))
			.addField(String.format("Total in %s", coronaResponse[0].getCountry()),
				String.format("%s confirmed [%s]", formaterInt(coronaList.getTowWeeksAgo().getCases()), coronaList.getTowWeeksAgo().getFormattedDate()) + "\r\n" +
					String.format("%s confirmed [%s]", formaterInt(coronaList.getLastWeek().getCases()), coronaList.getLastWeek().getFormattedDate()) + "\r\n" +
					String.format("%s confirmed [%s]", formaterInt(coronaList.getToday().getCases()), coronaList.getToday().getFormattedDate()))
			.addField(String.format("More than yesterday in %s", coronaResponse[0].getCountry()),
				String.format("More than yesterday %s", formaterInt(moreThanYesterday)));

		return embed;
	}

	private String formaterInt(int responseInt)
	{
		DecimalFormat df = new DecimalFormat("#,###.##");
		String s = df.format(responseInt);
		return s;
	}

	private EmbedBuilder buildFaildEmbed(String message)
	{
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Sorry but there was an error with the corona api!")
			.addField("Error:", message)
			.setColor(Color.red);

		return embed;
	}
}
