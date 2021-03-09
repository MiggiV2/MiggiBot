package de.mymiggi.webuntis.actions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.webuntis.helper.FormattedUntisTimeAction;
import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class NextLessonForToday
{
	private static Logger logger = LoggerFactory.getLogger(NextLessonForToday.class.getSimpleName());

	public Elements getNextSubjectByTime(WebUntisResponse response)
	{
		LessonPeriod[] timeTabelToday = response.getTimetable().get("195");
		int currentTime = new FormattedUntisTimeAction().getWithCustomTime(LocalDateTime.now());
		List<LessonPeriod> unsortedTimeTabelToday = new LessonsManager().getUnsorted(timeTabelToday, LocalDate.now());
		List<LessonPeriod> sortedTimeTabelToday = new LessonsManager().getShortedAndSorted(unsortedTimeTabelToday);
		for (LessonPeriod temp : sortedTimeTabelToday)
		{
			logger.info(temp.getStartTime() + " " + temp.getElements()[1].getSubjekt(response).getLongName());
			if (temp.getStartTime() > currentTime)
			{
				return temp.getElements()[1].getSubjekt(response);
			}
		}
		return null;
	}
}
