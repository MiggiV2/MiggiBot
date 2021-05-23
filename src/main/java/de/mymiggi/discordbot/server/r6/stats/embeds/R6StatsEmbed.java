package de.mymiggi.discordbot.server.r6.stats.embeds;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.r6.stats.wrapper.entitys.Profile;
import de.mymiggi.r6.stats.wrapper.entitys.stats.GameModeStats;
import de.mymiggi.r6.stats.wrapper.entitys.stats.SmartStatsResponse;

public class R6StatsEmbed
{
	public EmbedBuilder build(SmartStatsResponse statsResponse, Profile userProfile)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Your stats " + userProfile.getNameOnPlatform())
			.setThumbnail(userProfile.getProfilePrictureURL())
			.setFooter(String.format("From %s till %s", statsResponse.getStartDate(), statsResponse.getEndDate()));
		addField(embed, statsResponse.getAllStats(), "GENERAL", false);
		addField(embed, statsResponse.getRankedStats(), "RANKED", true);
		addField(embed, statsResponse.getUnrankedStats(), "UNRANKED", true);
		addField(embed, statsResponse.getCasualStats(), "CASUAL", false);
		return embed;
	}

	private EmbedBuilder addField(EmbedBuilder embed, GameModeStats gameModeStats, String mode, boolean inline)
	{
		if (gameModeStats == null)
		{
			return embed;
		}
		else
		{
			return embed.addField(mode, String.format(""
				+ "K/D: %s \r\n"
				+ "Headshot: %s %% \r\n"
				+ "Win/Loss: %s \r\n"
				+ "Trades: %s \r\n"
				+ "TeamKills: %s \r\n"
				+ "OpeningKills: %s \r\n"
				+ "OpeningDeaths: %s \r\n"
				+ "RoundsWithAnAce %s \r\n"
				+ "RoundsWithClutch %s",
				gameModeStats.getKillDeathRatio(),
				(int)(gameModeStats.getHeadshotAccuracy() * 100),
				gameModeStats.getWinLossRatio(),
				gameModeStats.getTrades(),
				gameModeStats.getTeamKills(),
				gameModeStats.getOpeningKills(),
				gameModeStats.getOpeningDeaths(),
				gameModeStats.getRoundsWithAnAce(),
				gameModeStats.getRoundsWithClutch()), inline);
		}
	}
}
