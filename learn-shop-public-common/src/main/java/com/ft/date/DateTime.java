package com.ft.date;

import com.ft.utlis.StringUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * DateTime数据类型. <br>
 */
public class DateTime extends Date implements Serializable {
    private static final long serialVersionUID = 1L;

    // 年用1表示，月用2表示，日用3表示，依次类推
    // 从年开始11-17
    /**
     * <code>年到年</code>
     */
    public static final int YEAR_TO_YEAR = 11;

    /**
     * <code>年到月</code>
     */
    public static final int YEAR_TO_MONTH = 12;

    /**
     * <code>年到日</code>
     */
    public static final int YEAR_TO_DAY = 13;

    /**
     * <code>年到小时</code>
     */
    public static final int YEAR_TO_HOUR = 14;

    /**
     * <code>年到分</code>
     */
    public static final int YEAR_TO_MINUTE = 15;

    /**
     * <code>年到秒</code>
     */
    public static final int YEAR_TO_SECOND = 16;

    /**
     * <code>年到毫秒</code>
     */
    public static final int YEAR_TO_MILLISECOND = 17;

    // 从月开始22-27
    /**
     * <code>月到月</code>
     */
    public static final int MONTH_TO_MONTH = 22;

    /**
     * <code>月到日</code>
     */
    public static final int MONTH_TO_DAY = 23;

    /**
     * <code>月到小时</code>
     */
    public static final int MONTH_TO_HOUR = 24;

    /**
     * <code>月到分</code>
     */
    public static final int MONTH_TO_MINUTE = 25;

    /**
     * <code>月到秒</code>
     */
    public static final int MONTH_TO_SECOND = 26;

    /**
     * <code>月到毫秒</code>
     */
    public static final int MONTH_TO_MILLISECOND = 27;

    // 从日开始33-37
    /**
     * <code>日到日</code>
     */
    public static final int DAY_TO_DAY = 33;

    /**
     * <code>日到小时</code>
     */
    public static final int DAY_TO_HOUR = 34;

    /**
     * <code>日到分</code>
     */
    public static final int DAY_TO_MINUTE = 35;

    /**
     * <code>日到秒</code>
     */
    public static final int DAY_TO_SECOND = 36;

    /**
     * <code>日到毫秒</code>
     */
    public static final int DAY_TO_MILLISECOND = 37;

    // 从小时开始44-47
    /**
     * <code>小时到小时</code>
     */
    public static final int HOUR_TO_HOUR = 44;

    /**
     * <code>小时到分</code>
     */
    public static final int HOUR_TO_MINUTE = 45;

    /**
     * <code>小时到秒</code>
     */
    public static final int HOUR_TO_SECOND = 46;

    /**
     * <code>小时到毫秒</code>
     */
    public static final int HOUR_TO_MILLISECOND = 47;

    // 从分开始55-57
    /**
     * <code>分到分</code>
     */
    public static final int MINUTE_TO_MINUTE = 55;

    /**
     * <code>分到秒</code>
     */
    public static final int MINUTE_TO_SECOND = 56;

    /**
     * <code>分到毫秒</code>
     */
    public static final int MINUTE_TO_MILLISECOND = 57;

    // 从秒开始66-67
    /**
     * <code>秒到秒</code>
     */
    public static final int SECOND_TO_SECOND = 66;

    /**
     * <code>秒到毫秒</code>
     */
    public static final int SECOND_TO_MILLISECOND = 67;

    // 从毫秒开始77-77
    /**
     * <code>毫秒到毫秒</code>
     */
    public static final int MILLISECOND_TO_MILLISECOND = 77;

    private static String delimiter = "-"; // 日期分割符

    private int type = 0;

    private boolean empty = true; // 是否有值

    private static final long MILLS_ONE_SECOND = 1000;

    private static final long MILLS_ONE_MINUTE = 60 * MILLS_ONE_SECOND;

    private static final long MILLS_ONE_HOUR = 60 * MILLS_ONE_MINUTE;

    private static final long MILLS_ONE_DAY = 24 * MILLS_ONE_HOUR;

    /**
     * DateTime的构造函数,不含任何值. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime startDate = new DateTime();
     * </pre>
     */
    public DateTime() {
        this((Date) null);
    }

    /**
     * DateTime的构造函数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime startDate = new DateTime(new java.util.Date());
     * <br>
     * DateTime startDate = new DateTime((java.util.Date)null);
     * <br>
     * </pre>
     *
     * @param date 一个java.util.Date对象
     */
    public DateTime(Date date) {
        this(date, YEAR_TO_DAY);
    }

    /**
     * DateTime的构造函数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime startDate = new DateTime(&quot;2004/02/01&quot;);
     * <br>
     * DateTime startDate = new DateTime(&quot;2004-2-1&quot;);
     * <br>
     * DateTime startDate = new DateTime((String)null);
     * <br>
     * </pre>
     *
     * @param dateString 一个表示日期的字符串
     */
    public DateTime(String dateString) {
        this(dateString, YEAR_TO_DAY);
    }

    /**
     * DateTime的构造函数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime startDate = new DateTime(&quot;2004-2-1&quot;, DateTime.YEAR_TO_DAY);
     * <br>
     * DateTime startDate = new DateTime(&quot;2004-02-01 11:32&quot;, DateTime.YEAR_TO_MINUTE);
     * <br>
     * DateTime startDate = new DateTime(&quot;2004/02/01 11:32&quot;, DateTime.YEAR_TO_MINUTE);
     * <br>
     * </pre>
     *
     * @param dateString 一个表示日期和时间的字符串
     * @param type       DateTime类型
     */
    public DateTime(String dateString, int type) {
        setTime(dateString, type);
    }

    /**
     * 设置时间
     *
     * @param dateTimeString 日期时间字符串
     * @param type           日期时间类型
     */
    private void setTime(String dateTimeString, int type) {
        if (dateTimeString == null || dateTimeString.trim().length() == 0) {
            empty = true;
            return;
        }
        try {
            if (dateTimeString.length() >= 8 && dateTimeString.indexOf("-") == -1 && dateTimeString.indexOf("/") == -1) {
                String year = dateTimeString.substring(0, 4);
                String month = dateTimeString.substring(4, 6);
                String dayEtc = dateTimeString.substring(6, dateTimeString.length());
                dateTimeString = year + "-" + month + "-" + dayEtc;
            }
            dateTimeString = correct(dateTimeString);
            SimpleDateFormat dateFormat = getDateFormat(type);
            Date date = dateFormat.parse(dateTimeString);
            this.setTime(date.getTime());
            empty = false;
        } catch (ParseException e) {
            empty = true;
            throw new IllegalArgumentException("unable to parse " + dateTimeString);
        }
        this.type = type;
        check(this, type);
    }

    /**
     * 设置时间
     *
     * @param date 日期对象
     * @param type 日期时间类型
     */
    private void setTime(Date date, int type) {
        if (date == null) {
            empty = true;
            return;
        }
        setTime(getDateFormat(type).format(date), type);
    }

    /**
     * DateTime的构造函数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime startDate = new DateTime(new Date(), DateTime.YEAR_TO_DAY);
     * <br>
     * </pre>
     *
     * @param date 日期对象
     * @param type DateTime类型
     */
    public DateTime(Date date, int type) {
        setTime(date, type);
    }

    /**
     * DateTime的构造函数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime startDate = new DateTime(new DateTime(new Date(), DateTime.YEAR_TO_SECOND),
     *         DateTime.YEAR_TO_DAY);
     * <br>
     * </pre>
     *
     * @param dateTime 日期时间对象
     * @param type     DateTime类型
     */
    public DateTime(DateTime dateTime, int type) {
        this((Date) dateTime, type);
    }

    /**
     * 返回日期时间对象中的年. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
     * <br>
     *
     *                  dt.getYear() 返回 2004
     *
     *
     * </pre>
     *
     * @return 日期时间对象中的年
     */
    public int getYear() {
        check(this, YEAR_TO_YEAR);
        return Integer.parseInt(getDateFormat(YEAR_TO_YEAR).format(this));
    }

    /**
     * 返回日期时间对象中的月. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
     * <br>
     *
     *                  dt.getMonth() 返回 2
     *
     *
     * </pre>
     *
     * @return 日期时间对象中的月
     */
    public int getMonth() {
        check(this, MONTH_TO_MONTH);
        return Integer.parseInt(getDateFormat(MONTH_TO_MONTH).format(this));
    }

    /**
     * 返回日期时间对象中的日. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
     * <br>
     *
     *                  dt.getDay() 返回 3
     *
     *
     * </pre>
     *
     * @return 日期时间对象中的日
     */
    public int getDay() {
        check(this, DAY_TO_DAY);
        return Integer.parseInt(getDateFormat(DAY_TO_DAY).format(this));
    }

    /**
     * 返回日期时间对象中的时. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
     * <br>
     *
     *                  dt.getHour() 返回 9
     *
     *
     * </pre>
     *
     * @return 日期时间对象中的时
     */
    public int getHour() {
        check(this, HOUR_TO_HOUR);
        return Integer.parseInt(getDateFormat(HOUR_TO_HOUR).format(this));
    }

    /**
     * 返回日期时间对象中的分. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
     * <br>
     *
     *                  dt.getMinute() 返回 34
     *
     *
     * </pre>
     *
     * @return 日期时间对象中的分
     */
    public int getMinute() {
        check(this, MINUTE_TO_MINUTE);
        return Integer.parseInt(getDateFormat(MINUTE_TO_MINUTE).format(this));
    }

    /**
     * 返回日期时间对象中的秒. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = new DateTime(&quot;2004-02-03 09:34:52&quot;, DateTime.YEAR_TO_SECOND);
     * <br>
     *
     *                  dt.getSecond() 返回 52
     *
     *
     * </pre>
     *
     * @return 日期时间对象中的秒
     */
    public int getSecond() {
        check(this, SECOND_TO_SECOND);
        return Integer.parseInt(getDateFormat(SECOND_TO_SECOND).format(this));
    }

    /**
     * 设置DateTime类默认采用的日期分割符，默认为'-'
     *
     * @param delimiter 日期分割符
     */
    public static void setDateDelimiter(String delimiter) {
        DateTime.delimiter = delimiter;
    }

    /**
     * 获取DateTime类默认采用的日期分割符，默认为'-'
     *
     * @return 日期分割符
     */
    public static String getDateDelimiter() {
        return delimiter;
    }

    /**
     * 得到DateFormat
     *
     * @param type DateTime类型
     * @return type对应的DateFormat
     */
    private static SimpleDateFormat getDateFormat(int type) {
        SimpleDateFormat formatter;
        String pattern = "";
        switch (type) {
            // 从年开始
            case YEAR_TO_YEAR:
                pattern = "yyyy";
                break;
            case YEAR_TO_MONTH:
                pattern = "yyyy" + delimiter + "MM";
                break;
            case YEAR_TO_DAY:
                pattern = "yyyy" + delimiter + "MM" + delimiter + "dd";
                break;
            case YEAR_TO_HOUR:
                pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH";
                break;
            case YEAR_TO_MINUTE:
                pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm";
                break;
            case YEAR_TO_SECOND:
                pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss";
                break;
            case YEAR_TO_MILLISECOND:
                pattern = "yyyy" + delimiter + "MM" + delimiter + "dd HH:mm:ss.SSS";
                break;
            // 从月开始
            case MONTH_TO_MONTH:
                pattern = "MM";
                break;
            case MONTH_TO_DAY:
                pattern = "MM" + delimiter + "dd";
                break;
            case MONTH_TO_HOUR:
                pattern = "MM" + delimiter + "dd HH";
                break;
            case MONTH_TO_MINUTE:
                pattern = "MM" + delimiter + "dd HH:mm";
                break;
            case MONTH_TO_SECOND:
                pattern = "MM" + delimiter + "dd HH:mm:ss";
                break;
            case MONTH_TO_MILLISECOND:
                pattern = "MM" + delimiter + "dd HH:mm:ss.SSS";
                break;
            // 从日开始
            case DAY_TO_DAY:
                pattern = "dd";
                break;
            case DAY_TO_HOUR:
                pattern = "dd HH";
                break;
            case DAY_TO_MINUTE:
                pattern = "dd HH:mm";
                break;
            case DAY_TO_SECOND:
                pattern = "dd HH:mm:ss";
                break;
            case DAY_TO_MILLISECOND:
                pattern = "dd HH:mm:ss.SSS";
                break;
            // 从小时开始
            case HOUR_TO_HOUR:
                pattern = "HH";
                break;
            case HOUR_TO_MINUTE:
                pattern = "HH:mm";
                break;
            case HOUR_TO_SECOND:
                pattern = "HH:mm:ss";
                break;
            case HOUR_TO_MILLISECOND:
                pattern = "HH:mm:ss.SSS";
                break;
            // 从分开始
            case MINUTE_TO_MINUTE:
                pattern = "mm";
                break;
            case MINUTE_TO_SECOND:
                pattern = "mm:ss";
                break;
            case MINUTE_TO_MILLISECOND:
                pattern = "mm:ss.SSS";
                break;
            // 从秒开始
            case SECOND_TO_SECOND:
                pattern = "ss";
                break;
            case SECOND_TO_MILLISECOND:
                pattern = "ss.SSS";
                break;
            // 从毫秒开始
            case MILLISECOND_TO_MILLISECOND:
                pattern = "SSS";
                break;
            default:
                throw new IllegalArgumentException(type + " is not support");
        }
        formatter = new SimpleDateFormat(pattern);
        return formatter;
    }

    /**
     * 返回DateTime对象的字符串值. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  new DateTime().toString() 返回 &quot;&quot;
     *
     *
     * </pre>
     *
     * @return 一个表示日期和/或时间的字符串
     */
    public String toString() {
        if (empty == true) {
            return "";
        }
        SimpleDateFormat dateFormat = getDateFormat(type);
        return dateFormat.format(this);
    }

    /**
     * 根据类型返回DateTime对象的字符串值. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  new DateTime().toString() 返回 &quot;&quot;
     *
     *
     * </pre>
     *
     * @param type 类型
     * @return 根据类型返回一个表示日期和/或时间的字符串
     */
    public String toString(int type) {
        if (empty == true) {
            return "";
        }
        // check(this, type);
        SimpleDateFormat dateFormat = getDateFormat(type);
        return dateFormat.format(this);
    }

    /**
     * 返回当前日期和时间. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     * DateTime dt = DateTime().current();
     * </pre>
     *
     * @return 返回表示当前日期和时间的DateTime对象
     */
    public static DateTime current() {
        SimpleDateFormat dateFormat = getDateFormat(YEAR_TO_MILLISECOND);
        return new DateTime(dateFormat.format(new Date()), YEAR_TO_MILLISECOND);
    }

    /**
     * 返回当开始时间、小时与结束时间、小时相差的年数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  示例1：
     *                  DateTime startDate = new DateTime(&quot;2004/02/01&quot;);
     *                  DateTime endDate = new DateTime(&quot;2005/02/01&quot;);
     *                  DateTime.intervalYear(startDate,0,endDate,24) 返回 2
     *
     *
     * <br>
     *
     *                  示例2：
     *                  DateTime startDate = new DateTime(&quot;2004/09/18&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/09/18&quot;);
     *                  DateTime.intervalYear(startDate,11,endDate,2) 返回 -1
     *
     *
     * <br>
     *
     *                  示例3：
     *                  DateTime startDate = new DateTime(&quot;2004/04/01&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/04/01&quot;);
     *                  DateTime.intervalYear(startDate,1,endDate,1) 返回 0
     *
     *
     * <br>
     * </pre>
     *
     * @param startDate 开始时间(年到日型)
     * @param startHour 开始小时
     * @param endDate   结束时间(年到日型)
     * @param endHour   结束小时
     * @return 相差的天数
     */
    public static int intervalYear(DateTime startDate, int startHour, DateTime endDate, int endHour) {
        if (startDate.getType() != YEAR_TO_DAY) {
            throw new IllegalArgumentException("startDate is not a validate DateTime which type is YEAR_TO_DAY");
        }
        if (endDate.getType() != YEAR_TO_DAY) {
            throw new IllegalArgumentException("endDate is not a validate DateTime which type is YEAR_TO_DAY");
        }
        startDate = new DateTime(startDate + " " + startHour, DateTime.YEAR_TO_HOUR);
        endDate = new DateTime(endDate + " " + endHour, DateTime.YEAR_TO_HOUR);
        int yearDiff = endDate.getYear() - startDate.getYear();
        if (endDate.getMonth() > startDate.getMonth()) {
            yearDiff++;
        } else if (endDate.getMonth() < startDate.getMonth()) {
            yearDiff--;
        } else {
            if (endDate.getDay() > startDate.getDay()) {
                yearDiff++;
            } else if (endDate.getDay() < startDate.getDay()) {
                yearDiff--;
            } else {
                if (endHour > startHour) {
                    yearDiff++;
                } else if (endHour < startHour) {
                    yearDiff--;
                }
            }
        }
        return yearDiff;
    }

    /**
     * 返回当开始时间、小时与结束时间、小时相差的月数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  示例1：
     *                  DateTime startDate = new DateTime(&quot;2004/02/01&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/02/01&quot;);
     *                  DateTime.intervalMonth(startDate,0,endDate,24) 返回 1
     *
     *
     * <br>
     *
     *                  示例2：
     *                  DateTime startDate = new DateTime(&quot;2004/09/18&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/09/18&quot;);
     *                  DateTime.intervalMonth(startDate,3,endDate,2) 返回 -1
     *
     *
     * <br>
     *
     *                  示例3：
     *                  DateTime startDate = new DateTime(&quot;2004/04/01&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/04/01&quot;);
     *                  DateTime.intervalMonth(startDate,1,endDate,1) 返回 0
     *
     *
     * <br>
     * </pre>
     *
     * @param startDate 开始时间(年到日型)
     * @param startHour 开始小时
     * @param endDate   结束时间(年到日型)
     * @param endHour   结束小时
     * @return 相差的天数
     */
    public static int intervalMonth(DateTime startDate, int startHour, DateTime endDate, int endHour) {
        if (startDate.getType() != YEAR_TO_DAY) {
            throw new IllegalArgumentException("startDate is not a validate DateTime which type is YEAR_TO_DAY");
        }
        if (endDate.getType() != YEAR_TO_DAY) {
            throw new IllegalArgumentException("endDate is not a validate DateTime which type is YEAR_TO_DAY");
        }
        startDate = new DateTime(startDate + " " + startHour, DateTime.YEAR_TO_HOUR);
        endDate = new DateTime(endDate + " " + endHour, DateTime.YEAR_TO_HOUR);
        int monthDiff = (endDate.getYear() - startDate.getYear()) * 12;
        if (endDate.getMonth() > startDate.getMonth()) {
            monthDiff += endDate.getMonth() - startDate.getMonth();
            if (endDate.getDay() > startDate.getDay()) {
                monthDiff++;
            } else if (endDate.getDay() < startDate.getDay()) {
            } else {
                if (endDate.getHour() > startDate.getHour()) {
                    monthDiff++;
                }
            }
        } else if (endDate.getMonth() < startDate.getMonth()) {
            monthDiff += endDate.getMonth() - startDate.getMonth();
            if (endDate.getDay() > startDate.getDay()) {
            } else if (endDate.getDay() < startDate.getDay()) {
                monthDiff--;
            } else {
                if (endDate.getHour() > startDate.getHour()) {
                    monthDiff++;
                } else if (endDate.getHour() < startDate.getHour()) {
                    monthDiff--;
                }
            }
        } else {
            if (endDate.getDay() > startDate.getDay()) {
                monthDiff++;
            } else if (endDate.getDay() < startDate.getDay()) {
                monthDiff--;
            } else {
                if (endDate.getHour() > startDate.getHour()) {
                } else if (endDate.getHour() < startDate.getHour()) {
                    monthDiff--;
                }
            }
        }
        return monthDiff;
    }

    /**
     * 返回当开始时间、小时与结束时间、小时相差的天数. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  示例1：
     *                  DateTime startDate = new DateTime(&quot;2004/02/01&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/02/03&quot;);
     *                  DateTime.intervalDay(startDate,0,endDate,24) 返回 3
     *
     *
     * <br>
     *
     *                  示例2：
     *                  DateTime startDate = new DateTime(&quot;2004/09/18&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/09/18&quot;);
     *                  DateTime.intervalDay(startDate,11,endDate,2) 返回 -1
     *
     *
     * <br>
     *
     *                  示例3：
     *                  DateTime startDate = new DateTime(&quot;2004/04/01&quot;);
     *                  DateTime endDate = new DateTime(&quot;2004/04/01&quot;);
     *                  DateTime.intervalDay(startDate,1,endDate,1) 返回 0
     *
     *
     * <br>
     * </pre>
     *
     * @param startDate 开始时间(年到日型)
     * @param startHour 开始小时
     * @param endDate   结束时间(年到日型)
     * @param endHour   结束小时
     * @return 相差的天数
     */
    public static int intervalDay(DateTime startDate, int startHour, DateTime endDate, int endHour) {
        if (startDate.getType() != YEAR_TO_DAY) {
            throw new IllegalArgumentException("startDate is not a validate DateTime which type is YEAR_TO_DAY");
        }
        if (endDate.getType() != YEAR_TO_DAY) {
            throw new IllegalArgumentException("endDate is not a validate DateTime which type is YEAR_TO_DAY");
        }
        long diffTime = (endDate.getTime() + endHour * MILLS_ONE_HOUR) - (startDate.getTime() + startHour * MILLS_ONE_HOUR);
        int diffDay = (int) (diffTime / MILLS_ONE_DAY);
        long diffT = diffTime - diffDay * MILLS_ONE_DAY;
        if (diffT > 0) {
            diffDay++;
        } else if (diffT < 0) {
            diffDay--;
        }
        return diffDay;
    }

    /**
     * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定天数之后的日期. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  示例1：
     *                  DateTime dt = new DateTime(&quot;2004/02/01&quot;);
     *                  dt.addDate(5) 返回 new DateTime(&quot;2004/02/06&quot;);
     *
     *
     * <br>
     *
     *                  示例2：
     *                  DateTime dt = new DateTime(&quot;2004/02/01&quot;);
     *                  dt.addDate(-3) 返回 new DateTime(&quot;2004/01/29&quot;);
     *
     *
     * <br>
     *
     *                  示例3：
     *                  DateTime dt = new DateTime(&quot;2004/02/01&quot;);
     *                  dt.addDay(0) 返回 new DateTime(&quot;2004/02/01&quot;);
     *
     *
     * <br>
     * </pre>
     *
     * @param day 日期数
     * @return 新的日期对象
     */
    public DateTime addDay(int day) {
        DateTime dt = new DateTime(this.toString());
        dt.setTime(getTime() + day * MILLS_ONE_DAY);
        return dt;
    }

    /**
     * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定月数之后的日期. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  DateTime dt = new DateTime(&quot;2004/02/14&quot;);
     *                  dt.addMonth(5) 返回 new DateTime(&quot;2004/07/14&quot;);
     *
     *
     * </pre>
     *
     * @param iMonth 月数
     * @return 新的日期对象
     */
    public DateTime addMonth(int iMonth) {
        DateTime dt = (DateTime) this.clone();
        GregorianCalendar gval = new GregorianCalendar();
        gval.setTime(dt);
        gval.add(Calendar.MONTH, iMonth);
        dt.setTime(gval.getTime().getTime());
        return dt;
    }

    /**
     * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定年数之后的日期. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  示例1：
     *                  DateTime dt = new DateTime(&quot;2004/02/14&quot;);
     *                  dt.addYear(1) 返回 new DateTime(&quot;2005/02/14&quot;);
     *
     *
     * </pre>
     *
     * @param iYear 年数
     * @return 新的日期对象
     */
    public DateTime addYear(int iYear) {
        DateTime dt = (DateTime) this.clone();
        GregorianCalendar gval = new GregorianCalendar();
        gval.setTime(dt);
        gval.add(Calendar.YEAR, iYear);
        dt.setTime(gval.getTime().getTime());
        return dt;
    }

    /**
     * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定小时数之后的日期. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  示例1：
     *                  DateTime dt = new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
     *                  dt.addDate(5) 返回 new DateTime(&quot;2004/02/01 05&quot;,DateTime.YEAR_TO_HOUR);
     *
     *
     * <br>
     *
     *                  示例2：
     *                  DateTime dt = new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
     *                  dt.addDate(-3) 返回 new DateTime(&quot;2004/02/31 21&quot;,DateTime.YEAR_TO_HOUR);
     *
     *
     * <br>
     *
     *                  示例3：
     *                  DateTime dt = new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
     *                  dt.addDate(0) 返回 new DateTime(&quot;2004/02/01 00&quot;,DateTime.YEAR_TO_HOUR);
     *
     *
     * <br>
     * </pre>
     *
     * @param hour 小时数
     * @return 新的日期对象
     */
    public DateTime addHour(int hour) {
        DateTime dt = (DateTime) this.clone();
        dt.setTime(getTime() + hour * MILLS_ONE_HOUR);
        return dt;
    }

    /**
     * 返回一个新的日期对象，新的日期对象是当前日期对象加上指定分钟数之后的日期. <br>
     * <br>
     *
     * @param minute 分钟数
     * @return 新的日期对象
     */
    public DateTime addMinute(int minute) {
        DateTime dt = (DateTime) this.clone();
        dt.setTime(getTime() + minute * MILLS_ONE_MINUTE);
        return dt;
    }

    /**
     * 返回日期类型属性
     *
     * @return 返回日期类型属性 type.
     */
    public int getType() {
        return type;
    }

    /**
     * 修正数据.即将'/','-'替换成当前日期分隔符
     *
     * @param dateString 传入日期串
     * @return 修正后的日期串
     */
    private static String correct(String dateString) {
        String resultString = dateString;
        if (dateString.indexOf("/") > -1) {
            resultString = StringUtils.replace(dateString, "/", delimiter);
        }
        if (dateString.indexOf("-") > -1) {
            resultString = StringUtils.replace(dateString, "/", delimiter);
        }
        return resultString;
    }

    /**
     * 判断是否有值. <br>
     * <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     *                  DateTime startDate = new DateTime();
     *                  startDate.isEmpty() 返回true
     *                  DateTime endDate = new DateTime(new java.util.Date());
     *                  endDate.isEmpty() 返回false
     *
     * </pre>
     *
     * @return 返回属性 empty.
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * 检查数据
     *
     * @param dateTime datetime
     * @param type     类型
     */
    private void check(DateTime dateTime, int type) {
        if (dateTime.isEmpty()) {
            throw new IllegalStateException("DateTime is empty.");
        }
        int types[] = new int[]{YEAR_TO_YEAR, YEAR_TO_MONTH, YEAR_TO_DAY, YEAR_TO_HOUR, YEAR_TO_MINUTE, YEAR_TO_SECOND, YEAR_TO_MILLISECOND,
                MONTH_TO_MONTH, MONTH_TO_DAY, MONTH_TO_HOUR, MONTH_TO_MINUTE, MONTH_TO_SECOND, MONTH_TO_MILLISECOND, DAY_TO_DAY, DAY_TO_HOUR,
                DAY_TO_MINUTE, DAY_TO_SECOND, DAY_TO_MILLISECOND, HOUR_TO_HOUR, HOUR_TO_MINUTE, HOUR_TO_SECOND, HOUR_TO_MILLISECOND,
                MINUTE_TO_MINUTE, MINUTE_TO_SECOND, MINUTE_TO_MILLISECOND, SECOND_TO_SECOND, SECOND_TO_MILLISECOND, MILLISECOND_TO_MILLISECOND};
        boolean isValidType = false;
        for (int i = 0; i < types.length; i++) {
            if (types[i] == type) {
                isValidType = true;
                continue;
            }
        }
        if (isValidType == false) {
            throw new IllegalStateException("this type is not support.");
        }
        // 如果类型一致就无需再检查类型了
        if (dateTime.getType() != type) {
            // 如果datetime类型10位大于type，那是肯定不行的,因为首部越界
            if ((dateTime.getType() / 10) > (type / 10)) {
                throw new IllegalStateException("this type is out of range of this datetime instance.");
            }
            // 如果datetime类型个位小于type个位，那是肯定不行的,因为尾部越界
            if ((dateTime.getType() % 10) < (type % 10)) {
                throw new IllegalStateException("this type is out of range of this datetime instance.");
            }
        }
    }

    /**
     * 时区转换 <br>
     * <b>示例: </b>
     * <p>
     * <pre>
     *
     * // 入参：-1 (西一区) , 2006-12-31 23:20:05, 3 (东三区)
     * // 出参：Mon Jan 01 03:20:05 CST 2007
     * DateTime testDateTime = new DateTime(&quot;2007-01-01 03:20:05&quot;, DateTime.YEAR_TO_SECOND);
     * DateTime sourceDateTime = new DateTime(&quot;2006-12-31 23:20:05&quot;, DateTime.YEAR_TO_SECOND);
     * DateTime targetDateTime = DateTime.timeZoneTransform(sourceDateTime, -1, 3);
     *
     * // 入参：-12 (西十二区) , 2006-09-15 11:50:29 , 12 (东十二区)
     * // 出参：Sat Sep 16 11:50:29 CST 2006
     * testDateTime = new DateTime(&quot;2006-09-16 11:50:29&quot;, DateTime.YEAR_TO_SECOND);
     * sourceDateTime = new DateTime(&quot;2006-09-15 11:50:29&quot;, DateTime.YEAR_TO_SECOND);
     * targetDateTime = DateTime.timeZoneTransform(sourceDateTime, -12, 12);
     *
     * // 入参：2 (东二区) , 2006-11-30 11:01:00 , 8 (东八区)
     * // 出参：Thu Nov 30 17:01:00 CST 2006
     * testDateTime = new DateTime(&quot;2006-11-30 17:01:00&quot;, DateTime.YEAR_TO_SECOND);
     * sourceDateTime = new DateTime(&quot;2006-11-30 11:01:00&quot;, DateTime.YEAR_TO_SECOND);
     * targetDateTime = DateTime.timeZoneTransform(sourceDateTime, 2, 8);
     *
     * // 入参：-9 (西九区) , 2007-02-28 20:12:00 , 1 (东一区)
     * // 出参：Thu Mar 01 06:12:00 CST 2007
     * testDateTime = new DateTime(&quot;2007-03-01 06:12:00&quot;, DateTime.YEAR_TO_SECOND);
     * sourceDateTime = new DateTime(&quot;2007-02-28 20:12:00&quot;, DateTime.YEAR_TO_SECOND);
     * targetDateTime = DateTime.timeZoneTransform(sourceDateTime, -9, 1);
     *
     * </pre>
     *
     * @param sourceDate 源地区日期
     * @param sourceZone 源地区时区
     * @param targetZone 目标地区时区
     * @return 目标时区的日期
     */
    public static DateTime timeZoneTransform(Date sourceDate, int sourceZone, int targetZone) {
        int type = DateTime.YEAR_TO_DAY;
        if (sourceDate instanceof DateTime) {
            type = ((DateTime) sourceDate).getType();
        }
        DateTime resultDateTime = new DateTime(new Date(), type);
        // 计算公式：目标时区时间 = 参考时区时间 + (目标时区 \xA8D 参考时区)*1小时
        long newTime = sourceDate.getTime() + (targetZone - sourceZone) * MILLS_ONE_HOUR;
        resultDateTime.setTime(newTime);
        return resultDateTime;
    }

    /**
     * 计算两个日期相隔的天数。只要传入日期的小时没到24点，就算作为一天。见如下举例： <br>
     * (1) startDate = new DateTime("2002-04-23 00:00:00",
     * DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25 00:00:00",
     * DateTime.YEAR_TO_SECOND); return interval=2. <br>
     * (2) startDate = new DateTime("2002-04-23 24:00:00",
     * DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25 00:00:00",
     * DateTime.YEAR_TO_SECOND); return interval=1. <br>
     * (3) startDate = new DateTime("2002-04-23 10:00:01",
     * DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25 24:00:00",
     * DateTime.YEAR_TO_SECOND); return interval=3. <br>
     * (4) startDate = new DateTime("2002-04-23 00:00:00",
     * DateTime.YEAR_TO_SECOND); endDate = new DateTime("2002-04-25 24:00:00",
     * DateTime.YEAR_TO_SECOND); return interval=3. (5) startDate = new
     * DateTime("2002-04-23 20:20:20", DateTime.YEAR_TO_SECOND); endDate = new
     * DateTime("2002-04-25 10:10:10", DateTime.YEAR_TO_SECOND); return
     * interval=2.
     */
    @SuppressWarnings("deprecation")
    public static int getDateInterval(Date startDate, Date endDate) {
        DateTime startDateTime = new DateTime(startDate, DateTime.YEAR_TO_DAY);
        DateTime endDateTime = new DateTime(endDate, DateTime.YEAR_TO_DAY);
        long startTime = startDateTime.getTime();
        long endTime = endDateTime.getTime();
        int interval = (int) ((endTime - startTime) / (1000 * 60 * 60 * 24));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, startDate.getHours());
        calendar1.set(Calendar.MINUTE, startDate.getMinutes());
        calendar1.set(Calendar.SECOND, startDate.getSeconds());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, endDate.getHours());
        calendar2.set(Calendar.MINUTE, endDate.getMinutes());
        calendar2.set(Calendar.SECOND, endDate.getSeconds());
        if (calendar2.after(calendar1) == true) {
            interval++;
        }
        return interval;
    }

    /**
     * 得到以baseDate为基准日期的dateInterval天数之后的DateTime日期对象。
     * 如果入参baseDate是DateTime实例，那么返回的对象与它的格式相同；
     * 如果入参是java.util.Date实例，那么返回的对象格式为DateTime.YEAR_TO_SECOND。 见如下举例： <br>
     * (1) baseDate = new DateTime("2004-02-27 00:00:00",
     * DateTime.YEAR_TO_SECOND); return = 2004-03-03 00:00:00 <br>
     * (2) baseDate = new DateTime("2004-02-27", DateTime.YEAR_TO_DAY); return =
     * 2004-03-03 <br>
     * (3) baseDate = new DateTime("2004-02-27 24:00:00",
     * DateTime.YEAR_TO_SECOND); return = 2004-03-04 00:00:00
     */
    public static DateTime getOffsetDate(Date baseDate, int dateInterval) {
        DateTime returnDateTime = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        int baseDateInt = calendar.get(Calendar.DAY_OF_MONTH);
        baseDateInt = baseDateInt + dateInterval;
        calendar.set(Calendar.DAY_OF_MONTH, baseDateInt);

        if (baseDate.getClass() == DateTime.class) {
            DateTime baseDateTime = (DateTime) baseDate;
            returnDateTime = new DateTime(calendar.getTime(), baseDateTime.getType());
        } else {
            returnDateTime = new DateTime(calendar.getTime(), DateTime.YEAR_TO_SECOND);
        }
        return returnDateTime;
    }

    /**
     * 获取上个月第一天
     *
     * @return
     */
    public static String getFirstDayForPreviousMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return format.format(cal.getTime());
    }

    /**
     * 获取上个月最后一天
     *
     * @return
     */
    public static String getLastDayForPreviousMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(cal.getTime());
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public static String getSimpleDateFormat(Date... date) {
        Date currDate = new Date();
        if (date != null && date.length > 0) {
            currDate = date[0];
        }
        String message = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currDate);
        return message;
    }
}
