package de.mymiggi.discordbot.main;

public class BotConfig
{
	private String ytAPIKey;
	private String botToken;
	private String prefix;
	private String tenorAPIKey;
	private String untisSchoolName;

	public String getYtAPIKey()
	{
		return ytAPIKey;
	}

	public void setYtAPIKey(String ytAPIKey)
	{
		this.ytAPIKey = ytAPIKey;
	}

	public String getBotToken()
	{
		return botToken;
	}

	public void setBotToken(String botToken)
	{
		this.botToken = botToken;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public String getTenorAPIKey()
	{
		return tenorAPIKey;
	}

	public void setTenorAPIKey(String tenorAPIKey)
	{
		this.tenorAPIKey = tenorAPIKey;
	}

	public String getUntisSchoolName()
	{
		return untisSchoolName;
	}

	public void setUntisSchoolName(String untisSchoolName)
	{
		this.untisSchoolName = untisSchoolName;
	}
}
