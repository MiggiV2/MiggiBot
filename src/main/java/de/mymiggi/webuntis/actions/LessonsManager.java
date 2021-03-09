package de.mymiggi.webuntis.actions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.mymiggi.webuntis.helper.FormattedUntisDateAction;
import de.mymiggi.webuntis.util.Elements;
import de.mymiggi.webuntis.util.LessonPeriod;

public class LessonsManager
{
	public List<LessonPeriod> getUnsorted(LessonPeriod[] timeTabel, LocalDate date)
	{
		int formattedDate = new FormattedUntisDateAction().get(date);
		List<LessonPeriod> lessons = new ArrayList<LessonPeriod>();
		for (LessonPeriod temp : timeTabel)
		{
			if (temp.getDate() == formattedDate)
			{
				lessons.add(temp);
			}
		}
		return lessons;
	}

	public List<LessonPeriod> getShortedAndSorted(List<LessonPeriod> lessons)
	{
		/*
		 * Example: Math 8:00 - 8:45 + Math 8:40 - 9:30 -> Math 8:00 - 9:30
		 */
		List<LessonPeriod> lessonsSorted = getAllSortedByTime(lessons);
		LessonPeriod lastLesson = lessonsSorted.get(0);
		for (int i = 1; i < lessonsSorted.size(); i++)
		{
			LessonPeriod temp = lessonsSorted.get(i);
			if (temp.getElements()[1].getId() == lastLesson.getElements()[1].getId())
			{
				LessonPeriod newLesson = new LessonPeriod();
				newLesson.setDate(temp.getDate());
				newLesson.setElements(temp.getElements());
				newLesson.setStartTime(lastLesson.getStartTime());
				newLesson.setEndTime(temp.getEndTime());
				lessonsSorted.set(i - 1, newLesson);
				lessonsSorted.remove(i);
				i--;
			}
			lastLesson = temp;
		}
		return lessonsSorted;
	}

	public List<LessonPeriod> getShortedAndSortedWithBrakes(List<LessonPeriod> lessons)
	{
		List<LessonPeriod> lessonsSorted = getAllSortedByTime(lessons);
		if (!lessonsSorted.isEmpty())
		{
			LessonPeriod lastLesson = lessonsSorted.get(0);
			for (int i = 1; i < lessonsSorted.size(); i++)
			{
				LessonPeriod temp = lessonsSorted.get(i);
				if (temp.getStartTime() != lastLesson.getEndTime() && temp.getStartTime() != lastLesson.getStartTime())
				{
					LessonPeriod newLesson = new LessonPeriod();
					newLesson.setDate(temp.getDate());
					newLesson.setStartTime(lastLesson.getEndTime());
					newLesson.setEndTime(temp.getStartTime());
					Elements[] elements = new Elements[2];
					Elements newElement = new Elements();
					newElement.setLongName("Break");
					elements[1] = newElement;
					newLesson.setElements(elements);
					lessonsSorted.add(i, newLesson);
					i++;
				}
				lastLesson = temp;
			}
		}
		return lessonsSorted;
	}

	private List<LessonPeriod> getAllSortedByTime(List<LessonPeriod> lessons)
	{
		List<LessonPeriod> lessonsSorted = new ArrayList<LessonPeriod>();
		lessons.forEach(lesson -> {
			if (lessonsSorted.isEmpty())
			{
				lessonsSorted.add(lesson);
			}
			else
			{
				boolean added = false;
				for (int i = 0; i < lessonsSorted.size(); i++)
				{
					if (lesson.getStartTime() < lessonsSorted.get(i).getStartTime() && !added)
					{
						lessonsSorted.add(i, lesson);
						added = true;
					}
				}
				if (!added)
				{
					lessonsSorted.add(lesson);
				}
			}
		});
		return lessonsSorted;
	}
}
