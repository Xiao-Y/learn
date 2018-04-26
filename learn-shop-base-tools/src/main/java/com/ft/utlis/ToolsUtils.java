package com.ft.utlis;

import com.ft.date.DateTime;
import com.ft.proxy.ProxyInfo;
import com.ft.proxy.ProxyInfoContext;
import org.apache.commons.lang3.StringUtils;

import org.springframework.web.multipart.MultipartFile;

//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 数据操作工具类
 *
 * @author liuyongtao
 * @date 2016年12月2日 下午2:45:41
 */
public class ToolsUtils {

    /**
     * 从一个List中获取指定属性的值
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param list  数据集
     * @param field 指定字段
     * @return 指定字段的结果
     * @date 2016年12月2日 下午2:45:37
     */
    @SuppressWarnings("unchecked")
    public static <T, E> List<T> getListByFieldValue(List<E> list, String field) {
        List<T> fields = new ArrayList<>();
        for (E e : list) {
            Class<? extends Object> clazz = e.getClass();
            try {
                Field f = clazz.getDeclaredField(field);
                // 设置些属性是可以访问的
                f.setAccessible(true);
                T object = (T) f.get(e);
                fields.add(object);
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        return fields;
    }

    /**
     * 去掉右边的空白
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @return
     * @date 2016年11月25日 下午5:35:34
     */
    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != ' ') {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return date.format(today);
    }

    /**
     * 获得当月第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        return date.format(today).substring(0, 8) + "01";
    }

    /**
     * 取得当前日期，格式由参数format决定
     *
     * @return String
     */
    public static String getCurrentDate(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(cal.getTime());
    }

    /**
     * 获得给定年月的最后一天
     *
     * @param startDate
     * @return
     */
    public static String getLastDayOfMonth(String startDate) {
        String[] t = startDate.split("-");
        String year = t[0];
        String month = t[1];
        Calendar cal = Calendar.getInstance();
        // 年
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        // 月，因为Calendar里的月是从0开始，所以要-1
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 日，设为一号
        cal.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        // 下一个月减一为本月最后一天
        cal.add(Calendar.DATE, -1);
        return t[0] + "-" + t[1] + "-" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
    }

    public static String getFirstDayOfNextMonth(String lastDayOfMonth) {
        String[] t = lastDayOfMonth.split("-");
        String year = t[0];
        String month = t[1];
        if (month.equals("12")) {
            month = "01";
            year = String.valueOf(Integer.parseInt(year) + 1);
        } else {
            month = String.valueOf(Integer.parseInt(month) + 1);
            if (month.length() == 1) {
                month = "0" + month;
            }
        }
        return year + "-" + month + "-" + "01";
    }

    /**
     * 在dateTime的基础上加减指定天数
     *
     * @param dateTime
     * @param day
     * @return
     */
    public static DateTime addDay(DateTime dateTime, int day) {
        DateTime dt = new DateTime(dateTime.toString(), dateTime.getType());
        dt.setTime(dateTime.getTime() + day * 86400000L);
        return dt;
    }

//    public static void main(String[] args) {
//
//        int i = 0;
//        while (i < 1000) {
//            System.out.println(getRandomNo());
//            i++;
//        }
//    }

    /**
     * 文件压缩
     *
     * @param dir
     * @param zipfile
     * @param DirectoryName
     * @return
     * @throws IOException
     */
    public static void zipDirectory(String dir, String zipfile) throws IOException {
        // 建立压缩文件输入流
        File dFile = new File(dir);
        if (!dFile.isDirectory()) {
            throw new IllegalArgumentException("Exception: not a directory!");
        }
        String[] entries = dFile.list();
        // 设置缓冲区大小
        byte[] buffer = new byte[4096];
        int bytes_read;
        // 创建一个流来压缩数据并存入zipfile文件中
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));
        // 遍历目录所有项
        int entriesLength = entries.length;
        for (int i = 0; i < entriesLength; i++) {
            File f = new File(dFile, entries[i]);
            if (f.isDirectory()) {
                System.out.println("this file is a directory!");
                continue;
            }
            // 只打包excel文件
            if (f.getName().indexOf(".zip") != -1) {
                continue;
            }
            FileInputStream in = new FileInputStream(f);
            ZipEntry entry = new ZipEntry(entries[i]);
            out.putNextEntry(entry);
            while ((bytes_read = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes_read);
            }
            in.close();
        }
        out.close();
        // 删除生成的excel文件
        File[] child = dFile.listFiles();
        if (child != null && child.length != 0) {
            for (int j = 0; j < child.length; j++) {
                if (child[j].getName().indexOf(".zip") != -1) {
                    continue;
                }
                child[j].delete();
            }
        }
    }

    public static String getSelectTime() {
        StringBuffer selectTime = new StringBuffer();
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        Date year = new Date();
        String thisYear = df.format(year);
        selectTime.append("<select name=year>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) + 1) + ">").append(Integer.parseInt(thisYear) + 1).append("</option>");
        selectTime.append("<option selected value=" + thisYear + ">").append(Integer.parseInt(thisYear)).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 1) + ">").append(Integer.parseInt(thisYear) - 1).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 2) + ">").append(Integer.parseInt(thisYear) - 2).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 3) + ">").append(Integer.parseInt(thisYear) - 3).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 4) + ">").append(Integer.parseInt(thisYear) - 4).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 5) + ">").append(Integer.parseInt(thisYear) - 5).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 6) + ">").append(Integer.parseInt(thisYear) - 6).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 7) + ">").append(Integer.parseInt(thisYear) - 7).append("</option>");
        selectTime.append("<option value=" + (Integer.parseInt(thisYear) - 8) + ">").append(Integer.parseInt(thisYear) - 8).append("</option>");
        selectTime.append("</select>");
        return selectTime.toString();
    }

    /**
     * 下载附件
     *
     * @param filePath
     * @param fileName
     * @param response
     * @throws Exception void Author Skify Jun 13, 2011
     */
//    public static void download(String filePath, String fileName, HttpServletResponse response) throws Exception {
//
//        // 转化为UTF-8避免文件名乱码
//        fileName = changeFileName(fileName);
//
//        response.reset();
//        response.setContentType("application/x-msdownload");
//        response.addHeader("Content-Disposition", "attachment;   filename=\"" + fileName + "\"");
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//            bis = new BufferedInputStream(new FileInputStream(filePath));
//            bos = new BufferedOutputStream(response.getOutputStream());
//            byte[] buff = new byte[20480];
//            while ((bis.read(buff, 0, buff.length)) != -1) {
//                bos.write(buff, 0, buff.length);
//            }
//            bos.flush();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            try {
//                if (bis != null) {
//                    bis.close();
//                }
//                if (bos != null) {
//                    bos.close();
//                }
//            } catch (Exception ne) {
//                throw ne;
//            }
//        }
//    }
//
//    /**
//     * 输出下载文件
//     *
//     * @param file
//     * @param fileName
//     * @param response
//     * @throws Exception void Author: Skify Apr 25, 2012
//     */
//    @SuppressWarnings("resource")
//    public static void download(File file, String fileName, HttpServletResponse response) throws Exception {
//
//        // 转化为UTF-8避免文件名乱码
//        fileName = changeFileName(fileName);
//
//        response.reset();
//        response.setContentType("application/x-msdownload");
//        response.addHeader("Content-Disposition", "attachment;   filename=\"" + fileName + "\"");
//        BufferedInputStream bis = null;
//        BufferedOutputStream bos = null;
//        try {
//            FileInputStream inputStream = new FileInputStream(file);
//            bos = new BufferedOutputStream(response.getOutputStream());
//            byte[] buff = new byte[20480];
//            while ((inputStream.read(buff, 0, buff.length)) != -1) {
//                bos.write(buff, 0, buff.length);
//            }
//            bos.flush();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            try {
//                if (bis != null) {
//                    bis.close();
//                }
//                if (bos != null) {
//                    bos.close();
//                }
//            } catch (Exception ne) {
//                throw ne;
//            }
//        }
//    }

    /**
     * 统一修改文件名
     *
     * @param outputFileName
     * @return String Author Skify Jun 13, 2011
     */
    private static String changeFileName(String outputFileName) {
        int index = outputFileName.lastIndexOf(".");
        String fileType = "";
        if (index != -1) {
            fileType = outputFileName.substring(index);
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        return simpleDateFormat.format(date) + fileType;
    }

    /**
     * 删除文件
     *
     * @param filePath void Author Skify Jun 13, 2011
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() < 1) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(String[] items) {
        if (items == null || items.length < 1) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String[] items) {
        if (items != null && items.length > 0) {
            return true;
        }
        return false;
    }

    /**
     * 取得默认的日期格式
     *
     * @return
     */
    public static String getDefaultDateFormat() {
        // 用方法的形式提供默认的日期格式，便于维护日期格式的取得来源和格式内容。
        return "yyyy-MM-dd";
    }

    /**
     * 本方法用于将yyyy-MM-dd型的字符串转换成日期类型 取得参数日期的偏移日期，参数date格式为yyyy-MM-dd
     * 当输入addDay为正数时，为基准日期的后N天；负数时，为为基准日期的前N天
     * 列1：参数date为2008-01-01，addDay为-1，返回值为2007-12-31
     * 列2：参数date为2008-01-01，addDay为 1，返回值为2008-01-02
     * 列3：参数date为2008-01-01，addDay为 0，返回值为2008-01-01
     *
     * @param strDate
     * @param addDay
     * @return
     * @throws Exception Date Author Skify Jun 21, 2011
     */
    public static Date getDateObjectAfter(String strDate, int addDay) throws Exception {

        if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }

        Date date = null;
        SimpleDateFormat simdf = new SimpleDateFormat(getDefaultDateFormat());
        try {
            date = simdf.parse(strDate);

            Calendar cal = Calendar.getInstance();
            // String[] strArray = strDate.split("-");
            // int year = Integer.parseInt(strArray[0]);
            // int month = Integer.parseInt(strArray[1]);
            // int day = Integer.parseInt(strArray[2]);

            // Calendar cal = Calendar.getInstance();

            // cal.set(year, month - 1, day, 0, 0, 0);
            cal.setTime(date);
            // 计算出相应的日期
            cal.add(Calendar.DATE, addDay);

            return cal.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 转换Date为指定格式的字符串
     *
     * @return String
     */
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 把file转换成byte
     *
     * @param file
     * @return
     * @throws IOException byte[] Author: Skify Feb 16, 2012
     */
    @SuppressWarnings("resource")
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }
        is.close();
        return bytes;
    }

    /**
     * 本方法用于将yyyy-MM-dd型的字符串转换成日期类型，如果strDate为Null或者为空字符串,则返回null
     *
     * @param strDate
     * @return
     * @throws Exception Date Author Skify Jul 9, 2011
     */
    public static Date getDateObject(String strDate) throws Exception {
        Date dt_Date1 = null;
        if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat simdf = new SimpleDateFormat(getDefaultDateFormat());
        try {
            dt_Date1 = simdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dt_Date1;
    }

    /**
     * 转换字符串成按指定格式日期
     *
     * @param strDate
     * @param format
     * @return
     * @throws Exception Date Author: xinyu.li 2012-11-6
     */
    public static Date getDateObjectForMin(String strDate, String format) throws Exception {
        Date dt_Date1 = null;
        if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
        SimpleDateFormat simdf = new SimpleDateFormat(format);
        try {
            dt_Date1 = simdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dt_Date1;
    }

    /**
     * 本方法用于将指定格式型的字符串转换成日期类型，如果strDate为Null或者为空字符串,则返回null
     *
     * @param strDate
     * @return
     * @throws Exception Date Author Skify Jul 9, 2011
     */
    public static Date getDateFromStr(String strDate, String format) throws Exception {
        Date dt_Date1 = null;
        if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
        if (isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simdf = new SimpleDateFormat(format);
        try {
            dt_Date1 = simdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return dt_Date1;
    }

    /**
     * 获取系统时间，可传入DateTime.YEAR_TO_SECOND值定时间段，默认为DateTime.YEAR_TO_SECOND
     *
     * @param timeSpan
     * @return DateTime Author: Skify Apr 13, 2012
     */
    public static DateTime getDateTimeNow(Integer timeSpan) {
        if (timeSpan == null) {
            timeSpan = DateTime.YEAR_TO_SECOND;
        }
        DateTime dateTime = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
        try {
            dateTime = new DateTime(new Date(), timeSpan);
        } catch (Exception e) {
            dateTime = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
        }
        return dateTime;
    }

    /**
     * 获取系统时间，可传入DateTime.YEAR_TO_SECOND值定时间段，默认为DateTime.YEAR_TO_SECOND
     *
     * @param timeSpan
     * @return DateTime Author: Skify Apr 13, 2012
     */
    public static DateTime getDateTimeNow() {
        return new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
    }

    /**
     * 检测是否数字
     *
     * @param string
     * @return boolean Author Skify Aug 21, 2011
     */
    public static boolean checkIsInteger(String string) {
        if (isEmpty(string)) {
            return false;
        }
        char[] c = string.toCharArray(); // 把输入的字符串转成字符数组
        for (int i = 0; i < c.length; i++) {
            if (!Character.isDigit(c[i])) { // 判断是否为数字
                return false;
            }
        }
        return true;
    }

    /**
     * 获取时间年月日时分秒毫秒14位+4位随机数
     *
     * @param number
     * @return String Author Skify Aug 31, 2011
     */
    public static String getRandomNo() {
        Random random = new Random();
        String numbers = getCurrentDate("yyyyMMddHHmmss");
        int ranInt = 0;
        while (true) {
            ranInt = random.nextInt(9999);
            if (ranInt > 999) {
                return numbers + String.valueOf(ranInt);
            }
        }
    }

    /**
     * 把#,##.00格式的数字转回00.00格式
     *
     * @param doubleNum
     * @return Double Author: Skify Mar 24, 2012
     */
    public static Double returnDoubleNum(String doubleNum) {
        Double returnValue = 0.0;
        String valueStr = "";
        if (isNotEmpty(doubleNum)) {
            String[] items = doubleNum.split(",");
            for (String item : items) {
                valueStr = valueStr + item;
            }
        }
        if (isNotEmpty(valueStr)) {
            returnValue = Double.valueOf(valueStr);
        }
        return returnValue;
    }

    /**
     * 通过出生日期计算年龄
     *
     * @param birthDateArg
     * @return
     * @throws Exception author cong. 2012-8-27上午11:41:02
     */
    public static Integer getAge(Date birthDateArg) {
        Integer age = 0;
        DateTime birthDate = new DateTime(birthDateArg, DateTime.YEAR_TO_DAY);
        DateTime today = new DateTime(new Date(), DateTime.YEAR_TO_DAY);
        int yearDiff = today.getYear() - birthDate.getYear();
        if ((birthDate.toString()).length() > 0) {
            if (today.getMonth() > birthDate.getMonth()) {
            } else if (today.getMonth() < birthDate.getMonth()) {
                yearDiff--;
            } else {
                if (today.getDay() > birthDate.getDay()) {
                } else if (today.getDay() < birthDate.getDay()) {
                    yearDiff--;
                }
            }
            age = yearDiff;
        }
        return age;
    }

    /**
     * 检测是否浮点数字
     *
     * @param string
     * @return boolean Author Skify Aug 21, 2011
     */
    public static boolean checkIsFloat(String string) {
        if (isEmpty(string)) {
            return false;
        }
        if ("-".equals(string.substring(0, 1))) {
            string = string.substring(1, string.length());
        }
        char[] c = string.toCharArray(); // 把输入的字符串转成字符数组
        if (string.indexOf(".") != -1) {
            String[] items = string.split(".");
            if (items.length != 2) {
                return false;
            } else {
                char[] cc = (items[0] + items[1]).toCharArray();
                for (int i = 0; i < cc.length; i++) {
                    if (!Character.isDigit(cc[i])) { // 判断是否为数字
                        return false;
                    }
                }
            }
        } else {
            for (int i = 0; i < c.length; i++) {
                if (!Character.isDigit(c[i])) { // 判断是否为数字
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检测是否yyyy-MM-dd日期类型
     *
     * @param string
     * @return boolean Author Skify Aug 21, 2011
     */
    public static boolean checkIsDate(String string) {
        if (isEmpty(string)) {
            return false;
        }
        string = string.trim();
        if (string.length() != 10) {
            return false;
        }
        // 年
        String year = string.substring(0, 4);
        char[] years = string.substring(0, 4).toCharArray();
        for (int i = 0; i < years.length; i++) {
            if (!Character.isDigit(years[i])) { // 判断是否为数字
                return false;
            }
        }
        Integer yearIn = Integer.valueOf(year);
        // 符号
        if (!"-".equals(string.substring(4, 5)) || !"-".equals(string.substring(7, 8))) {
            return false;
        }
        // 月
        String month = string.substring(5, 7);
        char[] months = month.toCharArray();
        for (int i = 0; i < months.length; i++) {
            if (!Character.isDigit(months[i])) { // 判断是否为数字
                return false;
            }
        }
        Integer monthIn = Integer.valueOf(month);
        if (monthIn > 12) {
            return false;
        }
        // 日
        String day = string.substring(8, 10);
        char[] days = day.toCharArray();
        for (int i = 0; i < days.length; i++) {
            if (!Character.isDigit(days[i])) { // 判断是否为数字
                return false;
            }
        }
        Integer dayIn = Integer.valueOf(day);
        if (dayIn > 31) {
            return false;
        }
        // 月日
        if ("02".equals(month)) {
            if ("00".equals(year.substring(2, 4))) {
                if (yearIn % 400 == 0) {
                    if (dayIn > 29) {
                        return false;
                    }
                } else {
                    if (dayIn > 28) {
                        return false;
                    }
                }
            } else {
                if (yearIn % 4 == 0) {
                    if (dayIn > 29) {
                        return false;
                    }
                } else {
                    if (dayIn > 28) {
                        return false;
                    }
                }
            }
        } else if ("01".equals(month) || "03".equals(month) || "05".equals(month) || "07".equals(month) || "08".equals(month) || "10".equals(month)
                || "12".equals(month)) {
            if (dayIn > 31) {
                return false;
            }
        } else {
            if (dayIn > 30) {
                return false;
            }
        }
        return true;
    }

    /**
     * return Integer 获取两个日期间的天数
     *
     * @param dateBegin
     * @param dateEnd
     * @return Integer Author: Skify Aug 16, 2012
     */
    public static Integer getDaysOfTwoDateTime(Date dateBegin, Date dateEnd) {
        Long quot = dateEnd.getTime() - dateBegin.getTime();
        quot = quot / 1000 / 60 / 60 / 24;
        return Integer.valueOf(String.valueOf(quot));
    }

    public static Long getAge(Date startDate, Date birthDateArg) {
        Long age = null;
        try {
            DateTime birthDate = new DateTime(birthDateArg, DateTime.YEAR_TO_DAY);
            DateTime startDay = new DateTime(startDate, DateTime.YEAR_TO_DAY);
            int yearDiff = startDay.getYear() - birthDate.getYear();
            if ((birthDate.toString()).length() > 0) {
                if (startDay.getMonth() > birthDate.getMonth()) {

                } else if (startDay.getMonth() < birthDate.getMonth()) {
                    yearDiff--;
                } else {
                    if (startDay.getDay() > birthDate.getDay()) {

                    } else if (startDay.getDay() < birthDate.getDay()) {
                        yearDiff--;
                    }
                }
                age = new Long(yearDiff);
            }
        } catch (Exception e) {
            e.printStackTrace();
            age = new Long(0);
        }
        return age;
    }

    public static String upperCaseFirstChar(String propertyName) {
        if (propertyName == null || "".equals(propertyName)) {
            return "";
        } else {
            return new StringBuilder(propertyName.length()).append(propertyName.substring(0, 1).toUpperCase()).append(propertyName.substring(1))
                    .toString();
        }
    }

    public static String parserDate(Date date, String fmt) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            String strDate = sdf.format(date);
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            // throw new Exception("parserDate ERROR" + e);

        }
    }

    /**
     * 判断某元素是否存在数组中
     *
     * @param arrayName
     * @param str
     * @return
     */
    public static boolean elemIsExistsArray(String[] arrayName, String str) {
        boolean flag = false;
        Arrays.sort(arrayName);
        int index = Arrays.binarySearch(arrayName, str);
        if (index >= 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 字符串null 转换""
     *
     * @param str
     */
    public static String emptyToString(String str) {
        if (isEmpty(str)) {
            return "";
        } else
            return str;
    }

    @SuppressWarnings("static-access")
    public static String subStrByLen(String str, int len) {
        String s = "";
        if (isEmpty(str))
            return s;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        // Matcher m = p.matcher(str);
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (p.matches(regEx, str.charAt(i) + "")) {
                count = count + 3;
            } else
                count++;
            if (count > len) {
                break;
            } else {
                s = s + str.charAt(i);
            }
        }
        return s;
    }

    public static String convertToNotNullValue(String value) {
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }
    }

    public static int getDateRangeDay(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / 86400000L);
    }

    public static int getDateRangeMonth(Date date1, Date date2) {
        return getDateRangeDay(date1, date2) / 30;
    }

    public static int getDateRangeYear(Date date1, Date date2) {
        int range = 0;
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);

        int year1 = calendar1.get(1);
        int year2 = calendar2.get(1);

        if (year2 > year1) {
            range = year2 - year1;

            if (calendar2.get(2) < calendar1.get(2)) {
                range--;
            }

            if ((calendar2.get(2) == calendar1.get(2)) && (calendar2.get(5) < calendar1.get(5))) {
                range--;
            }

        } else {
            range = year1 - year2;

            if (calendar1.get(2) < calendar2.get(2)) {
                range--;
            }

            if ((calendar1.get(2) == calendar2.get(2)) && (calendar1.get(5) < calendar2.get(5))) {
                range--;
            }

        }

        return range;
    }

    public static Date getAfterMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = (cal.get(2) + month) / 12;
        int month2 = (cal.get(2) + 1 + month) % 12;
        int newYear = cal.get(1) + year;
        int newMonth = 0;
        if ((year == 0) && (month2 != 0))
            newMonth = month2;
        else if ((year == 0) && (month2 == 0))
            newMonth = 0;
        else if ((year != 0) && (month2 != 0))
            newMonth = month2;
        else if ((year != 0) && (month2 == 0))
            newMonth = 0;
        cal.set(newYear, newMonth, cal.get(5), cal.get(11), cal.get(12), cal.get(13));

        return cal.getTime();
    }

    /**
     * 判断所有的参数是否为空
     *
     * @param objects
     * @return
     */
    public static boolean isNotEmpty(Object... objects) {
        if (objects != null) {
            for (Object o : objects) {
                if (o == null || "".equals(o)) {
                    return false;
                }
            }
        }
        return true;
    }

//    /**
//     * 保存上传的文件，返回文件名的字符串以“*”分隔
//     *
//     * @param imgUrls
//     * @param request
//     * @param realPath
//     * @return
//     */
//    public static String uploadFile(MultipartFile[] imgUrls, HttpServletRequest request, String realPath) {
//        String path = request.getSession().getServletContext().getRealPath(realPath);
//        // 获取图片的分割符
//        String imageSplit = ToolsUtils.getSystemConfigString(request, "imageUploadSplit");
//        StringBuffer buffer = new StringBuffer("");
//        for (MultipartFile m : imgUrls) {
//            // 获取文件的名称
//            String fileName = m.getOriginalFilename();
//            if (!StringUtils.isEmpty(fileName)) {
//                fileName = ToolsUtils.generateFileName(fileName);
//                File targetFile = new File(path, fileName);
//                // 文件路径不存在
//                if (!targetFile.exists()) {
//                    // 新建文件
//                    targetFile.mkdirs();
//                }
//                // 保存
//                try {
//                    m.transferTo(targetFile);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                // 拼接图片的名字，用于保存
//                buffer.append(fileName);
//                buffer.append(imageSplit);
//            }
//        }
//        String imgUrl = buffer.toString();
//        if (!StringUtils.isEmpty(imgUrl)) {
//            int index = imgUrl.lastIndexOf(imageSplit);
//            imgUrl = imgUrl.substring(0, index);
//        }
//        return imgUrl;
//    }
//
//    /**
//     * 删除图片
//     *
//     * @param realPath 图片路径
//     * @param imgUrls  图片名称字符串，可以为单个
//     * @date 2015年8月25日下午12:02:11
//     */
//    public static void deleteFile(HttpServletRequest request, String imgUrls, String realPath) {
//        // 获取图片的分割符
//        String imageSplit = ToolsUtils.getSystemConfigString(request, "imageSplit");
//        String path = request.getSession().getServletContext().getRealPath(realPath);
//        if (!StringUtils.isEmpty(imgUrls)) {
//            String[] imageNames = imgUrls.split(imageSplit);
//            for (String imageName : imageNames) {
//                File file = new File(path, imageName);
//                if (file.exists()) {
//                    file.delete();
//                }
//            }
//        }
//    }
//
//    /**
//     * 返回用户的IP地址
//     *
//     * @param request
//     * @return
//     */
//    public static String toIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("X-Forwarded-For");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }

    /**
     * 穿透代理获取服务器真实ip
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @date 2017年8月17日 上午9:26:34
     */
    public static String getServiceIpAddr() {
        String ip = "";
        ProxyInfo proxyInfo = ProxyInfoContext.getProxyInfo();
        if (proxyInfo != null) {
            String proxyAddr = proxyInfo.getProxyAddr();
            if ("localhost".equals(proxyAddr)) {
                ip = "127.0.0.1";
            } else {
                ip = proxyInfo.getLocalAddr();
            }
            ip += ":" + proxyInfo.getPort();
        }
        return ip;
    }

    /**
     * 传入原图名称，获得一个以时间格式的新名称
     *
     * @param fileName 　原图名称
     * @return
     */
    public static String generateFileName(String fileName) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatDate = format.format(new Date());
        int random = new Random().nextInt(10000);
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        return formatDate + random + extension;
    }

    /**
     * 加载资源文件
     *
     * @param filename
     * @date 2015年8月25日上午9:23:04
     */
    public static Properties readPropertiesFile(String filename) {
        Properties properties = new Properties();
        try {
            InputStream in = ToolsUtils.class.getClassLoader().getResourceAsStream(filename);
            properties.load(in);
            in.close(); // 关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

//    /**
//     * 从内存中，通过key获取系统属性的value
//     *
//     * @param request
//     * @param key     关键字
//     * @return value
//     */
//    public static String getSystemConfigString(HttpServletRequest request, String key) {
//        ServletContext sct = request.getServletContext();
//        Properties properties = (Properties) sct.getAttribute("SYSTEM_CONFIG");
//        String value = properties.getProperty(key);
//        return value;
//    }

    /**
     * 主要用于配置文件中数据的计算<br/>
     * 如：1000*60*24
     *
     * @param text
     * @return
     */
    public static long splitTextData(String text) {
        long time = 0L;
        if (ToolsUtils.isNotEmpty(text)) {
            String[] autoTime = text.split("\\*");
            if (ToolsUtils.isNotEmpty(autoTime)) {
                time = 1L;
                if (autoTime.length > 1) {
                    for (String str : autoTime) {
                        time = time * new Long(StringUtils.trim(str));
                    }
                } else {
                    time = new Long(autoTime[0]);
                }
            }
        }
        return time;
    }
}
