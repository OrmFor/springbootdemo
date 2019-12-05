package com.jlkj.web.shopnew.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.common.base.Strings;

/**
 * 工具类-日期处理
 */
public class DateUtil {

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static Date getNow() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();
		return currDate;
	}

	/**
	 * dateStr转换为指定格式的date
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date dateStr(String dateStr, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * date转换为指定格式的dateStr
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateStr(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str = format.format(date);
		return str;
	}

	/**
	 * date转换为字符串 MM月dd日 hh:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr(Date date) {
		return dateStr(date, "MM月dd日 hh:mm");
	}

	/**
	 * date转换为字符串 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr2(Date date) {
		return dateStr(date, "yyyy-MM-dd");
	}

	/**
	 * date转换为字符串 yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr3(Date date) {
		return dateStr(date, "yyyyMMddHHmmss");
	}

	/**
	 * date转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr4(Date date) {
		return dateStr(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * date转换为字符串 yyyy年MM月dd日 HH时mm分ss秒
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr5(Date date) {
		return dateStr(date, "yyyy年MM月dd日 HH时mm分ss秒");
	}

	/**
	 * date转换为字符串 yyyy年MM月dd日
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr6(Date date) {
		return dateStr(date, "yyyy年MM月dd日");
	}

	/**
	 * date转换为字符串 yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr7(Date date) {
		return dateStr(date, "yyyyMMdd");
	}

	/**
	 * date转换为字符串 MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr8(Date date) {
		return dateStr(date, "MM-dd");
	}

	/**
	 * date转换为字符串 yyyyMMddHHmmssSSS
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr9(Date date) {
		return dateStr(date, "yyyyMMddHHmmssSSS");
	}

	/**
	 * date转换为字符串 yyyy.MM.dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr10(Date date) {
		return dateStr(date, "yyyy.MM.dd");
	}

	/**
	 * date转换为字符串 yyyy.MM.dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String dateStr11(Date date) {
		return dateStr(date, "yyyy.MM.dd HH:mm");
	}

	/**
	 * 获取date1到date2时间之间时间戳字符串，如果date1<date2，为0
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static String getTimesBetween(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return "0";
		} else {
			return String.valueOf(date1.getTime() < date2.getTime() ? 0 : (date1.getTime() - date2.getTime()) / 1000);
		}
	}

	/**
	 * date转换为时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static long getTime(Date date) {
		return date.getTime() / 1000;
	}

	/**
	 * date转换为时间戳字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimes(Date date) {
		if (date == null) {
			return "0";
		} else {
			return String.valueOf(date.getTime() / 1000);
		}
	}

	/**
	 * 时间戳转换为date
	 * 
	 * @param times
	 * @return
	 */
	public static Date getDate(String times) {
		long time = Long.parseLong(times);
		return new Date(time * 1000);
	}

	/**
	 * 获取当前时间时间戳
	 * 
	 * @return
	 */
	public static long getNowTime() {
		return (System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取当前时间时间戳字符串
	 * 
	 * @return
	 */
	public static String getNowTimeStr() {
		String str = Long.toString(System.currentTimeMillis() / 1000);
		return str;
	}

	/**
	 * 时间戳转换为字符串 MM月dd日 hh:mm
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr(String times) {
		return dateStr(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyy-MM-dd
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr2(String times) {
		return dateStr2(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyyMMddHHmmss
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr3(String times) {
		return dateStr3(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr4(String times) {
		return dateStr4(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyy年MM月dd日 HH时mm分ss秒
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr5(String times) {
		return dateStr5(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyy年MM月dd日
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr6(String times) {
		return dateStr6(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyyMMdd
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr7(String times) {
		return dateStr7(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 MM-dd
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr8(String times) {
		return dateStr8(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyyMMddHHmmssSSS
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr9(String times) {
		return dateStr9(getDate(times));
	}

	/**
	 * 时间戳转换为字符串 yyyy.MM.dd
	 * 
	 * @param times
	 * @return
	 */
	public static String dateStr10(String times) {
		return dateStr10(getDate(times));
	}

	/**
	 * 取某日期的当前天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * s：yyyy-mm-dd等日期格式
	 * 
	 * @param s
	 * @return
	 */
	public static Date valueOf(String s) {
		final int YEAR_LENGTH = 4;
		final int MONTH_LENGTH = 2;
		final int DAY_LENGTH = 2;
		final int MAX_MONTH = 12;
		final int MAX_DAY = 31;
		int firstDash;
		int secondDash;
		int threeDash = 0;
		int fourDash = 0;
		Date date = null;

		if (s == null) {
			throw new java.lang.IllegalArgumentException();
		}

		firstDash = s.indexOf('-');
		secondDash = s.indexOf('-', firstDash + 1);
		if (s.contains(":")) {
			threeDash = s.indexOf(':');
			fourDash = s.indexOf(':', threeDash + 1);
		}
		if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
			String yyyy = s.substring(0, firstDash);
			String mm = s.substring(firstDash + 1, secondDash);
			String dd = "";
			String hh = "";
			String MM = "";
			String ss = "";
			if (s.contains(":")) {
				dd = s.substring(secondDash + 1, threeDash - 3);
				hh = s.substring(threeDash - 2, threeDash);
				MM = s.substring(threeDash + 1, fourDash);
				ss = s.substring(fourDash + 1);
			} else {
				dd = s.substring(secondDash + 1);
			}
			if (yyyy.length() == YEAR_LENGTH && mm.length() == MONTH_LENGTH && dd.length() == DAY_LENGTH) {
				int year = Integer.parseInt(yyyy);
				int month = Integer.parseInt(mm);
				int day = Integer.parseInt(dd);
				int hour = 0;
				int minute = 0;
				int second = 0;
				if (s.contains(":")) {
					hour = Integer.parseInt(hh);
					minute = Integer.parseInt(MM);
					second = Integer.parseInt(ss);
				}
				if (month >= 1 && month <= MAX_MONTH) {
					int maxDays = MAX_DAY;
					switch (month) {
					// February determine if a leap year or not
					case 2:
						if ((year % 4 == 0 && !(year % 100 == 0)) || (year % 400 == 0)) {
							maxDays = MAX_DAY - 2; // leap year so 29 days in
													// February
						} else {
							maxDays = MAX_DAY - 3; // not a leap year so 28 days
													// in February
						}
						break;
					// April, June, Sept, Nov 30 day months
					case 4:
					case 6:
					case 9:
					case 11:
						maxDays = MAX_DAY - 1;
						break;
					}
					if (day >= 1 && day <= maxDays) {
						Calendar cal = Calendar.getInstance();
						cal.set(year, month - 1, day, hour, minute, second);
						cal.set(Calendar.MILLISECOND, 0);
						date = cal.getTime();
					}
				}
			}
		}
		if (date == null) {
			throw new java.lang.IllegalArgumentException();
		}
		return date;
	}

	/**
	 * 前/后?分钟
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date rollMinute(Date date, int minute) {
		return new Date(date.getTime() + minute * 60 * 1000);
	}

	/**
	 * 输出指定格式的前/后?天
	 * 
	 * @param date
	 * @param day
	 * @param pattern
	 * @return
	 */
	public static String rollDayFormat(Date date, int day, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(rollDay(date, day));
	}

	/**
	 * 前/后?天
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date rollDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 输出指定格式的前/后?月
	 * 
	 * @param date
	 * @param mon
	 * @param pattern
	 * @return
	 */
	public static String rollMonFormat(Date date, int mon, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(rollMon(date, mon));
	}

	/**
	 * 前/后?月
	 * 
	 * @param date
	 * @param mon
	 * @return
	 */
	public static Date rollMon(Date date, int mon) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, mon);
		return cal.getTime();
	}

	/**
	 * 输出指定格式的前/后?年
	 * 
	 * @param date
	 * @param year
	 * @param pattern
	 * @return
	 */
	public static String rollYearFormat(Date date, int year, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(rollYear(date, year));
	}

	/**
	 * 前/后?年
	 * 
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date rollYear(Date date, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		return cal.getTime();
	}

	/**
	 * 输出指定格式的前/后?日期
	 * 
	 * @param date
	 * @param year
	 * @param mon
	 * @param day
	 * @param pattern
	 * @return
	 */
	public static String rollDateFormat(Date date, int year, int mon, int day, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(rollDate(date, year, mon, day));
	}

	/**
	 * 前/后?日期
	 * 
	 * @param date
	 * @param year
	 * @param mon
	 * @param day
	 * @return
	 */
	public static Date rollDate(Date date, int year, int mon, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, mon);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	/**
	 * 获取当天00:00:00时间
	 * 
	 * @return
	 */
	public static Date getIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当天23:59:59时间
	 * 
	 * @return
	 */
	public static Date getLastIntegralTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取某日期00:00:00时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstSecIntegralTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取某日期23:59:59时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastSecIntegralTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取本周日的日期
	 * 
	 * @return
	 */
	public static String getCurrentWeekday() {
		int mondayPlus = DateUtil.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取当前日期与本周日相差的天数
	 * 
	 * @return
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获取今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/**
	 * 获取本周一的日期
	 * 
	 * @return
	 */
	public static String getMondayOFWeek() {
		int mondayPlus = DateUtil.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 获取指定格式的当前月第一天
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getFirstDayOfMonth(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = format.format(cal.getTime());
		return first;
	}

	/**
	 * 获取当前月第一天00:00:00时间
	 * 
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定格式的当月最后一天
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getLastDayOfMonth(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(cal.getTime());
		return last;
	}

	/**
	 * 获取当前月最后一天23:59:59时间
	 * 
	 * @param
	 * @return
	 */
	public static Date getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 日期月份处理
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date timeMonthManage(Date date, int month) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, month);
		return rightNow.getTime();
	}

	/**
	 * 获取指定年月的最后一天23:59:59时间
	 * 
	 * @param yearTime
	 * @param monthTime
	 * @return
	 */
	public static Date monthLastDay(int yearTime, int monthTime) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearTime, monthTime, 0, 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定年月的第一天00:00:00时间
	 * 
	 * @param yearTime
	 * @param monthTime
	 * @return
	 */
	public static Date monthFirstDay(int yearTime, int monthTime) {
		Calendar cal = Calendar.getInstance();
		cal.set(yearTime, monthTime - 1, 1, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间月的第一天00:00:00时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date currMonthFirstDay(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定时间月的最后一天23:59:59时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date currMonthLastDay(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定时间的年
	 * 
	 * @param date
	 * @return
	 */
	public static int getTimeYear(Date date) {
		if (date == null)
			date = DateUtil.getNow();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取指定时间的月
	 * 
	 * @param date
	 * @return
	 */
	public static int getTimeMonth(Date date) {
		if (date == null)
			date = DateUtil.getNow();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定时间的天
	 * 
	 * @param date
	 * @return
	 */
	public static int getTimeDay(Date date) {
		if (date == null)
			date = DateUtil.getNow();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	/**
	 * 获取指定时间天的结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayEndTime(long date) {
		Date day = null;
		if (date <= 0) {
			day = DateUtil.getNow();
		} else {
			day = new Date(date * 1000);
		}
		Calendar cal = Calendar.getInstance();
		if (day != null) {
			cal.setTimeInMillis(day.getTime());
		}
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 23, 59, 59);
		return cal.getTime();
	}

	/**
	 * 获取指定时间天的开始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDayStartTime(long date) {
		Date day = null;
		if (date <= 0) {
			day = DateUtil.getNow();
		} else {
			day = new Date(date * 1000);
		}
		Calendar cal = Calendar.getInstance();
		if (day != null) {
			cal.setTimeInMillis(day.getTime());
		}
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 获取19位的格式时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date getDateByFullDateStr(String dateStr) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.parse(dateStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		try {
			Date d1 = format.parse(DateUtil.dateStr7(date1));
			Date d2 = format.parse(DateUtil.dateStr7(date2));
			cal.setTime(d1);
			long time1 = cal.getTimeInMillis();
			cal.setTime(d2);
			long time2 = cal.getTimeInMillis();
			return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 计算两个指定格式日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2, String format) {
		if (Strings.isNullOrEmpty(format))
			format = "yyyyMMdd";
		Date d1 = dateStr(dateStr(date1, format), format);
		Date d2 = dateStr(dateStr(date2, format), format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(d2);
		long time2 = cal.getTimeInMillis();
		return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
	}

	/**
	 * 
	 * @Title isValidDate
	 * @Description 校验字符串是否为指定时间格式
	 * @author wzq
	 * @date 2017年7月11日 下午6:28:06
	 * @param str
	 * @param pattern
	 * @return
	 * @return boolean
	 */
	public static boolean isValidDate(String str, String pattern) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

}
