/**
 * 
 */
package com.tmm.frm.helper;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

/**
 * Class to hold methods for all core util/date methods
 * 
 * @author robert.hinds
 * 
 */
@Service("dateService")
public class DateService {

	private static final String YEARS = " years ago";
	private static final String MONTHS = " months ago";
	private static final String DAYS = " days ago";
	private static final String HOURS = " hours ago";
	private static final String MINUTES = " minutes ago";
	private static final String YEAR = " year ago";
	private static final String MONTH = " month ago";
	private static final String DAY = " day ago";
	private static final String HOUR = " hour ago";
	private static final String MINUTE = " minute ago";
	private static final String NOW = "just now";

	private static final String FUTURE_YEARS = " years";
	private static final String FUTURE_MONTHS = " months";
	private static final String FUTURE_DAYS = " days";
	private static final String FUTURE_HOURS = " hours";
	private static final String FUTURE_MINUTES = " minutes";
	private static final String FUTURE_YEAR = " year";
	private static final String FUTURE_MONTH = " month";
	private static final String FUTURE_DAY = " day";
	private static final String FUTURE_HOUR = " hour";
	private static final String FUTURE_MINUTE = " minute";

	/**
	 * Method to calculate a logical string representing the day since or untill
	 * a given date
	 * 
	 * @param futureDate
	 * @return
	 */
	public String getTimeAgo(Date futureDate) {
		if (futureDate == null) {
			return " N/A";
		}

		Calendar current = Calendar.getInstance();
		Calendar deadline = Calendar.getInstance();
		deadline.setTime(futureDate);

		int yearsAgo = current.get(Calendar.YEAR) - deadline.get(Calendar.YEAR);
		if (yearsAgo != 0) {
			if (yearsAgo > 1) {
				return yearsAgo + YEARS;
			} else if (yearsAgo < -1) {
				return (yearsAgo * -1) + FUTURE_YEARS;
			} else if (yearsAgo < 0) {
				return (yearsAgo * -1) + FUTURE_YEAR;
			} else {
				return yearsAgo + YEAR;
			}
		}

		int monthsAgo = current.get(Calendar.MONTH) - deadline.get(Calendar.MONTH);
		if (monthsAgo != 0) {
			if (monthsAgo > 1) {
				return monthsAgo + MONTHS;
			} else if (monthsAgo < -1) {
				return (monthsAgo * -1) + FUTURE_MONTHS;
			} else if (monthsAgo < 0) {
				return (monthsAgo * -1) + FUTURE_MONTH;
			} else {
				return monthsAgo + MONTH;
			}
		}

		int daysAgo = current.get(Calendar.DATE) - deadline.get(Calendar.DATE);
		if (daysAgo != 0) {
			if (daysAgo > 1) {
				return daysAgo + DAYS;
			} else if (daysAgo < -1) {
				return (daysAgo * -1) + FUTURE_DAYS;
			} else if (daysAgo < 0) {
				return (daysAgo * -1) + FUTURE_DAY;
			} else {
				return daysAgo + DAY;
			}
		}

		int current24Hour = current.get(Calendar.HOUR_OF_DAY);
		if (current24Hour == 12 && current.get(Calendar.AM_PM) == Calendar.PM) {
			current24Hour = 24;
		}
		int deadline24Hour = deadline.get(Calendar.HOUR_OF_DAY);
		if (deadline24Hour == 12 && deadline.get(Calendar.AM_PM) == Calendar.PM) {
			deadline24Hour = 24;
		}
		int hoursAgo = current24Hour - deadline24Hour;
		if (hoursAgo != 0) {
			if (hoursAgo > 1) {
				return hoursAgo + HOURS;
			} else if (hoursAgo < -1) {
				return (hoursAgo * -1) + FUTURE_HOURS;
			} else if (hoursAgo < 0) {
				return (hoursAgo * -1) + FUTURE_HOUR;
			} else {
				return hoursAgo + HOUR;
			}
		}

		int minsAgo = current.get(Calendar.MINUTE) - deadline.get(Calendar.MINUTE);
		if (minsAgo != 0) {
			if (minsAgo > 1) {
				return minsAgo + MINUTES;
			} else if (minsAgo < -1) {
				return (minsAgo * -1) + FUTURE_MINUTES;
			} else if (minsAgo < 0) {
				return (minsAgo * -1) + FUTURE_MINUTE;
			} else {
				return minsAgo + MINUTE;
			}
		}

		return NOW;
	}

}
