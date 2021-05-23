package de.mymiggi.r6.stats.wrapper.entitys;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginResponse
{
	private String platformType;
	private String ticket;
	private String twoFactorAuthenticationTicket;
	private String profileId;
	private String userId;
	private String nameOnPlatform;
	private String environment;
	private String expiration;
	private String spaceId;
	private String clientIp;
	private String clientIpCountry;
	private String serverTime;
	private String sessionId;
	private String sessionKey;
	private String rememberMeTicket;

	public String getPlatformType()
	{
		return platformType;
	}

	public void setPlatformType(String platformType)
	{
		this.platformType = platformType;
	}

	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}

	public String getTwoFactorAuthenticationTicket()
	{
		return twoFactorAuthenticationTicket;
	}

	public void setTwoFactorAuthenticationTicket(String twoFactorAuthenticationTicket)
	{
		this.twoFactorAuthenticationTicket = twoFactorAuthenticationTicket;
	}

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

	public String getNameOnPlatform()
	{
		return nameOnPlatform;
	}

	public void setNameOnPlatform(String nameOnPlatform)
	{
		this.nameOnPlatform = nameOnPlatform;
	}

	public String getEnvironment()
	{
		return environment;
	}

	public void setEnvironment(String environment)
	{
		this.environment = environment;
	}

	public boolean isExpired()
	{
		char[] shorterChar = new char[19];
		expiration.getChars(0, 19, shorterChar, 0);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String shorterStr = new String(shorterChar);
		return LocalDateTime.parse(shorterStr, format).isBefore(LocalDateTime.now());
	}

	public String getExpiration()
	{
		return expiration;
	}

	public void setExpiration(String expiration)
	{
		this.expiration = expiration;
	}

	public String getSpaceId()
	{
		return spaceId;
	}

	public void setSpaceId(String spaceId)
	{
		this.spaceId = spaceId;
	}

	public String getClientIp()
	{
		return clientIp;
	}

	public void setClientIp(String clientIp)
	{
		this.clientIp = clientIp;
	}

	public String getClientIpCountry()
	{
		return clientIpCountry;
	}

	public void setClientIpCountry(String clientIpCountry)
	{
		this.clientIpCountry = clientIpCountry;
	}

	public String getServerTime()
	{
		return serverTime;
	}

	public void setServerTime(String serverTime)
	{
		this.serverTime = serverTime;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getSessionKey()
	{
		return sessionKey;
	}

	public void setSessionKey(String sessionKey)
	{
		this.sessionKey = sessionKey;
	}

	public String getRememberMeTicket()
	{
		return rememberMeTicket;
	}

	public void setRememberMeTicket(String rememberMeTicket)
	{
		this.rememberMeTicket = rememberMeTicket;
	}
}
