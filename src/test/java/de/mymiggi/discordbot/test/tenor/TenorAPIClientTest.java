package de.mymiggi.discordbot.test.tenor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.mymiggi.tenor.TenorClientCore;
import de.mymiggi.tenor.util.TenorResponse;

class TenorAPIClientTest
{
	@Test
	void test() throws Exception
	{
		TenorResponse response = new TenorClientCore().run("cat");
		assertTrue(response.getResutls().length != 0);
	}
}
