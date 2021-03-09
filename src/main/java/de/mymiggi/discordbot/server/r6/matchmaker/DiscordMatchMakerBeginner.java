package de.mymiggi.discordbot.server.r6.matchmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import de.mymiggi.discordbot.server.r6.matchmaker.embeds.NeedToRegisterEmbed;
import de.mymiggi.discordbot.server.r6.matchmaker.embeds.TeamsEmbed;
import de.mymiggi.discordbot.tools.database.UniversalHibernateClient;
import de.mymiggi.discordbot.tools.database.util.RainbowSixPlayer;
import de.mymiggi.discordbot.tools.r6.matchmaker.MatchMakerCore;
import de.mymiggi.discordbot.tools.r6.matchmaker.Team;

public class DiscordMatchMakerBeginner
{
	private Message teamBlueEmbed;
	private Message teamOrangeEmbed;
	private List<RainbowSixPlayer> allSiegePlayers = new UniversalHibernateClient().getList(RainbowSixPlayer.class);

	public void run(Map<User, Boolean> vcUserIngoreMap, Server server, TextChannel channel)
	{
		List<User> selectedPlayer = new ArrayList<User>();
		vcUserIngoreMap.forEach((user, shouldIgnore) -> {
			if (!shouldIgnore)
			{
				selectedPlayer.add(user);
			}
		});
		List<RainbowSixPlayer> selectedSiegePlayers = new ArrayList<RainbowSixPlayer>();
		List<User> notRegisterdUser = new ArrayList<User>();
		for (User temp : selectedPlayer)
		{
			try
			{
				selectedSiegePlayers.add(getPlayerByID(allSiegePlayers, temp, server));
			}
			catch (Exception e)
			{
				notRegisterdUser.add(temp);
			}
		}
		if (!notRegisterdUser.isEmpty())
		{
			channel.sendMessage(new NeedToRegisterEmbed().build(notRegisterdUser));
		}
		else
		{
			Thread thread = new Thread()
			{
				public void run()
				{
					match(selectedSiegePlayers, channel);
				}
			};
			thread.start();
		}
	}

	public void deleteEmbeds()
	{
		if (teamBlueEmbed != null && teamBlueEmbed.canYouDelete())
		{
			teamBlueEmbed.delete();
		}
		if (teamOrangeEmbed != null && teamOrangeEmbed.canYouDelete())
		{
			teamOrangeEmbed.delete();
		}
	}

	private void match(List<RainbowSixPlayer> selectedSiegePlayers, TextChannel channel)
	{
		Map<String, Integer> map = new HashMap<String, Integer>();
		selectedSiegePlayers.forEach(player -> {
			map.put(player.getName(), player.getSkillIndex());
		});
		List<Team> teams = new MatchMakerCore().run(map);

		int random = (int)(Math.random() * 10) + 1;
		boolean isAttack = random > 5;

		if (teamBlueEmbed == null && teamOrangeEmbed == null)
		{
			channel.type();
			channel.sendMessage(new TeamsEmbed().buildTeamBlue(teams, !isAttack)).thenAccept(message -> {
				teamBlueEmbed = message;
			});
			channel.sendMessage(new TeamsEmbed().buildTeamOrange(teams, isAttack)).thenAccept(message -> {
				teamOrangeEmbed = message;
			});
		}
		else
		{
			teamBlueEmbed.edit(new TeamsEmbed().buildTeamBlue(teams, !isAttack));
			teamOrangeEmbed.edit(new TeamsEmbed().buildTeamOrange(teams, isAttack));
		}
	}

	private RainbowSixPlayer getPlayerByID(List<RainbowSixPlayer> allSiegePlayers, User user, Server server) throws Exception
	{
		String userID = server.getId() + "" + user.getId();
		for (RainbowSixPlayer temp : allSiegePlayers)
		{
			if (temp.getId().equals(userID))
			{
				return temp;
			}
		}
		throw new Exception("Not found!");
	}
}
