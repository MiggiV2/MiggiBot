package de.mymiggi.discordbot.main;

public class BotConfig
{
	private String ytAPIKey;
	private String botToken;
	private String prefix;
	private String tenorAPIKey;
	private String untisSchoolName;
	/*
	 * [UBISOFT]
	 * USE EMAIL + PW
	 * OR CREDENTIAL
	 */
	private String eMail;
	private String password;
	private String credential;

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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEMail()
	{
		return eMail;
	}

	public void seteMail(String eMail)
	{
		this.eMail = eMail;
	}

	public String getCredential()
	{
		return credential;
	}

	public void setCredential(String credential)
	{
		this.credential = credential;
	}
}
