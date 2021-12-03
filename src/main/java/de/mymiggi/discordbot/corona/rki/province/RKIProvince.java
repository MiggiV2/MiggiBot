package de.mymiggi.discordbot.corona.rki.province;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class RKIProvince
{
	private List<Attributes> data;
	private int totalCases;
	private int avDeath;
	private int avCases;
	private int avInzidenz;
	private String coivIcon = "https://cdn.pixabay.com/photo/2020/04/19/07/13/coronavirus-5062185_960_720.png";
	private Logger logger = LoggerFactory.getLogger(RKIProvince.class.getSimpleName());
	private long lastUpdateStamp;

	public void send(MessageCreateEvent event)
	{
		if (data == null || System.currentTimeMillis() - lastUpdateStamp > 12 * 60 * 60 * 1000)
		{
			lastUpdateStamp = System.currentTimeMillis();
			syncData();
		}
		event.getChannel().sendMessage(buildEmbed());
		event.getChannel().sendMessage(highestEmbed());

		MessageCoolDown.del(event.getMessageLink().toString(), event.getChannel(), 5);
		logger.info(event.getMessageAuthor().getName() + " used command!");

	}

	public void send(SlashCommandCreateEvent event)
	{
		SlashCommandInteraction interaction = event.getSlashCommandInteraction();
		interaction.respondLater();
		if (data == null || System.currentTimeMillis() - lastUpdateStamp > 12 * 60 * 60 * 1000)
		{
			lastUpdateStamp = System.currentTimeMillis();
			syncData();
		}
		interaction.createFollowupMessageBuilder()
			.addEmbed(buildEmbed())
			.addEmbed(highestEmbed())
			.send();
		logger.info(interaction.getUser().getName() + " used command!");

	}

	public void sendInChannel(TextChannel channel)
	{
		if (data == null || System.currentTimeMillis() - lastUpdateStamp > 12 * 60 * 60 * 1000)
		{
			lastUpdateStamp = System.currentTimeMillis();
			syncData();
		}
		channel.sendMessage(buildEmbed());
		channel.sendMessage(highestEmbed());
	}

	public void syncData()
	{
		RobertKochInstitut rki = new RobertKochInstitut();

		data = rki.getData();
		totalCases = rki.getTotal(data);
		avDeath = rki.getAverageDeathLast7Days(data);
		avCases = rki.getAverageCasesLast7Days(data);
		avInzidenz = (int)rki.getAverageInzidenz(data);
	}

	public EmbedBuilder buildEmbed()
	{
		Instant updatedInstant = Instant.ofEpochMilli(data.get(0).getResponse().getAktualisierung());
		Date updateDate = Date.from(updatedInstant);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm dd.MM");
		EmbedBuilder embed = new EmbedBuilder();
		for (Attributes temp : data)
		{
			int newInfections = temp.getResponse().getFallzahl();
			double inzidenz = temp.getResponse().getCases7_bl_per_100k();

			embed.addInlineField(
				String.format("%s", temp.getResponse().getBundesLand()),
				String.format("*Total %s \r\n Inzidenz %s*", formaterInt(newInfections), (int)inzidenz));
		}
		embed
			.setTitle(String.format("TOTAL CASES **%s** INZIDENZ **%s**", formaterInt(totalCases), avInzidenz))
			.setFooter(String.format("Data from %s for Germany", dateFormatter.format(updateDate)))
			.setColor(Color.GREEN)
			.setThumbnail(coivIcon);
		return embed;
	}

	public EmbedBuilder highestEmbed()
	{
		EmbedBuilder embed = new EmbedBuilder();
		Response highestInzidenz = new Response();
		highestInzidenz.setCases7_bl_per_100k(0);

		for (int i = 0; i < data.size(); i++)
		{
			Response temp = data.get(i).getResponse();

			if (temp.getCases7_bl_per_100k() > highestInzidenz.getCases7_bl_per_100k())
			{
				highestInzidenz = temp;
			}
		}
		embed.setTitle(String.format("%s has the highest inzidenz", highestInzidenz.getBundesLand()))
			.setDescription(String.format("\r\n **Inzidenz is %s ** \r\n **Total deaths %s**", (int)highestInzidenz.getCases7_bl_per_100k(), formaterInt(highestInzidenz.getDeath())))
			.setColor(Color.RED)
			.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/3/33/Man_in_a_Mask.svg/1200px-Man_in_a_Mask.svg.png")
			.setFooter("Data from RKI (Robert Koch Institut)");

		return embed;
	}

	private String formaterInt(int responseInt)
	{
		DecimalFormat df = new DecimalFormat("#,###.##");
		String s = df.format(responseInt);
		return s;
	}

	public List<Attributes> getData()
	{
		return data;
	}

	public int getTotalCases()
	{
		return totalCases;
	}

	public int getAvDeath()
	{
		return avDeath;
	}

	public int getAvCases()
	{
		return avCases;
	}
}
