package de.mymiggi.discordbot.drivingschool.lessons.create;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.tools.database.util.DrivingLesson;

public class DrivingLessonBuilder
{
	private static Logger logger = LoggerFactory.getLogger(DrivingLessonBuilder.class.getSimpleName());

	public DrivingLesson run(String message) throws Exception
	{
		String[] words = message.split(" ");
		return new DrivingLesson()
			.setDate(getDate(words))
			.setTime(getTime(words))
			.setMeetingPlace(getMeetingPlace(message));
	}

	private String getMeetingPlace(String message)
	{
		if (!message.contains("Treffpunkt: "))
		{
			return "";
		}
		else
		{
			String[] messageParts = message.split("Treffpunkt: ");
			return messageParts[1];
		}
	}

	private LocalTime getTime(String[] words) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		List<LocalTime> timeList = new ArrayList<LocalTime>();
		for (String temp : words)
		{
			try
			{
				Date lessonDate = formatter.parse(temp);
				timeList.add(lessonDate.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalTime());
			}
			catch (ParseException e)
			{
			}
		}
		if (timeList.size() > 1)
		{
			logger.warn("More than one time found!");
		}
		if (timeList.isEmpty())
		{
			throw new Exception("No times found in message!");
		}
		else
		{
			return timeList.get(0);
		}
	}

	private LocalDate getDate(String[] words) throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		for (String temp : words)
		{
			try
			{
				Date lessonDate = formatter.parse(temp);
				dateList.add(lessonDate.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate());
			}
			catch (ParseException e)
			{
			}
		}
		if (dateList.size() > 1)
		{
			logger.warn("More than one date found!");
		}
		if (dateList.isEmpty())
		{
			throw new Exception("No dates found in message!");
		}
		else
		{
			return dateList.get(0);
		}
	}
}
