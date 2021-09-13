package de.mymiggi.discordbot.server.untis.timetable;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.webuntis.actions.LessonsManager;
import de.mymiggi.webuntis.helper.FormattedUntisDateAction;
import de.mymiggi.webuntis.helper.FormattedUntisTimeAction;
import de.mymiggi.webuntis.util.LessonPeriod;
import de.mymiggi.webuntis.util.WebUntisResponse;

public class TimeTableEmbed
{
	public EmbedBuilder build(LocalDate date)
	{
		WebUntisResponse response = BotMainCore.getTimeTableReminderCore().getResponse();
		LessonPeriod[] timeTable = response.getLessons();

		List<LessonPeriod> lessonList = new LessonsManager().getUnsorted(timeTable, date);
		List<LessonPeriod> sortedLessons = new LessonsManager().getShortedAndSortedWithBrakes(lessonList);

		SimpleDateFormat formatter = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");
		String strDate = formatter.format(new Date());
		EmbedBuilder embed = new EmbedBuilder();

		embed.setTitle("Time table " + date.getDayOfWeek().toString())
			.setColor(Color.orange)
			.setFooter(strDate);
		buildEmbed(sortedLessons, response, embed);
		return embed;
	}

	private void buildEmbed(List<LessonPeriod> sortedLessons, WebUntisResponse response, EmbedBuilder embed)
	{
		LessonPeriod lastLesson = null;
		if (sortedLessons.isEmpty())
		{
			embed.setDescription("No subjects for today found!");
		}
		else
		{
			for (LessonPeriod temp : sortedLessons)
			{
				if (lastLesson == null)
				{
					addSubject(temp, embed, response);
				}
				else
				{
					if (lastLesson.getStartTime() != temp.getStartTime())
					{
						addSubject(temp, embed, response);
					}
				}
				lastLesson = temp;
			}
		}
	}

	private void addSubject(LessonPeriod temp, EmbedBuilder embed, WebUntisResponse response)
	{
		/*
		 * temp.getElements()[1].getLongName() == null -> Custom Lesson like
		 * Break!
		 */
		String formattedStartTime = new FormattedUntisTimeAction().formated(temp.getStartTime());
		String formattedEndTime = new FormattedUntisTimeAction().formated(temp.getEndTime());
		String formattedTime = formattedStartTime + "-" + formattedEndTime;
		String subjektName;
		if (temp.getElements()[1].getLongName() != null)
		{
			subjektName = temp.getElements()[1].getLongName() + " :hugging:";
		}
		else
		{
			subjektName = temp.getElements()[1].getSubjekt(response).getLongName();
		}
		if (isCurrentSubject(temp) && temp.getDate() == new FormattedUntisDateAction().get(LocalDate.now()))
		{
			formattedTime = formattedTime + " :point_left:";
			subjektName = "**" + subjektName + "**";

		}
		if (temp.getElements()[1].getLongName() == null)
		{
			embed.addInlineField(formattedTime, subjektName);
		}
		else
		{
			embed.addField(formattedTime, subjektName);
		}
	}

	private boolean isCurrentSubject(LessonPeriod toCheck)
	{
		int time = new FormattedUntisTimeAction().getWithCustomTime(LocalDateTime.now());
		return (toCheck.getStartTime() <= time) && (toCheck.getEndTime() > time);
	}
}
