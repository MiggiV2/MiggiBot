package de.mymiggi.discordbot.main;

import java.util.Optional;

/**
 * [UBISOFT] USE EMAIL + PW OR CREDENTIAL = Base64 (email:password)
 */
public class BotConfig
{
	private String botToken;
	private String ytAPIKey;
	private String prefix;
	private Optional<String> tenorAPIKey;
	private Optional<String> ipInfoToken;
	private Optional<String> untisSchoolName;
	private Optional<String> eMail;
	private Optional<String> password;
	private Optional<String> credential;

	public BotConfig()
	{
		this.tenorAPIKey = Optional.empty();
		this.ipInfoToken = Optional.empty();
		this.untisSchoolName = Optional.empty();
		this.eMail = Optional.empty();
		this.password = Optional.empty();
		this.credential = Optional.empty();
	}

	public String getYtAPIKey()
	{
		return ytAPIKey;
	}

	public String getBotToken()
	{
		return botToken;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public Optional<String> getTenorAPIKey()
	{
		return tenorAPIKey;
	}

	public void setTenorAPIKey(String tenorAPIKey)
	{
		this.tenorAPIKey = Optional.of(tenorAPIKey);
	}

	public Optional<String> getIpInfoToken()
	{
		return ipInfoToken;
	}

	public void setIpInfoToken(String ipInfoToken)
	{
		this.ipInfoToken = Optional.of(ipInfoToken);
	}

	public Optional<String> getUntisSchoolName()
	{
		return untisSchoolName;
	}

	public void setUntisSchoolName(String untisSchoolName)
	{
		this.untisSchoolName = Optional.of(untisSchoolName);
	}

	public Optional<String> geteMail()
	{
		return eMail;
	}

	public void seteMail(String eMail)
	{
		this.eMail = Optional.of(eMail);
	}

	public Optional<String> getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = Optional.of(password);
	}

	public Optional<String> getCredential()
	{
		return credential;
	}

	public void setCredential(String credential)
	{
		this.credential = Optional.of(credential);
	}

	public void setYtAPIKey(String ytAPIKey)
	{
		this.ytAPIKey = ytAPIKey;
	}

	public void setBotToken(String botToken)
	{
		this.botToken = botToken;
	}
}