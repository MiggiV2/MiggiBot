package de.mymiggi.webuntis.actions;

import java.time.LocalDate;
import java.util.List;

import de.mymiggi.webuntis.helper.FormattedUntisDateAction;
import de.mymiggi.webuntis.helper.FormattedUntisTimeAction;
import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class CurrentLessonAction
{
	public Elements get(LessonPeriod[] timeTable, WebUntisResponse response)
	{
		int formattedDate = new FormattedUntisDateAction().get(LocalDate.now());
		int formattedTime = new FormattedUntisTimeAction().get();
		for (LessonPeriod temp : timeTable)
		{
			if (temp.getDate() == formattedDate)
			{
				if (temp.getStartTime() <= formattedTime && temp.getEndTime() >= formattedTime)
				{
					return temp.getElements()[1].getSubjekt(response);
				}
			}
		}
		return null;
	}

	public Elements get(List<LessonPeriod> timeTable, WebUntisResponse response)
	{
		int formattedDate = new FormattedUntisDateAction().get(LocalDate.now());
		int formattedTime = new FormattedUntisTimeAction().get();
		for (LessonPeriod temp : timeTable)
		{
			if (temp.getDate() == formattedDate)
			{
				if (temp.getStartTime() <= formattedTime && temp.getEndTime() >= formattedTime)
				{
					return temp.getElements()[1].getSubjekt(response);
				}
			}
		}
		return null;
	}
}
