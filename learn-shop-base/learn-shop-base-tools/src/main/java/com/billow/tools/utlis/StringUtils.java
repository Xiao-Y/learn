package com.billow.tools.utlis;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StringUtils extends org.apache.commons.lang3.StringUtils {

    private String defaultCharset = "UTF-8";

    /**
     * 获取默认编码，UTF-8
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @date 2017年6月23日 上午9:06:30
     */
    public String getDefaultCharset() {
        return defaultCharset;
    }

    /**
     * 复制字符串
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str       源字符串
     * @param copyTimes 复制的次数
     * @return
     * @date 2017年6月23日 上午9:08:27
     */
    public static String copyString(String str, int copyTimes) {
        if (str == null)
            return null;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < copyTimes; i++)
            buffer.append(str);

        return buffer.toString();
    }

    /**
     * 获取字节长度
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @return
     * @date 2017年6月23日 上午9:09:08
     */
    public static int getBytesLength(String str) {
        if (str == null)
            return -1;
        else
            return str.getBytes().length;
    }

    /**
     * 从指定位置搜索字符串出现的位置
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str             源
     * @param subStr          目标
     * @param startIndex      开始搜索的位置
     * @param occurrenceTimes 第几次出现
     * @return
     * @date 2017年6月23日 上午9:21:27
     */
    public static int indexOf(String str, String subStr, int startIndex, int occurrenceTimes) {
        int foundCount = 0;
        int index = startIndex;
        if (occurrenceTimes <= 0)
            return -1;
        if (str.length() - 1 < startIndex)
            return -1;
        if ("".equals(subStr))
            return 0;
        int substrLength = subStr.length();
        while (foundCount < occurrenceTimes) {
            index = str.indexOf(subStr, index);
            if (index == -1)
                return -1;
            foundCount++;
            index += substrLength;
        }
        return index - substrLength;
    }

    /**
     * 从头开始搜索字符串出现的位置
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str             源
     * @param subStr          目标
     * @param occurrenceTimes 第几次出现
     * @return
     * @date 2017年6月23日 上午9:21:27
     */
    public static int indexOf(String str, String subStr, int occurrenceTimes) {
        return indexOf(str, subStr, 0, occurrenceTimes);
    }

    /**
     * <br>
     * added by liuyongtao<br>
     *
     * @param str           源
     * @param subStr        目标
     * @param fromIndex     开始搜索的位置
     * @param caseSensitive 是否忽略大小写
     * @return
     * @date 2017年6月23日 上午9:28:27
     */
    public static int indexOf(String str, String subStr, int fromIndex, boolean caseSensitive) {
        if (!caseSensitive)
            return str.toLowerCase().indexOf(subStr.toLowerCase(), fromIndex);
        else
            return str.indexOf(subStr, fromIndex);
    }

    /**
     * 将字符串替换成指定的字符串
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str           源
     * @param searchStr     目标
     * @param replaceStr    指定的字符串
     * @param caseSensitive 是否忽略大小写
     * @return
     * @date 2017年6月23日 上午9:30:27
     */
    public static String replace(String str, String searchStr, String replaceStr, boolean caseSensitive) {
        int i = 0;
        int j = 0;
        if (str == null)
            return null;
        if ("".equals(str))
            return "";
        if (searchStr == null || searchStr.equals(""))
            return str;
        String newReplaceStr = replaceStr;
        if (replaceStr == null)
            newReplaceStr = "";
        StringBuilder buffer = new StringBuilder();
        while ((j = indexOf(str, searchStr, i, caseSensitive)) > -1) {
            buffer.append(str.substring(i, j));
            buffer.append(newReplaceStr);
            i = j + searchStr.length();
        }
        buffer.append(str.substring(i, str.length()));
        return buffer.toString();
    }

    /**
     * 将字符串替换成指定的字符串（忽略大小写）
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str        源
     * @param searchStr  目标
     * @param replaceStr 指定的字符串
     * @return
     * @date 2017年6月23日 上午9:30:27
     */
    public static String replace(String str, String searchStr, String replaceStr) {
        return replace(str, searchStr, replaceStr, true);
    }

    /**
     * 将字符串替换成指定的字符串（忽略大小写）
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str        源
     * @param searchChar 目标
     * @param replaceStr 指定的字符串
     * @return
     * @date 2017年6月23日 上午9:30:27
     */
    public static String replace(String str, char searchChar, String replaceStr) {
        return replace(str, String.valueOf(searchChar), replaceStr, true);
    }

    /**
     * 从指定位置开始替换字符串
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str        源
     * @param beginIndex 指定位置
     * @param replaceStr 指定的字符串
     * @return
     * @date 2017年6月23日 上午9:35:33
     */
    public static String replace(String str, int beginIndex, String replaceStr) {
        if (str == null)
            return null;
        String newReplaceStr = replaceStr;
        if (replaceStr == null)
            newReplaceStr = "";
        StringBuilder buffer = new StringBuilder(str.substring(0, beginIndex));
        buffer.append(newReplaceStr);
        buffer.append(str.substring(beginIndex + newReplaceStr.length()));
        return buffer.toString();
    }

    public static String[] split(String originalString, int splitByteLength, String charsetName) {
        if (originalString == null)
            return new String[0];
        if ("".equals(originalString))
            return new String[0];
        if (originalString.trim().equals(""))
            return (new String[]{""});
        if (splitByteLength <= 1)
            return (new String[]{originalString});
        int size = originalString.length();
        List<String> strList = new ArrayList<>();
        try {
            int count = 0;
            int start = 0;
            int len = 0;
            for (int i = 0; i < size; i++) {
                len = String.valueOf(originalString.charAt(i)).getBytes(charsetName).length;
                count += len;
                if (count == splitByteLength) {
                    strList.add(originalString.substring(start, i + 1));
                    count = 0;
                    start = i + 1;
                    continue;
                }
                if (count > splitByteLength) {
                    strList.add(originalString.substring(start, i));
                    count = len;
                    start = i;
                }
            }

            if (start < size)
                strList.add(originalString.substring(start));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        String arrReturn[] = new String[strList.size()];
        strList.toArray(arrReturn);
        return arrReturn;
    }

    public static String[] split(String originalString, String delimiterString) {
        int index = 0;
        String returnArray[] = null;
        int length = 0;
        if (originalString == null || delimiterString == null || "".equals(originalString))
            return new String[0];
        if ("".equals(originalString) || "".equals(delimiterString) || originalString.length() < delimiterString.length())
            return (new String[]{originalString});
        String strTemp = originalString;
        do {
            if (strTemp == null || strTemp.equals(""))
                break;
            index = strTemp.indexOf(delimiterString);
            if (index == -1)
                break;
            length++;
            strTemp = strTemp.substring(index + delimiterString.length());
        } while (true);
        returnArray = new String[++length];
        strTemp = originalString;
        for (int i = 0; i < length - 1; i++) {
            index = strTemp.indexOf(delimiterString);
            returnArray[i] = strTemp.substring(0, index);
            strTemp = strTemp.substring(index + delimiterString.length());
        }

        returnArray[length - 1] = strTemp;
        return returnArray;
    }

    /**
     * 去掉右边的空白
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @return
     * @date 2017年6月23日 上午9:12:29
     */
    public static String rightTrim(String str) {
        if (str == null)
            return "";
        int length = str.length();
        for (int i = length - 1; i >= 0 && str.charAt(i) == ' '; i--)
            length--;

        return str.substring(0, length);
    }

    /**
     * 去掉左边的空白
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @return
     * @date 2017年6月23日 上午9:12:51
     */
    public static String leftTrim(String str) {
        if (str == null)
            return "";
        int start = 0;
        int i = 0;
        for (int n = str.length(); i < n && str.charAt(i) == ' '; i++)
            start++;

        return str.substring(start);
    }

    /**
     * 去掉所有空白
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @return
     * @date 2017年6月23日 上午9:13:54
     */
    public static String absoluteTrim(String str) {
        return replace(str, " ", "");
    }

    /**
     * 全部转小写
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     * @date 2017年6月23日 上午9:05:47
     */
    public static String lowerCase(String str, int beginIndex, int endIndex) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(str.substring(0, beginIndex));
        buffer.append(str.substring(beginIndex, endIndex).toLowerCase());
        buffer.append(str.substring(endIndex));
        return buffer.toString();
    }

    /**
     * 全部转大写
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @param beginIndex
     * @param endIndex
     * @return
     * @date 2017年6月23日 上午9:05:33
     */
    public static String upperCase(String str, int beginIndex, int endIndex) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(str.substring(0, beginIndex));
        buffer.append(str.substring(beginIndex, endIndex).toUpperCase());
        buffer.append(str.substring(endIndex));
        return buffer.toString();
    }

    /**
     * 首字母转小写
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param iString
     * @return
     * @date 2017年6月23日 上午9:05:12
     */
    public static String lowerCaseFirstChar(String iString) {
        if (iString == null || iString.length() < 1)
            throw new IllegalArgumentException("String must have at least one character.");
        else
            return (new StringBuilder(iString.length())).append(iString.substring(0, 1).toLowerCase()).append(iString.substring(1)).toString();
    }

    /**
     * 首字母转大写
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param iString
     * @return
     * @date 2017年6月23日 上午9:04:25
     */
    public static String upperCaseFirstChar(String iString) {
        if (iString == null || iString.length() < 1)
            throw new IllegalArgumentException("String must have at least one character.");
        else
            return (new StringBuilder(iString.length())).append(iString.substring(0, 1).toUpperCase()).append(iString.substring(1)).toString();
    }

    public static int timesOf(String str, String subStr) {
        int foundCount = 0;
        if ("".equals(subStr))
            return 0;
        for (int fromIndex = str.indexOf(subStr); fromIndex != -1; fromIndex = str.indexOf(subStr, fromIndex + subStr.length()))
            foundCount++;

        return foundCount;
    }

    public static int timesOf(String str, char ch) {
        int foundCount = 0;
        for (int fromIndex = str.indexOf(ch); fromIndex != -1; fromIndex = str.indexOf(ch, fromIndex + 1))
            foundCount++;

        return foundCount;
    }

    /**
     * 字符串转Map<br/>
     * 形式：a=1,b=2,c=3或者1,2,3<br/>
     * 转换：map.put("a",1)或者map.(1,1)
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @param splitString
     * @return
     * @date 2017年6月23日 上午9:00:02
     */
    public static Map<String, String> toMap(String str, String splitString) {
        Map<String, String> map = new HashMap<>();
        String values[] = split(str, splitString);
        String key = "";
        String value = "";
        for (int i = 0; i < values.length; i++) {
            String tempValue = values[i];
            int pos = tempValue.indexOf('=');
            key = "";
            value = "";
            if (pos > -1) {
                key = tempValue.substring(0, pos);
                value = tempValue.substring(pos + splitString.length());
            } else {
                key = tempValue;
            }
            map.put(key, value);
        }

        return map;
    }

    /**
     * 字符串转ascii
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param str
     * @return
     * @date 2017年6月23日 上午9:03:21
     */
    public static String native2ascii(String str) {
        char ca[] = str.toCharArray();
        StringBuilder buffer = new StringBuilder(ca.length * 6);
        for (int x = 0; x < ca.length; x++) {
            char a = ca[x];
            if (a > '\377')
                buffer.append("\\u").append(Integer.toHexString(a));
            else
                buffer.append(a);
        }

        return buffer.toString();
    }

    /**
     * 数组转字符串
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param sources
     * @return
     * @date 2017年6月23日 上午9:02:39
     */
    public static String concat(Object sources[]) {
        if (sources == null)
            return "";
        if (sources.length == 1)
            return String.valueOf(sources[0]);
        StringBuilder sb = new StringBuilder();
        Object arr$[] = sources;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++) {
            Object o = arr$[i$];
            if (o != null)
                sb.append(o);
        }

        return sb.toString();
    }
}
