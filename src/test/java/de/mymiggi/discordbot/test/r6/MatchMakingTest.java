package de.mymiggi.discordbot.test.r6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.r6.matchmaker.MatchMakerCore;
import de.mymiggi.discordbot.tools.r6.matchmaker.Team;

class MatchMakingTest
{
	private static Logger logger = LoggerFactory.getLogger(MatchMakingTest.class.getSimpleName());

	@Test
	void test() throws InterruptedException
	{
		for (int i = 0; i < (int)(Math.random() * 20 + 99); i++)
		{
			runTest();
		}
	}

	private void runTest()
	{
		Map<String, Integer> playerMap = getRealPlayer();
		// new HashMap<String, Integer>();
		// for (int i = 0; i < (int)(Math.random() * 8 + 4); i++)
		// {
		// String name = getRandomString();
		// int skill = (int)(Math.random() * 10);
		// playerMap.put(name, skill);
		// }
		List<Team> teams = new MatchMakerCore().run(playerMap);
		teams.forEach(team -> {
			logger.info("[Team] Skill: " + team.getUnBalancedAverageSkillIndex());
			team.getPlayers().forEach(player -> {
				logger.info("Name: " + player.getName() + " Skill: " + player.getSkillIndex());
			});
		});
		logger.info("");
	}

	Map<String, Integer> getRealPlayer()
	{
		Map<String, Integer> playerMap = new HashMap<String, Integer>();
		playerMap.put("Joine", 6);
		playerMap.put("Maxi", 1);
		playerMap.put("Justin", 3);
		playerMap.put("Stef", 7);
		playerMap.put("Simon", 6);
		playerMap.put("Eli", 8);
		playerMap.put("Bene", 2);
		playerMap.put("Tobi", 8);
		// playerMap.put("Xandi", 0);
		playerMap.put("Michi", 9);
		return playerMap;
	}

	String getRandomString()
	{
		char[] chars = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'X', 'Z' };
		char[] randomStr = new char[8];
		for (int i = 0; i < randomStr.length; i++)
		{
			int randomIndex = (int)(Math.random() * chars.length);
			randomStr[i] = chars[randomIndex];
		}
		return new String(randomStr);
	}
}
