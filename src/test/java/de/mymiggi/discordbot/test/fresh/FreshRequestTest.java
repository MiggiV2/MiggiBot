package de.mymiggi.discordbot.test.fresh;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import de.mymiggi.discordbot.music.fresh.FreshPlayer;
import de.mymiggi.discordbot.music.fresh.entitys.FreshSong;

class FreshRequestTest
{

	@Test
	void test() throws Exception
	{
		FreshPlayer player = new FreshPlayer();
		List<FreshSong> songs = player.loadSongs();
		assertTrue(!songs.isEmpty());
		System.out.println("Songs:");
		songs.forEach(temp -> {
			System.out.println(
				String.format("title: %s, artist: %s, starttime %s",
					temp.getTitle(),
					temp.getArtist(),
					temp.getStarttime()));
		});
	}
}
