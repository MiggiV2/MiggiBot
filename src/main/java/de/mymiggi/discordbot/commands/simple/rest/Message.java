package de.mymiggi.discordbot.commands.simple.rest;

import java.util.Date;

public class Message
{
	private String name;
	private String authorPin;
	private String mesContent;
	private Date date;
	private String dateStr;
	private int id;

	public Message()
	{
	}

	public Message(String name, String authorPin, String description, int id)
	{
		this.name = name;
		this.authorPin = authorPin;
		this.mesContent = description;
		this.date = new Date();
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAuthorPin()
	{
		return authorPin;
	}

	public void setAuthorPin(String authorPin)
	{
		this.authorPin = authorPin;
	}

	public String getMesContent()
	{
		return mesContent;
	}

	public void setMesContent(String mesContent)
	{
		this.mesContent = mesContent;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getDateStr()
	{
		return dateStr;
	}

	public void setDateStr(String dateStr)
	{
		this.dateStr = dateStr;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
