package de.mymiggi.webuntis.helper.actions;

import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class GetLessonByID
{
	public Elements get(WebUntisResponse response, int ID) throws Exception
	{
		Elements[] lessons = response.getData().getLessonInfos();
		for (Elements temp : lessons)
		{
			if (temp.getId() == ID)
			{
				return temp;
			}
		}
		throw new Exception("Not found!");
	}
}
