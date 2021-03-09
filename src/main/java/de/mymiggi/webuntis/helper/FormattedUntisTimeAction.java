package de.mymiggi.webuntis.helper;

import java.time.LocalDateTime;

public class FormattedUntisTimeAction
{
	public int get()
	{
		LocalDateTime now = LocalDateTime.now().plusMinutes(5);
		String hour = String.valueOf(now.getHour());
		String minute = String.valueOf(now.getMinute());
		String formattedTime = hour + minute;
		return Integer.parseInt(formattedTime);
	}

	public int getWithCustomTime(LocalDateTime now)
	{
		String hour = String.valueOf(now.getHour());
		String minute = String.valueOf(now.getMinute());
		String formattedTime = hour + minute;
		return Integer.parseInt(formattedTime);
	}

	public String formated(int untisTime)
	{
		String time = String.valueOf(untisTime);
		char[] formattedTime;
		if (time.length() == 3)
		{
			formattedTime = new char[5];
			time.getChars(0, 1, formattedTime, 1);
			formattedTime[0] = '0';
			formattedTime[2] = ':';
			time.getChars(1, 3, formattedTime, 3);
		}
		else
		{
			formattedTime = new char[5];
			time.getChars(0, 2, formattedTime, 0);
			formattedTime[2] = ':';
			time.getChars(2, 4, formattedTime, 3);
		}
		return new String(formattedTime);
	}
}
