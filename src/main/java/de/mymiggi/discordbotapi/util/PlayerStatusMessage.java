package de.mymiggi.discordbotapi.util;

public class PlayerStatusMessage
{
	private String[] onlinePlayerNames;
	private String[] offlinePlayerNames;
	private String webhookID;

	public String getWebhookID()
	{
		return webhookID;
	}

	public PlayerStatusMessage setWebhookID(String webhookID)
	{
		this.webhookID = webhookID;
		return this;
	}

	public String[] getOnlinePlayerNames()
	{
		return onlinePlayerNames;
	}

	public void setOnlinePlayerNames(String[] onlinePlayerNames)
	{
		this.onlinePlayerNames = onlinePlayerNames;
	}

	public String[] getOfflinePlayerNames()
	{
		return offlinePlayerNames;
	}

	public void setOfflinePlayerNames(String[] offlinePlayerNames)
	{
		this.offlinePlayerNames = offlinePlayerNames;
	}
}
