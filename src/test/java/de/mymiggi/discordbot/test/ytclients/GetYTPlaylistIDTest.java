package de.mymiggi.discordbot.test.ytclients;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.tools.ytclients.GetYTPlaylistIDAction;

class GetYTPlaylistIDTest
{
	@Test
	void test() throws Exception
	{
		String searchQuery = "https://youtube.com/playlist?list=PLiLyShvbEK3SZ5CueLeCWfsrJgcAqFxuQ";
		String rightID = "PLiLyShvbEK3SZ5CueLeCWfsrJgcAqFxuQ";
		String id = new GetYTPlaylistIDAction().get(searchQuery);
		System.out.println("'" + rightID + "'");
		System.out.println("'" + id + "'");
		assertEquals(rightID, id);
	}
}
