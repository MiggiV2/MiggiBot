package de.mymiggi.webuntis;

import java.io.IOException;

import de.mymiggi.webuntis.helper.actions.SendUntisRequestAction;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class WebUntisClient
{
	public WebUntisResponse getResponse(String untisSchoolName)
	{
		try
		{
			WebUntisResponse response = new SendUntisRequestAction().run(untisSchoolName);
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
