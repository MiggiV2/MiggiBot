package de.mymiggi.webuntis.helper;

import java.time.LocalDate;

public class FormattedUntisDateAction
{
	public int get(LocalDate now)
	{
		String month = String.valueOf(now.getMonthValue());
		if (month.length() == 1)
		{
			month = "0" + month;
		}
		String day = String.valueOf(now.getDayOfMonth());
		if (day.length() == 1)
		{
			day = "0" + day;
		}
		String formattedDate = String.valueOf(now.getYear()) + month + day;
		return Integer.parseInt(formattedDate);
	}
}
