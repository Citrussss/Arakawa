package com.swkj.common.util;

import android.text.TextUtils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

	public static boolean isEarly(int days, long time) {
		return (currentTimeMillis() - time) > (days * 24 * 3600 * 1000);
	}

	public static int currentTimeSecond() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取当前的时间搓
	 */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	public static long[] getTsTimes() {
		long[] times = new long[2];

		Calendar calendar = Calendar.getInstance();

		times[0] = calendar.getTimeInMillis() / 1000;

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		times[1] = calendar.getTimeInMillis() / 1000;

		return times;
	}

	public static String getFormatDatetime(int year, int month, int day) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new GregorianCalendar(year, month, day).getTime());
	}
    /**
     * 获取时间字符串获取日期
     * */
	public static Date getDateFromFormatString(String formatDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(formatDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
     * 获取时间字符串获取日期(精确到秒)
     * */
    public static Date getDateFromOrMmString(String formatDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

	public static long getDataTime(String time){
		Date date = getDateFromOrMmString(time);
		long overTime = date.getTime();
		long nowTime = currentTimeMillis();
		long t = overTime - nowTime;
		if (t<0){
		    return 0;
        }
		return t;
	}

	/**
	 * 获取当前的时间
	 * 
	 * @return 年月日时分秒
	 */
	public static String getNowDatetime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		return (formatter.format(new Date()));
	}

	/**
	 * 计算时间差
	 *
	 * @param starTime 开始时间
	 * @param endTime  结束时间
	 *  type 返回类型   1----天  ==2----时 ==3--天，时。
	 *
	 * @return 返回时间差
	 */
	public static String getTimeDifference(String starTime, String endTime) {
		String           timeString = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		try {
			Date parse  = dateFormat.parse(starTime);
			Date parse1 = dateFormat.parse(endTime);
			long diff = parse.getTime() - parse1.getTime();

           /* Log.e("kele endTime =", "服务器时间：" + parse1);
            Log.e("kele starTime =", "当前时间：" + parse);
            Log.e("kele diff =", "" + diff);*/
			long day  = diff / (24 * 60 * 60 * 1000);
			long hour = (diff / (60 * 60 * 1000) - day * 24);
			long min  = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s    = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			long ms   = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
			// System.out.println(day + "天" + hour + "小时" + min + "分" + s +"秒");
			long   hour1      = diff / (60 * 60 * 1000);
			long   min1       = ((diff / (60 * 1000)) - hour1 * 60);
			if (day >0 ){
				timeString = day + "天";
			}else if (hour > 0){
				timeString = hour + "小时";
			}else if (min>0){
				timeString = min+"分钟";
			} else{
				timeString = "30秒";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timeString;

	}

	public static int getNow() {
		return (int) ((new Date()).getTime() / 1000);
	}

	public static String getNowDateTime(String format) {
		Date date = new Date();

		SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
		return df.format(date);
	}

	public static String getDateString(long milliseconds) {
		return getDateTimeString(milliseconds, "yyyyMMdd");
	}

	public static String getTimeString(long milliseconds) {
		return getDateTimeString(milliseconds, "HHmmss");
	}

	public static String getBeijingNowTimeString(String format) {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

		Date date = new Date(currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
		formatter.setTimeZone(timezone);

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTimeZone(timezone);
		String prefix = gregorianCalendar.get(Calendar.AM_PM) == Calendar.AM ? "上午" : "下午";

		return prefix + formatter.format(date);
	}

	public static String getBeijingNowTime(String format) {
		TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");

		Date date = new Date(currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
		formatter.setTimeZone(timezone);

		return formatter.format(date);
	}

	public static String getDateTimeString(long milliseconds, String format) {
		Date date = new Date(milliseconds);
		SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
		return formatter.format(date);
	}

	public static String getFavoriteCollectTime(long milliseconds) {
		String showDataString = "";
		Date today = new Date();
		Date date = new Date(milliseconds);
		Date firstDateThisYear = new Date(today.getYear(), 0, 0);
		if (!date.before(firstDateThisYear)) {
			SimpleDateFormat dateformatter = new SimpleDateFormat("MM-dd", Locale.getDefault());
			showDataString = dateformatter.format(date);
		} else {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			showDataString = dateformatter.format(date);
		}
		return showDataString;
	}

	public static String getTimeShowString(long milliseconds, boolean abbreviate) {
		String dataString = "";
		String timeStringBy24 = "";

		Date currentTime = new Date(milliseconds);
		Date today = new Date();
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		Date todaybegin = todayStart.getTime();
		Date yesterdaybegin = new Date(todaybegin.getTime() - 3600 * 24 * 1000);
		Date preyesterday = new Date(yesterdaybegin.getTime() - 3600 * 24 * 1000);

		if (!currentTime.before(todaybegin)) {
			dataString = "今天";
		} else if (!currentTime.before(yesterdaybegin)) {
			dataString = "昨天";
		} else if (!currentTime.before(preyesterday)) {
			dataString = "前天";
		} else if (isSameWeekDates(currentTime, today)) {
			dataString = getWeekOfDate(currentTime);
		} else {
			SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			dataString = dateformatter.format(currentTime);
		}

		SimpleDateFormat timeformatter24 = new SimpleDateFormat("HH:mm", Locale.getDefault());
		timeStringBy24 = timeformatter24.format(currentTime);

		if (abbreviate) {
			if (!currentTime.before(todaybegin)) {
				return getTodayTimeBucket(currentTime);
			} else {
				return dataString;
			}
		} else {
			return dataString + " " + timeStringBy24;
		}
	}

	/**
	 * 根据不同时间段，显示不同时间
	 *
	 * @param date
	 * @return
	 */
	public static String getTodayTimeBucket(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat timeformatter0to11 = new SimpleDateFormat("KK:mm", Locale.getDefault());
		SimpleDateFormat timeformatter1to12 = new SimpleDateFormat("hh:mm", Locale.getDefault());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if (hour >= 0 && hour < 5) {
			return "凌晨 " + timeformatter0to11.format(date);
		} else if (hour >= 5 && hour < 12) {
			return "上午 " + timeformatter0to11.format(date);
		} else if (hour >= 12 && hour < 18) {
			return "下午 " + timeformatter1to12.format(date);
		} else if (hour >= 18 && hour < 24) {
			return "晚上 " + timeformatter1to12.format(date);
		}
		return "";
	}

	/**
	 * 根据日期获得星期
	 *
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		// String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	public static boolean isSameDay(long time1, long time2) {
		return isSameDay(new Date(time1), new Date(time2));
	}

	public static boolean isSameDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);

		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
				&& cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return sameDay;
	}

	/**
	 * 判断两个日期是否在同一周
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	public static long getSecondsByMilliseconds(long milliseconds) {
		long seconds = new BigDecimal((float) ((float) milliseconds / (float) 1000))
				.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		// if (seconds == 0) {
		// seconds = 1;
		// }
		return seconds;
	}

	public static String secToTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 60) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
			}
		}
		return timeStr;
	}

	public static String secToMinTime(int time) {
		String timeStr = null;
		int hour = 0;
		int minute = 0;
		int second = 0;
		if (time <= 0)
			return "00:00";
		else {
			minute = time / 60;
			if (minute < 99) {
				second = time % 60;
				timeStr = unitFormat(minute) + ":" + unitFormat(second);
			} else {
				return "99:59";
			}
		}
		return timeStr;
	}

	public static String unitFormat(int i) {
		String retStr = null;
		if (i >= 0 && i < 10)
			retStr = "0" + Integer.toString(i);
		else
			retStr = "" + i;
		return retStr;
	}

	public static String getElapseTimeForShow(int milliseconds) {
		StringBuilder sb = new StringBuilder();
		int seconds = milliseconds / 1000;
		if (seconds < 1)
			seconds = 1;
		int hour = seconds / (60 * 60);
		if (hour != 0) {
			sb.append(hour).append("小时");
		}
		int minute = (seconds - 60 * 60 * hour) / 60;
		if (minute != 0) {
			sb.append(minute).append("分");
		}
		int second = (seconds - 60 * 60 * hour - 60 * minute);
		if (second != 0) {
			sb.append(second).append("秒");
		}
		return sb.toString();
	}

	public static String msToTime(long ms) {
		return secToTime((int) (ms / 1000));
	}

	/***
	 * 计算两个时间差，返回的是的毫秒ms
	 *
	 *
	 *
	 * @return long
	 * @param dete1
	 * @param date2
	 * @return
	 */
	public static long calDateDifferent(String dete1, String date2) {
		long diff = 0;
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = dateFormater.get().parse(dete1);
			d2 = dateFormater.get().parse(date2);
			// 毫秒ms
			diff = d2.getTime() - d1.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendlyTime(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}
	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 描述：根据时间返回几天前或几分钟的描述.
	 *
	 * @param strDate
	 *            the str date
	 * @return the string
	 */
	public static String formatDateStr2Desc(String strDate) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c2.setTime(df.parse(strDate));
			c1.setTime(new Date());
			int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
			if (d == 0) {
				int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
				if (h > 0) {
					return h + "小时前";
				} else if (h < 0) {
					return Math.abs(h) + "小时后";
				} else if (h == 0) {
					int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
					if (m > 0) {
						return m + "分钟前";
					} else if (m < 0) {
						return Math.abs(m) + "分钟后";
					} else {
						return "刚刚";
					}
				}
			} else if (d > 0) {
				if (d == 1) {
					return "昨天";
				} else if (d == 2) {
					return "前天";
				}
			} else if (d < 0) {
				if (d == -1) {
					return "明天";
				} else if (d == -2) {
					return "后天";
				}
				return Math.abs(d) + "天后";
			}

			String out = getStringByFormat(strDate, "MM-dd HH:mm");
			if (!TextUtils.isEmpty(out)) {
				return out;
			}
		} catch (Exception e) {
		}

		return strDate;
	}

	/**
	 * 描述：计算两个日期所差的天数.
	 *
	 * @param date1
	 *            第一个时间的毫秒表示
	 * @param date2
	 *            第二个时间的毫秒表示
	 * @return int 所差的天数
	 */
	public static int getOffectDay(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		// 先判断是否同年
		int y1 = calendar1.get(Calendar.YEAR);
		int y2 = calendar2.get(Calendar.YEAR);
		int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
		int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
		int maxDays = 0;
		int day = 0;
		if (y1 - y2 > 0) {
			maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 + maxDays;
		} else if (y1 - y2 < 0) {
			maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 - maxDays;
		} else {
			day = d1 - d2;
		}
		return day;
	}
	/**
	 * 描述：计算两个日期所差的小时数.
	 *
	 * @param date1
	 *            第一个时间的毫秒表示
	 * @param date2
	 *            第二个时间的毫秒表示
	 * @return int 所差的小时数
	 */
	public static int getOffectHour(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
		int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
		int h = 0;
		int day = getOffectDay(date1, date2);
		h = h1 - h2 + day * 24;
		return h;
	}

	/**
	 * 描述：计算两个日期所差的分钟数.
	 *
	 * @param date1
	 *            第一个时间的毫秒表示
	 * @param date2
	 *            第二个时间的毫秒表示
	 * @return int 所差的分钟数
	 */
	public static int getOffectMinutes(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int m1 = calendar1.get(Calendar.MINUTE);
		int m2 = calendar2.get(Calendar.MINUTE);
		int h = getOffectHour(date1, date2);
		int m = 0;
		m = m1 - m2 + h * 60;
		return m;
	}

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
            Date parse = mSimpleDateFormat.parse(strDate);
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format,Locale.getDefault());
            mDateTime = mSimpleDateFormat2.format(parse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

}
