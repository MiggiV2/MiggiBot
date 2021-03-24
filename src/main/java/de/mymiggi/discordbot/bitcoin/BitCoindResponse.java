package de.mymiggi.discordbot.bitcoin;

public class BitCoindResponse
{

	private int fastestFee;
	private int halfHourFee;
	private int hourFee;
	private int minimumFee;
	public int getFastestFee()
	{
		return fastestFee;
	}

	public void setFastestFee(int fastestFee)
	{
		this.fastestFee = fastestFee;
	}

	public int getHalfHourFee()
	{
		return halfHourFee;
	}

	public void setHalfHourFee(int halfHourFee)
	{
		this.halfHourFee = halfHourFee;
	}

	public int getHourFee()
	{
		return hourFee;
	}

	public void setHourFee(int hourFee)
	{
		this.hourFee = hourFee;
	}

	public int getMinimumFee()
	{
		return minimumFee;
	}

	public void setMinimumFee(int minimumFee)
	{
		this.minimumFee = minimumFee;
	}

}
