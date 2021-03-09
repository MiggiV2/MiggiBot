package de.mymiggi.discordbot.server.r6.matchmaker.embeds;

import java.awt.Color;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.tools.r6.matchmaker.R6Player;
import de.mymiggi.discordbot.tools.r6.matchmaker.Team;

public class TeamsEmbed
{
	public EmbedBuilder buildTeamBlue(List<Team> teams, boolean isAttack)
	{
		return buildEmbed(teams.get(0), isAttack, true);
	}

	public EmbedBuilder buildTeamOrange(List<Team> teams, boolean isAttack)
	{
		return buildEmbed(teams.get(1), isAttack, false);
	}

	private EmbedBuilder buildEmbed(Team team, boolean isAttack, boolean isTeamBlue)
	{
		EmbedBuilder embed = new EmbedBuilder();
		String teamOrange = "";
		String imageURL;
		String teamColor;
		String sidePostion;
		for (R6Player player : team.getPlayers())
		{
			if (isBestPlayer(team, player))
			{
				teamOrange += "-" + player.getName() + " :crown:\r\n";
			}
			else
			{
				teamOrange += "-" + player.getName() + "\r\n";
			}
		}
		embed.setDescription(teamOrange);
		if (isTeamBlue)
		{
			embed.setColor(Color.BLUE);
			teamColor = "Team BLUE ";
		}
		else
		{
			embed.setColor(Color.ORANGE);
			teamColor = "Team Orange ";
		}
		if (isAttack)
		{
			sidePostion = "ATTACKER";
			imageURL = "https://generacionxbox.com/wp-content/uploads/2018/08/rainbow-six-siege-maverick.jpg";
		}
		else
		{
			sidePostion = "DEFENDER";
			imageURL = "https://pc-gaming.it/wp-content/uploads/2018/11/rainbow-six-siege-censura1-696x392.jpeg";
		}
		embed
			.setTitle(teamColor + sidePostion)
			.setImage(imageURL);
		return embed;
	}

	private boolean isBestPlayer(Team team, R6Player player)
	{
		int highestSkill = 0;
		for (R6Player temp : team.getPlayers())
		{
			if (temp.getSkillIndex() > highestSkill)
			{
				highestSkill = temp.getSkillIndex();
			}
		}
		return highestSkill == player.getSkillIndex();
	}
}
