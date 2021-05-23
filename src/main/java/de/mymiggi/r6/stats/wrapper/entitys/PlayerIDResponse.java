package de.mymiggi.r6.stats.wrapper.entitys;

public class PlayerIDResponse
{
	private Profile[] profiles = new Profile[1];

	public Profile[] getProfiles()
	{
		return profiles;
	}

	public void setProfiles(Profile[] profiles)
	{
		this.profiles = profiles;
	}

	public Profile get()
	{
		return profiles[0];
	}
}
