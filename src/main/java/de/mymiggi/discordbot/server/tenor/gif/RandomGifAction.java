package de.mymiggi.discordbot.server.tenor.gif;

import de.mymiggi.tenor.TenorClientCore;
import de.mymiggi.tenor.util.TenorGif;

public class RandomGifAction
{
	public String get(String searchQuery) throws Exception
	{
		TenorGif[] gifs = new TenorClientCore()
			.run(searchQuery)
			.getResutls();
		int randomInt = (int)(gifs.length * Math.random());
		String gifURL = gifs[randomInt].getUrl();
		return gifURL;
	}
}
