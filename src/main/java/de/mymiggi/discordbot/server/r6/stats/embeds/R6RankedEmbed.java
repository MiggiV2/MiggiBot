package de.mymiggi.discordbot.server.r6.stats.embeds;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.r6.stats.wrapper.entitys.Profile;
import de.mymiggi.r6.stats.wrapper.entitys.rank.RankedStats;

public class R6RankedEmbed
{
	public EmbedBuilder build(RankedStats stats, Profile userProfile)
	{
		return new EmbedBuilder()
			.setTitle("Your Ranked stats " + userProfile.getNameOnPlatform())
			.setThumbnail(userProfile.getProfilePrictureURL())
			.setColor(Color.BLUE)
			.addField(stats.getRankName(), String.format(""
				+ "Current MMR: %s\r\n"
				+ "Max MMR: %s\r\n"
				+ "LastMatch: %sMMR\r\n",
				stats.getMmr(),
				stats.getMax_mmr(),
				stats.getLast_match_mmr_change()))
			.addField("K/D " + stats.getKDStr(), String.format(""
				+ "Kills: %s\r\n"
				+ "Deaths: %s\r\n",
				stats.getKills(),
				stats.getDeaths()))
			.addField("Wins/Losses " + stats.getWinsAndLossesRation(), String.format(""
				+ "Wins: %s\r\n"
				+ "Losses: %s\r\n"
				+ "Abandons: %s\r\n",
				stats.getWins(),
				stats.getLosses(),
				stats.getAbandons()));
	}
}
