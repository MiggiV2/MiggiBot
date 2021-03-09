package de.mymiggi.webuntis.util;

import de.mymiggi.webuntis.helper.actions.GetLessonByID;

public class LessonPeriod
{
	private int startTime;
	private int endTime;
	private int date;
	private Elements[] elements;

	public int getEndTime()
	{
		return endTime;
	}

	public void setEndTime(int endTime)
	{
		this.endTime = endTime;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}

	public Elements[] getElements()
	{
		return elements;
	}

	public void setElements(Elements[] elements)
	{
		this.elements = elements;
	}

	public int getDate()
	{
		return date;
	}

	public void setDate(int date)
	{
		this.date = date;
	}

	public Elements getSubjekt(WebUntisResponse response)
	{
		try
		{
			return new GetLessonByID().get(response, elements[1].getId());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
