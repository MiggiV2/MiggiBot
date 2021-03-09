package de.mymiggi.webuntis.util;

import java.util.Map;

public class DataValue
{
	private Elements[] elements;
	private Map<String, LessonPeriod[]> elementPeriods;

	public Elements[] getLessonInfos()
	{
		return elements;
	}

	public void setElements(Elements[] elements)
	{
		this.elements = elements;
	}

	public Map<String, LessonPeriod[]> getTimetable()
	{
		return elementPeriods;
	}

	public void setElementPeriodsValue(Map<String, LessonPeriod[]> elementPeriodsValue)
	{
		this.elementPeriods = elementPeriodsValue;
	}
}
