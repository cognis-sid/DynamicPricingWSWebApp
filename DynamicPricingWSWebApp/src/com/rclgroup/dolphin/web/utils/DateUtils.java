/*Copyright (c) 2020 RCL| All Rights Reserved  */
package com.rclgroup.dolphin.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class mainly intended for form the date in different format.
 * 
 * @author Cognis Solution
 *
 */
public class DateUtils {
	public static final SimpleDateFormat simpleDateFormatInput = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat simpleDateFormatInputYY_MM = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat simpleDateFormatSQL = new SimpleDateFormat("yyyyMMdd");

	private static final SimpleDateFormat dateFormatDDMMYY = new SimpleDateFormat("dd/MM/yyyy");
	private static final SimpleDateFormat dateFormatDDMMYY_HH_MM_SS = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
	
	
	
	public static String getDateFromDefaultDateStringYYYYMMDD(String defaultDate) {
		String sdfYYYMMDDValue = "";
		if (defaultDate == null)
			return null;
		if (defaultDate.equals(""))
			return null;
		System.out.println("defaultDate  " + defaultDate);

		Date date = new Date();
		try {
			date = simpleDateFormatInput.parse(defaultDate);
			System.out.println("date " + date.toString());
			sdfYYYMMDDValue = simpleDateFormatSQL.format(date);
		} catch (Exception e) {
			
			try {
				date = simpleDateFormatInputYY_MM.parse(defaultDate);
				System.out.println("date " + date.toString());
				sdfYYYMMDDValue = simpleDateFormatSQL.format(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				return null;
			}
			
		}
		return sdfYYYMMDDValue;
	}

	public static String getDefaultDateYYYYMMDD(int numberOfDays) {
		Date date = new Date();
		date.setDate(date.getDate()+numberOfDays);
		return simpleDateFormatSQL.format(date);

	}
	
	

	 
	public static String formateDate(Date date) {

		if (date == null) {
			return "";
		}
		return dateFormatDDMMYY.format(date);

	}
	
	public static String formateDateWithTime(Date date) {

		if (date == null) {
			return "";
		}
		return dateFormatDDMMYY_HH_MM_SS.format(date);

	}
}
