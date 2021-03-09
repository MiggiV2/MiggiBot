package de.mymiggi.discordbot.tools.ytclients;

public class GetYTPlaylistIDAction
{
	public String get(String playlistURL) throws Exception
	{
		if (!playlistURL.contains("playlist?list="))
		{
			throw new Exception("No regular playlist url");
		}
		char[] playlistIDChars = new char[34];
		String[] urlParts = playlistURL.split("list=");
		if (urlParts[1].length() != 34)
		{
			throw new Exception("No regular playlist url");
		}
		urlParts[1].getChars(0, 34, playlistIDChars, 0);
		return new String(playlistIDChars);
	}
}
