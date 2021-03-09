package de.mymiggi.discordbot.corona.covid19;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CoronaResponse
{
	private String ID;
	private String Country;
	private String CountryCode;
	private String Province;
	private String City;
	private String CityCode;
	private String Lat;
	private String Lon;
	private String Confirmed;
	private String Deaths;
	private String Recovered;
	private String Active;
	private String Date;
	private int Cases;
	private String Status;
	@SuppressWarnings("unused")
	private String formattedDate;

	public String getID()
	{
		return ID;
	}

	public void setID(String iD)
	{
		ID = iD;
	}

	public String getCountry()
	{
		return Country;
	}

	public void setCountry(String country)
	{
		Country = country;
	}

	public String getCountryCode()
	{
		return CountryCode;
	}

	public void setCountryCode(String countryCode)
	{
		CountryCode = countryCode;
	}

	public String getProvince()
	{
		return Province;
	}

	public void setProvince(String province)
	{
		Province = province;
	}

	public String getCity()
	{
		return City;
	}

	public void setCity(String city)
	{
		City = city;
	}

	public String getCityCode()
	{
		return CityCode;
	}

	public void setCityCode(String cityCode)
	{
		CityCode = cityCode;
	}

	public String getLat()
	{
		return Lat;
	}

	public void setLat(String lat)
	{
		Lat = lat;
	}

	public String getLon()
	{
		return Lon;
	}

	public void setLon(String lon)
	{
		Lon = lon;
	}

	public String getConfirmed()
	{
		return Confirmed;
	}

	public void setConfirmed(String confirmed)
	{
		Confirmed = confirmed;
	}

	public String getDeaths()
	{
		return Deaths;
	}

	public void setDeaths(String deaths)
	{
		Deaths = deaths;
	}

	public String getRecovered()
	{
		return Recovered;
	}

	public void setRecovered(String recovered)
	{
		Recovered = recovered;
	}

	public String getActive()
	{
		return Active;
	}

	public void setActive(String active)
	{
		Active = active;
	}

	public String getDate()
	{
		return Date;
	}

	public void setDate(String date)
	{
		Date = date;
	}

	public int getCases()
	{
		return Cases;
	}

	public void setCases(int cases)
	{
		Cases = cases;
	}

	public String getStatus()
	{
		return Status;
	}

	public void setStatus(String status)
	{
		Status = status;
	}

	public String getFormattedDate() throws ParseException
	{

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		SimpleDateFormat shortFormatter = new SimpleDateFormat("dd.MM");
		Date formattedDate = new Date();

		formattedDate = formatter.parse(getDate());
		return shortFormatter.format(formattedDate);
	}

	public void setFormattedDate(String formattedDate)
	{
		this.formattedDate = formattedDate;
	}
}
