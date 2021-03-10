package de.mymiggi.discordbot.tools.database.util;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DrivingLesson
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ID;
	private LocalTime time;
	private LocalDate date;
	private String meetingPlace;
	private long userID;

	public LocalTime getTime()
	{
		return time;
	}

	public DrivingLesson setTime(LocalTime time)
	{
		this.time = time;
		return this;
	}

	public LocalDate getDate()
	{
		return date;
	}

	public DrivingLesson setDate(LocalDate date)
	{
		this.date = date;
		return this;
	}

	public String getMeetingPlace()
	{
		return meetingPlace;
	}

	public DrivingLesson setMeetingPlace(String meetingPlace)
	{
		this.meetingPlace = meetingPlace;
		return this;
	}

	public long getUserID()
	{
		return userID;
	}

	public DrivingLesson setUserID(long userID)
	{
		this.userID = userID;
		return this;
	}
}
