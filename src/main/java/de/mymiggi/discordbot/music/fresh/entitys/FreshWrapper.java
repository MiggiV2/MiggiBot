package de.mymiggi.discordbot.music.fresh.entitys;

public class FreshWrapper
{
	private boolean success;
	private String message;
	private FreshSongsWrapper data;

	public FreshWrapper(boolean success, String message, FreshSongsWrapper data)
	{
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public FreshWrapper()
	{
	}

	public boolean isSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public FreshSongsWrapper getData()
	{
		return data;
	}

	public void setData(FreshSongsWrapper data)
	{
		this.data = data;
	}

}
