package de.mymiggi.r6.stats.wrapper.actions;

public class GetProfilePictureURLAction
{
	public String get(String playerID)
	{
		return String.format("https://ubisoft-avatars.akamaized.net/%s/default_256_256.png", playerID);
	}
}
