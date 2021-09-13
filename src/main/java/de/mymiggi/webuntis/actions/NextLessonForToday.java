package de.mymiggi.webuntis.actions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import de.mymiggi.webuntis.helper.FormattedUntisTimeAction;
import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class NextLessonForToday
{
	public Elements getNextSubjectByTime(WebUntisResponse response)
	{
		LessonPeriod[] timeTabelToday = response.getLessons();
		int currentTime = new FormattedUntisTimeAction().getWithCustomTime(LocalDateTime.now());
		List<LessonPeriod> unsortedTimeTabelToday = new LessonsManager().getUnsorted(timeTabelToday, LocalDate.now());
		List<LessonPeriod> sortedTimeTabelToday = new LessonsManager().getShortedAndSorted(unsortedTimeTabelToday);
		for (LessonPeriod temp : sortedTimeTabelToday)
		{
			if (temp.getStartTime() > currentTime)
			{
				return temp.getElements()[1].getSubjekt(response);
			}
		}
		return null;
	}
}
