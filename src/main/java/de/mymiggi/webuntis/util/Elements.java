package de.mymiggi.webuntis.util;

import de.mymiggi.webuntis.helper.actions.GetLessonByID;

public class Elements
{
	private int type;
	private int id;
	private String longName;
	private String name;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getLongName()
	{
		return longName;
	}

	public void setLongName(String longName)
	{
		this.longName = longName;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
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

	public Elements getSubjekt(WebUntisResponse response)
	{
		try
		{
			return new GetLessonByID().get(response, id);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
