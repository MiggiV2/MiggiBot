package de.mymiggi.webuntis.util;

import java.util.Map;

public class WebUntisResponse
{
	private Data data;
	private String elementId = "360";

	public DataValue getData()
	{
		return data.getResult().getData();
	}

	public Elements[] getLessonInfos()
	{
		return data.getResult().getData().getLessonInfos();
	}

	public Map<String, LessonPeriod[]> getTimetables()
	{
		return data.getResult().getData().getTimetable();
	}

	public void setData(Data data)
	{
		this.data = data;
	}

	public LessonPeriod[] getLessons()
	{
		return getTimetables().get(elementId);
	}
}
