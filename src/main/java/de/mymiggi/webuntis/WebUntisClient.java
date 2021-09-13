package de.mymiggi.webuntis;

import java.io.IOException;

import de.mymiggi.webuntis.helper.actions.SendUntisRequestAction;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class WebUntisClient
{
	public WebUntisResponse getResponse()
	{
		try
		{
			WebUntisResponse response = new SendUntisRequestAction().run();
			response.getLessonInfos();
			response.getTimetables();
			return response;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
