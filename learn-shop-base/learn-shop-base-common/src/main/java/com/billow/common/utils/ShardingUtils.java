package com.billow.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.billow.tools.utlis.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 分片工具类
 * 基于 Hutool 提供统一的时间转换、数字处理和分片计算方法
 *
 * @author billow
 */
@Slf4j
public class ShardingUtils {

    // ========================== 常量定义 ==========================
    
    /**
     * 年月格式化器 (YYYYMM)
     */
    public static final DateTimeFormatter YEAR_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");

    /**
     * 月份格式化器 (MM)
     */
    public static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MM");

    /**
     * 日期格式化器 (YYYY-MM-DD)
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /**
     * 订单号日期格式 (YYYYMMDD)
     */
    public static final DateTimeFormatter ORDER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    /**
     * 订单号最小长度
     */
    public static final int ORDER_SN_MIN_LENGTH = 8;
    
    /**
     * 支付时间临界值天数（月初前N天需要查询上个月的表）
     */
    public static final int PAY_TIME_CRITICAL_DAYS = 3;
    
    /**
     * 数字提取最后位数
     */
    public static final int EXTRACT_LAST_DIGITS = 2;
    
    /**
     * 字符串补零长度
     */
    public static final int PAD_LENGTH = 2;
    
    /**
     * 日志格式前缀模板
     */
    public static final String LOG_PREFIX_TEMPLATE = "逻辑表{}，{}";

    // ========================== 数字处理方法 ==========================

    /**
     * 提取分片字段的最后两位数字
     * 支持类型：String/Integer/Long/Short/Byte/Character等
     * 使用 Hutool 的字符串工具进行处理
     *
     * @param value 分片键值
     * @return 最后两位数字（0-99）
     * @author billow
     */
    public static int extractLastTwoDigits(Comparable<?> value) {
        String valueStr;
        try {
            // 1. 数字类型（Integer/Long/Short/Byte等）转字符串
            if (value instanceof Number) {
                valueStr = String.valueOf(((Number) value).longValue());
            }
            // 2. 字符/字符串类型
            else if (value instanceof CharSequence) {
                valueStr = value.toString().trim();
            }
            // 3. 字符类型（单个字符）
            else if (value instanceof Character) {
                valueStr = String.valueOf(value);
            }
            // 4. 不支持的类型
            else {
                throw new IllegalArgumentException(String.format("不支持的分片键类型[%s]，仅支持数字/字符串/字符类型",
                        value.getClass().getName()));
            }

            // 5. 处理空字符串/长度不足两位的情况（使用 Hutool）
            if (StrUtil.isBlank(valueStr)) {
                throw new IllegalArgumentException("分片键值为空字符串，无法提取最后两位数字");
            }
            
            // 长度不足两位时，补前导0（如"5"→"05"，取5；"a9"→"9"→"09"，取9）
            String lastTwo = valueStr.length() >= EXTRACT_LAST_DIGITS
                    ? StrUtil.sub(valueStr, -EXTRACT_LAST_DIGITS, valueStr.length())  // 使用 Hutool 的 sub 方法
                    : StrUtil.padPre(valueStr, PAD_LENGTH, '0');             // 使用 Hutool 的 padPre 方法

            // 6. 提取纯数字（过滤非数字字符，仅保留最后两位数字）
            String digitsOnly = StrUtil.removeAll(lastTwo, "[^0-9]");  // 使用 Hutool 的 removeAll 方法
            
            // 再次补0（如最后两位是"a8"→"8"→"08"；是"xx"→""→"00"）
            digitsOnly = digitsOnly.length() >= EXTRACT_LAST_DIGITS
                    ? StrUtil.sub(digitsOnly, -EXTRACT_LAST_DIGITS, digitsOnly.length())
                    : StrUtil.padPre(digitsOnly, PAD_LENGTH, '0');

            return Integer.parseInt(digitsOnly);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("分片键值[%s]无法提取有效数字，错误：%s",
                    value, e.getMessage()), e);
        }
    }

    /**
     * 从物理表名提取分片索引（如 t_order_5 → 5）
     * 使用 Hutool 的字符串工具进行处理
     *
     * @param tableName 物理表名
     * @return 分片索引
     * @author billow
     */
    public static int extractShardIndexFromTableName(String tableName) {
        // 查找最后一个 "_" 后缀（避免表名中包含_的其他情况）
        int lastUnderscoreIndex = StrUtil.lastIndexOfIgnoreCase(tableName, "_");
        if (lastUnderscoreIndex == -1 || lastUnderscoreIndex == tableName.length() - 1) {
            throw new IllegalArgumentException(String.format("表名[%s]不符合分片规则（需为「逻辑表名_分片索引」格式，如t_order_0）",
                    tableName));
        }
        try {
            // 截取最后一个_后的数字部分（使用 Hutool）
            String indexStr = StrUtil.sub(tableName, lastUnderscoreIndex + 1, tableName.length());
            return Integer.parseInt(indexStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("表名[%s]的分片索引后缀格式错误（需为数字）",
                    tableName), e);
        }
    }

    // ========================== ID 处理方法 ==========================

    /**
     * 从雪花ID中提取日期时间
     * 调用 SnowFlakeUtil 工具类的方法
     *
     * @param snowFlakeId 雪花ID
     * @return LocalDateTime
     * @author billow
     */
    public static LocalDateTime extractDateFromId(long snowFlakeId) {
        try {
            return SnowFlakeUtil.extractDateFromId(snowFlakeId);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("从雪花ID[%d]提取时间失败，错误信息：%s", snowFlakeId, e.getMessage()), e);
        }
    }

    /**
     * 从雪花ID中提取日期时间（支持字符串类型）
     *
     * @param snowFlakeIdStr 雪花ID字符串
     * @return LocalDateTime
     * @author billow
     */
    public static LocalDateTime extractDateFromId(String snowFlakeIdStr) {
        if (StrUtil.isBlank(snowFlakeIdStr)) {
            throw new IllegalArgumentException("雪花ID字符串不能为空");
        }
        try {
            long snowFlakeId = Long.parseLong(snowFlakeIdStr);
            return extractDateFromId(snowFlakeId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("雪花ID字符串[%s]格式错误，无法转换为数字", snowFlakeIdStr), e);
        }
    }

    // ========================== 时间转换方法 ==========================

    /**
     * 转换任意 Comparable 类型为 LocalDateTime
     * 使用 Hutool 自动识别时间格式，支持 String/Date/LocalDateTime
     *
     * @param value 分片键值
     * @return LocalDateTime 或 null
     * @author billow
     */
    public static LocalDateTime convertToLocalDateTime(Comparable<?> value) {
        if (value == null) {
            return null;
        }

        try {
            // 1. 直接是 LocalDateTime 类型
            if (value instanceof LocalDateTime) {
                return (LocalDateTime) value;
            }

            // 2. Date 类型（Hutool 一键转 LocalDateTime）
            if (value instanceof Date) {
                return LocalDateTimeUtil.of((Date) value);
            }

            // 3. String 类型（Hutool 自动识别所有常见时间格式）
            if (value instanceof String) {
                String dateStr = ((String) value).replace("T", " ");
                return LocalDateTimeUtil.of(DateUtil.parse(dateStr));
            }

            // 4. 其他不支持的类型
            throw new IllegalArgumentException(String.format("不支持的分片键类型[%s]，仅支持 String/Date/LocalDateTime",
                    value.getClass().getName()));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("时间转换失败，分片键值：%s，错误信息：%s", value, e.getMessage()), e);
        }
    }

    /**
     * 转换任意 Comparable 类型为 LocalDate
     *
     * @param value 分片键值
     * @return LocalDate 或 null
     * @author billow
     */
    public static LocalDate convertToLocalDate(Comparable<?> value) {
        LocalDateTime dateTime = convertToLocalDateTime(value);
        return dateTime != null ? dateTime.toLocalDate() : null;
    }

    // ========================== 分片计算方法 ==========================

    /**
     * 计算年月后缀（格式：YYYYMM）
     *
     * @param dateTime 时间
     * @return 年月字符串，如 "202501"
     * @author billow
     */
    public static String calculateYearMonthSuffix(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("时间不能为空");
        }
        return dateTime.format(YEAR_MONTH_FORMAT);
    }

    /**
     * 计算月份后缀（格式：MM）
     *
     * @param dateTime 时间
     * @return 月份字符串，如 "01"
     * @author billow
     */
    public static String calculateMonthSuffix(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("时间不能为空");
        }
        return dateTime.format(MONTH_FORMAT);
    }

    /**
     * 获取目标表名（年月分片）
     *
     * @param dateTime        时间
     * @param logicTableName  逻辑表名
     * @return 目标表名
     * @author billow
     */
    public static String getTargetTableByYearMonth(LocalDateTime dateTime, String logicTableName) {
        String yearMonth = calculateYearMonthSuffix(dateTime);
        return logicTableName + "_" + yearMonth;
    }

    /**
     * 获取目标表名（月份分片）
     *
     * @param dateTime        时间
     * @param logicTableName  逻辑表名
     * @return 目标表名
     * @author billow
     */
    public static String getTargetTableByMonth(LocalDateTime dateTime, String logicTableName) {
        String month = calculateMonthSuffix(dateTime);
        return logicTableName + "_" + month;
    }

    // ========================== 订单号相关方法 ==========================

    /**
     * 从订单号中提取日期
     * 调用 NumUtil 工具类的方法
     *
     * @param orderSn 订单号
     * @return LocalDate
     * @author billow
     */
    public static LocalDate extractDateFromOrderSn(String orderSn) {
        try {
            return com.billow.tools.generator.NumUtil.extractDateTimeFromOrderSn(orderSn).toLocalDate();
        } catch (Exception e) {
            throw new IllegalArgumentException("从订单号[" + orderSn + "]提取日期失败：" + e.getMessage(), e);
        }
    }

    /**
     * 根据订单号获取目标年月
     *
     * @param baseDate 从订单号提取的日期
     * @return 年月字符串，如 "202501"
     * @author billow
     */
    public static String getTargetYearMonthByOrderSn(LocalDate baseDate) {
        if (baseDate == null) {
            throw new IllegalArgumentException("基准日期不能为空");
        }
        return baseDate.format(YEAR_MONTH_FORMAT);
    }

    // ========================== 支付时间相关方法 ==========================

    /**
     * 根据支付时间获取目标年月列表（支持临界值双表逻辑）
     * 如果是月初几天，可能需要查询上个月的表
     *
     * @param payDate 支付日期
     * @return 目标年月列表
     * @author billow
     */
    public static List<String> getTargetYearMonthsByPayTime(LocalDate payDate) {
        if (payDate == null) {
            throw new IllegalArgumentException("支付日期不能为空");
        }

        List<String> targetYearMonths = new ArrayList<>();
        
        // 当前月份
        String currentYearMonth = payDate.format(YEAR_MONTH_FORMAT);
        targetYearMonths.add(currentYearMonth);

        // 临界值逻辑：如果是月初前3天，也查询上个月的表
        if (payDate.getDayOfMonth() <= PAY_TIME_CRITICAL_DAYS) {
            LocalDate previousMonth = payDate.minusMonths(1);
            String previousYearMonth = previousMonth.format(YEAR_MONTH_FORMAT);
            targetYearMonths.add(0, previousYearMonth); // 添加到列表开头
        }

        return targetYearMonths;
    }

    // ========================== 范围查询相关方法 ==========================

    /**
     * 生成时间范围内的所有年月
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 年月列表
     * @author billow
     */
    public static List<String> generateYearMonthRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("开始日期和结束日期不能为空");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("开始日期不能晚于结束日期");
        }

        List<String> yearMonths = new ArrayList<>();
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate endMonth = endDate.withDayOfMonth(1);

        while (!currentMonth.isAfter(endMonth)) {
            yearMonths.add(currentMonth.format(YEAR_MONTH_FORMAT));
            currentMonth = currentMonth.plusMonths(1);
        }

        return yearMonths;
    }

    /**
     * 生成时间范围内的所有月份
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 月份列表
     * @author billow
     */
    public static List<String> generateMonthRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("开始日期和结束日期不能为空");
        }

        Set<String> months = new HashSet<>();
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate endMonth = endDate.withDayOfMonth(1);

        while (!currentMonth.isAfter(endMonth)) {
            months.add(currentMonth.format(MONTH_FORMAT));
            currentMonth = currentMonth.plusMonths(1);
        }

        return new ArrayList<>(months);
    }

    // ========================== 表名验证方法 ==========================

    /**
     * 验证目标表是否存在于可用表列表中
     * 如果不存在，记录警告日志并返回所有可用表
     *
     * @param targetTable         目标表名
     * @param availableTargetNames 可用表名列表
     * @param logicTableName      逻辑表名
     * @param currentValue        当前分片键值
     * @return 如果目标表存在返回包含目标表的集合，否则返回所有可用表
     * @author billow
     */
    public static Collection<String> validateAndGetTargetTables(String targetTable, Collection<String> availableTargetNames, 
                                                               String logicTableName, Object currentValue) {
        if (availableTargetNames.contains(targetTable)) {
            return Collections.singletonList(targetTable);
        } else {
            // 记录警告日志并返回所有表
            log.warn(LOG_PREFIX_TEMPLATE, logicTableName, 
                    String.format("未找到目标分片表[%s]，当前分片键值[%s]，将路由到所有可用表：%s", 
                            targetTable, currentValue, availableTargetNames));
            return availableTargetNames;
        }
    }

    /**
     * 过滤并排序可用的目标表
     *
     * @param targetTables         目标表名集合
     * @param availableTargetNames 可用表名列表
     * @return 排序后的可用目标表列表
     * @author billow
     */
    public static List<String> filterAndSortTargetTables(Collection<String> targetTables, Collection<String> availableTargetNames) {
        return targetTables.stream()
                .filter(availableTargetNames::contains)
                .sorted()
                .collect(ArrayList::new, (list, item) -> list.add(item), ArrayList::addAll);
    }
}