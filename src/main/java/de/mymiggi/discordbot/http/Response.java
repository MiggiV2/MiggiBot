package de.mymiggi.discordbot.http;

public class Response
{
	private String Status;
	private String jsonResult;

	public String getJsonResult()
	{
		return jsonResult;
	}

	public void setJsonResult(String jsonResult)
	{
		this.jsonResult = jsonResult;
	}

	public String getStatus()
	{
		return Status;
	}

	public void setStatus(String status)
	{
		Status = status;
	}
}
