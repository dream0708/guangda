package com.dexcoder.commons.utils;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author <a href="mailto:royiwu@hotmail.com">advance.wu</a>
 * @version 1.0
 */
import java.util.*;
import java.text.*;

public class DateUtils {

	public DateUtils() {}

	public static String getYYYY(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}
	
	public static String getMM(){
		SimpleDateFormat sdf=new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}
	
	public static String getDD(){
		SimpleDateFormat sdf=new SimpleDateFormat("dd");
		return sdf.format(new Date());
	}
	
	public static String getYYYYMM(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		return sdf.format(new Date());
	}
	
	public static String getYYYYMMDD(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	public static String getYYYY_MM(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}
	
	public static String getYYYY_MM_DD(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static String getYYYY_MM_DD_HH_MM(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM");
		return sdf.format(new Date());
	}
	
	public static String getYYYY_MM_DD_HH_MM_SS(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		return sdf.format(new Date());
	}
	
	/** * 根据生日计算年龄 */
	public static int getAge(String birth) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (birth.length() ==4) {
			sdf = new SimpleDateFormat("yyyy");
		}else if(birth.length() >=4 && birth.length() <=7){
			sdf = new SimpleDateFormat("yyyy-MM");
		}
		
		Date dateOfBirth = null;
		try {
			dateOfBirth = sdf.parse(birth);
			int age = 0;
			Calendar born = Calendar.getInstance();
			Calendar now = Calendar.getInstance();
			if (dateOfBirth != null) {
				now.setTime(new Date());
				born.setTime(dateOfBirth);
				if (born.after(now)) {
					throw new IllegalArgumentException("Can't be born in the future");
				}
				age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
				if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
					age -= 1;
				}
			}
			return age;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public static String lastDay(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		try {
			Date d = sdf.parse(date + "-01");
			cale.setTime(d);
			cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));  
	        String lastDay = sdf.format(cale.getTime());
	        return lastDay;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
       
		
	}

	/**
	 * 计算是否是季度末
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isSeason(String date) {
		int getMonth = Integer.parseInt(date.substring(5, 7));
		boolean sign = false;
		if (getMonth == 3)
			sign = true;
		if (getMonth == 6)
			sign = true;
		if (getMonth == 9)
			sign = true;
		if (getMonth == 12)
			sign = true;
		return sign;
	}

	/**
	 * 计算从现在开始几天后的时间
	 * 
	 * @param afterDay
	 * @return
	 */
	public static String getDateFromNow(int afterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		date = calendar.getTime();

		return df.format(date);
	}

	/**
	 * 带格式
	 * 
	 * @param afterDay
	 * @param format_string
	 * @return
	 */
	public static String getDateFromNow(int afterDay, String format_string) {
		Calendar calendar = Calendar.getInstance();
		Date date = null;
		DateFormat df = new SimpleDateFormat(format_string);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		date = calendar.getTime();
		return df.format(date);
	}

	/**
	 * 得到当前时间，用于文件名，没有特殊字符，使用yyyyMMddHHmmss格式
	 * 
	 * @param afterDay
	 * @return by tim
	 */
	public static String getNowForFileName(int afterDay) {
		GregorianCalendar calendar = new GregorianCalendar();
		// Date date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + afterDay);
		Date date = calendar.getTime();

		return df.format(date);
	}

	/**
	 * method diffdate 计算两个日期间相隔的日子
	 * 
	 * @param beforDate
	 *            格式:2005-06-20
	 * @param afterDate
	 *            格式:2005-06-21
	 * @return
	 */
	public static int diffDate(String beforeDate, String afterDate) {
		String[] tt = beforeDate.split("-");
		@SuppressWarnings("deprecation")
		Date firstDate = new Date(Integer.parseInt(tt[0]),
				Integer.parseInt(tt[1]) - 1, Integer.parseInt(tt[2]));

		tt = afterDate.split("-");
		@SuppressWarnings("deprecation")
		Date nextDate = new Date(Integer.parseInt(tt[0]),
				Integer.parseInt(tt[1]) - 1, Integer.parseInt(tt[2]));
		return (int) (nextDate.getTime() - firstDate.getTime())
				/ (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取今天的日期的字符串
	 * 
	 * @return
	 */
	public static String getToday() {
		Calendar cld = Calendar.getInstance();
		java.util.Date date = new Date();
		cld.setTime(date);
		int intMon = cld.get(Calendar.MONTH) + 1;
		int intDay = cld.get(Calendar.DAY_OF_MONTH);
		String mons = String.valueOf(intMon);
		String days = String.valueOf(intDay);
		if (intMon < 10)
			mons = "0" + String.valueOf(intMon);
		if (intDay < 10)
			days = "0" + String.valueOf(intDay);
		return String.valueOf(cld.get(Calendar.YEAR)) + "-" + mons + "-" + days;
	}

	/**
	 * 获取当前月份
	 * 
	 * @return 返回字符串 格式：两位数
	 */
	public static String getCurrentMonth() {
		String strmonth = null;
		Calendar cld = Calendar.getInstance();
		java.util.Date date = new Date();
		cld.setTime(date);
		int intMon = cld.get(Calendar.MONTH) + 1;
		if (intMon < 10)
			strmonth = "0" + String.valueOf(intMon);
		else
			strmonth = String.valueOf(intMon);
		date = null;
		return strmonth;
	}

	/**
	 * 获取昨天的日期的字符串
	 */
	public static String getYestoday() {
		Calendar cld = Calendar.getInstance();
		java.util.Date date = new Date();
		cld.setTime(date);
		cld.add(Calendar.DATE, -1);
		int intMon = cld.get(Calendar.MONTH) + 1;
		int intDay = cld.get(Calendar.DAY_OF_MONTH);
		String mons = String.valueOf(intMon);
		String days = String.valueOf(intDay);
		if (intMon < 10)
			mons = "0" + String.valueOf(intMon);
		if (intDay < 10)
			days = "0" + String.valueOf(intDay);
		return String.valueOf(cld.get(Calendar.YEAR)) + "-" + mons + "-" + days;
	}

	
	public static int getWorkDay(String startDate,String endDate){
		int sum = 0;
		int cnt = 0;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = df.parse(startDate);   
			Date d2 = df.parse(endDate);   	
			Long oneDay = 1000 * 60 * 60 * 24l; 
			long end = d2.getTime();
			long startTime = d1.getTime();
			while (startTime <= end) {
		        Date d = new Date(startTime);
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(d);
		        if (cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7) {
		        	cnt++;
				}
		        startTime += oneDay;   
		    }
			return sum - cnt;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cnt;
	}

	
}
