package de.mymiggi.webuntis;

import java.io.IOException;

import de.mymiggi.webuntis.helper.actions.SendRequestAction;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class WebUntisClient
{
	public WebUntisResponse getResponse()
	{
		try
		{
			WebUntisResponse response = new SendRequestAction().run();
			response.getLessonInfos();
			response.getTimetable();
			return response;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
