package de.mymiggi.r6.stats.wrapper.entitys;

public class Profile
{
	private String profileId;
	private String userId;
	private String platformType;
	private String idOnPlatform;
	private String nameOnPlatform;

	public String getProfileId()
	{
		return profileId;
	}

	public void setProfileId(String profileId)
	{
		this.profileId = profileId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getPlatformType()
	{
		return platformType;
	}

	public void setPlatformType(String platformType)
	{
		this.platformType = platformType;
	}

	public String getIdOnPlatform()
	{
		return idOnPlatform;
	}

	public void setIdOnPlatform(String idOnPlatform)
	{
		this.idOnPlatform = idOnPlatform;
	}

	public String getNameOnPlatform()
	{
		return nameOnPlatform;
	}

	public void setNameOnPlatform(String nameOnPlatform)
	{
		this.nameOnPlatform = nameOnPlatform;
	}

	public String getProfilePrictureURL()
	{
		return String.format("https://ubisoft-avatars.akamaized.net/%s/default_146_146.png", this.userId);
	}
}
