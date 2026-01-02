package com.billow.common.shardingsphere.rule;

import cn.hutool.core.collection.CollUtil;
import com.billow.common.utils.ShardingUtils;
import com.billow.tools.generator.NumUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 订单表按年月分表复合分片算法
 * 完整场景支持：
 * 1. 有ID（精准查询）→ 以ID为主
 * 2. 有订单号（精准查询）→ 以订单号为主，支付时间跨月修正（单表）
 * 3. 无订单号、有创建时间（精准/范围查询）→ 仅返回创建时间当年月表（单表，对齐订单号逻辑）
 * 4. 无订单号、有支付时间（精准/范围查询）→ 临界值返回双表，正常返回单表
 * 5. 无分片键 → 路由到所有可用表
 * <p>
 * 表名格式：oms_order_202401, oms_order_202402, ..., oms_order_202412, oms_order_202501, ...
 * 使用 ShardingUtils 统一处理时间相关逻辑
 * 
 * @author billow
 */
@Slf4j
@Component
public class OrderComplexMonthShardingAlgorithm implements ComplexKeysShardingAlgorithm<Comparable<?>> {

    private static final String COLUMN_NAME_ID = "id";
    private static final String COLUMN_NAME_ORDER_SN = "order_sn";
    private static final String COLUMN_NAME_CREATE_TIME = "create_time";
    private static final String COLUMN_NAME_PAYMENT_TIME = "payment_time";

    // 月份起始值（默认自然年1月，支持配置自定义）
    private int monthStart = 1;

    /**
     * 初始化：读取配置中的自定义参数
     * 
     * @param props 配置属性
     * @author billow
     */
    @Override
    public void init(Properties props) {
        if (props.containsKey("monthStart")) {
            try {
                monthStart = Integer.parseInt(props.getProperty("monthStart"));
                if (monthStart < 1 || monthStart > 12) {
                    throw new IllegalArgumentException("monthStart 必须是 1~12 的整数");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("monthStart 必须是整数类型", e);
            }
        }
    }

    /**
     * 复合分片算法主方法
     * 
     * @param availableTargetNames 可用目标表名集合
     * @param shardingValue 复合分片值
     * @return 目标表名集合
     * @author billow
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ComplexKeysShardingValue<Comparable<?>> shardingValue) {
        String logicTableName = shardingValue.getLogicTableName();
        
        // 1. 提取精准查询的分片键值
        Map<String, Collection<Comparable<?>>> preciseValuesMap = shardingValue.getColumnNameAndShardingValuesMap();
        Collection<Comparable<?>> idValues = preciseValuesMap.get(COLUMN_NAME_ID);
        Collection<Comparable<?>> orderSnValues = preciseValuesMap.get(COLUMN_NAME_ORDER_SN);
        Collection<Comparable<?>> paymentTimeValues = preciseValuesMap.get(COLUMN_NAME_PAYMENT_TIME);
        Collection<Comparable<?>> createTimeValues = preciseValuesMap.get(COLUMN_NAME_CREATE_TIME);

        Set<String> targetTables = new HashSet<>();

        // 2. 场景：无任何分片键 → 路由到所有可用表
        if (CollUtil.isEmpty(orderSnValues)
                && CollUtil.isEmpty(idValues)
                && CollUtil.isEmpty(paymentTimeValues)
                && CollUtil.isEmpty(createTimeValues)
                && CollUtil.isEmpty(shardingValue.getColumnNameAndRangeValuesMap())) {
            return availableTargetNames;
        }

        // 3. 场景1：有ID（精准查询，最高优先级）→ 单表
        if (CollUtil.isNotEmpty(idValues)) {
            return handleIdPreciseQuery(logicTableName, idValues, availableTargetNames);
        }

        // 4. 场景2：有订单号（精准查询，优先处理）→ 单表
        if (CollUtil.isNotEmpty(orderSnValues)) {
            return handleOrderSnPreciseQuery(logicTableName, orderSnValues, availableTargetNames);
        }

        // 5. 场景3：无订单号、有创建时间 → 单表（对齐订单号逻辑）
        // 5.1 创建时间精准查询
        if (CollUtil.isNotEmpty(createTimeValues)) {
            return handleCreateTimePreciseQuery(logicTableName, createTimeValues, availableTargetNames);
        }
        // 5.2 创建时间范围查询
        Map<String, Range<Comparable<?>>> rangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        Range<Comparable<?>> createTimeRange = rangeValuesMap.get(COLUMN_NAME_CREATE_TIME);
        if (createTimeRange != null) {
            return handleCreateTimeRangeQuery(logicTableName, createTimeRange, availableTargetNames);
        }

        // 6. 场景4：无订单号、有支付时间 → 保留临界值双表逻辑
        // 6.1 支付时间精准查询
        if (CollUtil.isNotEmpty(paymentTimeValues)) {
            return handlePayTimePreciseQuery(logicTableName, paymentTimeValues, availableTargetNames);
        }
        // 6.2 支付时间范围查询
        Range<Comparable<?>> payTimeRange = rangeValuesMap.get(COLUMN_NAME_PAYMENT_TIME);
        if (payTimeRange != null) {
            return handlePayTimeRangeQuery(logicTableName, payTimeRange, availableTargetNames);
        }

        // 兜底：若仍无目标表，返回所有可用表
        return CollUtil.isNotEmpty(targetTables) ? targetTables : availableTargetNames;
    }

    /**
     * 处理ID精准查询（=）→ 单表（最高优先级）
     * 
     * @param logicTableName 逻辑表名
     * @param idValues ID值集合
     * @param availableTargetNames 可用目标表名集合
     * @return 目标表名集合
     * @author billow
     */
    private Set<String> handleIdPreciseQuery(String logicTableName, Collection<Comparable<?>> idValues,
                                            Collection<String> availableTargetNames) {
        Set<String> targetTables = new HashSet<>();
        for (Comparable<?> id : idValues) {
            if (id == null) continue;
            
            try {
                // 使用 ShardingUtils 从雪花ID中提取时间
                LocalDateTime dateTime;
                if (id instanceof Number) {
                    dateTime = ShardingUtils.extractDateFromId(((Number) id).longValue());
                } else {
                    dateTime = ShardingUtils.extractDateFromId(id.toString());
                }
                
                // 获取目标年月（YYYYMM格式）
                String targetYearMonth = ShardingUtils.calculateYearMonthSuffix(dateTime);
                String targetTable = logicTableName + "_" + targetYearMonth;
                
                // 使用新的验证方法，如果表不存在则返回所有表并记录警告
                Collection<String> validatedTables = ShardingUtils.validateAndGetTargetTables(
                        targetTable, availableTargetNames, logicTableName, id);
                targetTables.addAll(validatedTables);
                
            } catch (Exception e) {
                // ID格式错误或提取时间失败，记录警告并返回所有表
                log.warn(ShardingUtils.LOG_PREFIX_TEMPLATE, logicTableName, 
                        String.format("从ID[%s]提取时间失败，错误：%s，将路由到所有可用表：%s", 
                                id, e.getMessage(), availableTargetNames));
                return new HashSet<>(availableTargetNames);
            }
        }
        return targetTables;
    }

    /**
     * 处理订单号精准查询（=）→ 单表
     * 
     * @param logicTableName 逻辑表名
     * @param orderSnValues 订单号值集合
     * @param availableTargetNames 可用目标表名集合
     * @return 目标表名集合
     * @author billow
     */
    private Set<String> handleOrderSnPreciseQuery(String logicTableName, Collection<Comparable<?>> orderSnValues,
                                                  Collection<String> availableTargetNames) {
        Set<String> targetTables = new HashSet<>();
        for (Comparable<?> orderSn : orderSnValues) {
            if (orderSn == null) continue;
            
            try {
                // 直接调用 NumUtil 提取订单号中的日期时间，然后转为日期
                LocalDate baseDate = NumUtil.extractDateTimeFromOrderSn(orderSn.toString()).toLocalDate();
                // 获取目标年月（YYYYMM格式，如202412）
                String targetYearMonth = ShardingUtils.getTargetYearMonthByOrderSn(baseDate);
                // 直接使用年月作为表名后缀
                String targetTable = logicTableName + "_" + targetYearMonth;
                
                // 使用新的验证方法，如果表不存在则返回所有表并记录警告
                Collection<String> validatedTables = ShardingUtils.validateAndGetTargetTables(
                        targetTable, availableTargetNames, logicTableName, orderSn);
                targetTables.addAll(validatedTables);
                
            } catch (Exception e) {
                // 订单号格式错误，记录警告并返回所有表
                log.warn(ShardingUtils.LOG_PREFIX_TEMPLATE, logicTableName, 
                        String.format("订单号[%s]格式错误，错误：%s，将路由到所有可用表：%s", 
                                orderSn, e.getMessage(), availableTargetNames));
                return new HashSet<>(availableTargetNames);
            }
        }
        return targetTables;
    }

    /**
     * 处理创建时间精准查询（=）→ 仅返回当年月单表，无跨月表
     * 
     * @param logicTableName 逻辑表名
     * @param createTimeValues 创建时间值集合
     * @param availableTargetNames 可用目标表名集合
     * @return 目标表名集合
     * @author billow
     */
    private Set<String> handleCreateTimePreciseQuery(String logicTableName, Collection<Comparable<?>> createTimeValues,
                                                     Collection<String> availableTargetNames) {
        Set<String> targetTables = new HashSet<>();
        for (Comparable<?> createTime : createTimeValues) {
            if (createTime == null) continue;
            
            try {
                // 使用 ShardingUtils 转换时间
                LocalDate createDate = ShardingUtils.convertToLocalDate(createTime);
                if (createDate == null) continue;
                
                // 仅取创建时间当年月（无临界值跨月逻辑，对齐订单号）
                String targetYearMonth = createDate.format(ShardingUtils.YEAR_MONTH_FORMAT);
                String targetTable = logicTableName + "_" + targetYearMonth;
                
                // 使用新的验证方法，如果表不存在则返回所有表并记录警告
                Collection<String> validatedTables = ShardingUtils.validateAndGetTargetTables(
                        targetTable, availableTargetNames, logicTableName, createTime);
                targetTables.addAll(validatedTables);
                
            } catch (Exception e) {
                // 时间格式转换错误，记录警告并返回所有表
                log.warn(ShardingUtils.LOG_PREFIX_TEMPLATE, logicTableName, 
                        String.format("创建时间[%s]格式转换失败，错误：%s，将路由到所有可用表：%s", 
                                createTime, e.getMessage(), availableTargetNames));
                return new HashSet<>(availableTargetNames);
            }
        }
        return targetTables;
    }

    /**
     * 处理创建时间范围查询（between/>=/<=）→ 仅返回范围内日期的当年月表，无跨月表
     * 
     * @param logicTableName 逻辑表名
     * @param createTimeRange 创建时间范围
     * @param availableTargetNames 可用目标表名集合
     * @return 目标表名集合
     * @author billow
     */
    private Set<String> handleCreateTimeRangeQuery(String logicTableName, Range<Comparable<?>> createTimeRange,
                                                   Collection<String> availableTargetNames) {
        if (createTimeRange == null) {
            return new HashSet<>();
        }

        // 提取范围上下限
        Comparable<?> lower = createTimeRange.lowerEndpoint();
        Comparable<?> upper = createTimeRange.upperEndpoint();
        if (lower == null || upper == null) {
            return new HashSet<>();
        }

        // 使用 ShardingUtils 转换为LocalDate
        LocalDate startDate = ShardingUtils.convertToLocalDate(lower);
        LocalDate endDate = ShardingUtils.convertToLocalDate(upper);
        if (startDate == null || endDate == null) {
            return new HashSet<>();
        }

        // 使用 ShardingUtils 生成范围内的所有年月
        List<String> targetYearMonths = ShardingUtils.generateYearMonthRange(startDate, endDate);

        // 转换为分表名并过滤可用表
        List<String> targetTables = new ArrayList<>();
        for (String yearMonth : targetYearMonths) {
            String targetTable = logicTableName + "_" + yearMonth;
            targetTables.add(targetTable);
        }

        // 使用 ShardingUtils 过滤并排序
        List<String> filteredTables = ShardingUtils.filterAndSortTargetTables(targetTables, availableTargetNames);
        return new HashSet<>(filteredTables);
    }

    /**
     * 处理支付时间精准查询（=）→ 保留临界值双表逻辑
     * 
     * @param logicTableName 逻辑表名
     * @param payTimeValues 支付时间值集合
     * @param availableTargetNames 可用目标表名集合
     * @return 目标表名集合
     * @author billow
     */
    private Set<String> handlePayTimePreciseQuery(String logicTableName, Collection<Comparable<?>> payTimeValues,
                                                  Collection<String> availableTargetNames) {
        Set<String> targetTables = new HashSet<>();
        for (Comparable<?> payTime : payTimeValues) {
            if (payTime == null) continue;
            
            try {
                // 使用 ShardingUtils 转换时间
                LocalDate payDate = ShardingUtils.convertToLocalDate(payTime);
                if (payDate == null) continue;
                
                // 保留原有临界值双表逻辑，但改为年月格式
                List<String> targetYearMonths = ShardingUtils.getTargetYearMonthsByPayTime(payDate);
                for (String yearMonth : targetYearMonths) {
                    String targetTable = logicTableName + "_" + yearMonth;
                    
                    // 使用新的验证方法，如果表不存在则返回所有表并记录警告
                    Collection<String> validatedTables = ShardingUtils.validateAndGetTargetTables(
                            targetTable, availableTargetNames, logicTableName, payTime);
                    targetTables.addAll(validatedTables);
                }
                
            } catch (Exception e) {
                // 时间格式转换错误，记录警告并返回所有表
                log.warn(ShardingUtils.LOG_PREFIX_TEMPLATE, logicTableName, 
                        String.format("支付时间[%s]格式转换失败，错误：%s，将路由到所有可用表：%s", 
                                payTime, e.getMessage(), availableTargetNames));
                return new HashSet<>(availableTargetNames);
            }
        }
        return targetTables;
    }

    /**
     * 处理支付时间范围查询（between/>=/<=）→ 保留临界值双表逻辑
     * 
     * @param logicTableName 逻辑表名
     * @param payTimeRange 支付时间范围
     * @param availableTargetNames 可用目标表名集合
     * @return 目标表名集合
     * @author billow
     */
    private Set<String> handlePayTimeRangeQuery(String logicTableName, Range<Comparable<?>> payTimeRange,
                                                Collection<String> availableTargetNames) {
        if (payTimeRange == null) {
            return new HashSet<>();
        }

        // 提取范围上下限
        Comparable<?> lower = payTimeRange.lowerEndpoint();
        Comparable<?> upper = payTimeRange.upperEndpoint();
        if (lower == null || upper == null) {
            return new HashSet<>();
        }

        // 使用 ShardingUtils 转换为LocalDate
        LocalDate startDate = ShardingUtils.convertToLocalDate(lower);
        LocalDate endDate = ShardingUtils.convertToLocalDate(upper);
        if (startDate == null || endDate == null) {
            return new HashSet<>();
        }

        // 遍历范围中的所有日期，保留临界值双表逻辑
        Set<String> allTargetYearMonths = new HashSet<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            List<String> targetYearMonths = ShardingUtils.getTargetYearMonthsByPayTime(currentDate);
            allTargetYearMonths.addAll(targetYearMonths);
            currentDate = currentDate.plusDays(1);
        }

        // 转换为分表名并过滤可用表
        List<String> targetTables = new ArrayList<>();
        for (String yearMonth : allTargetYearMonths) {
            String targetTable = logicTableName + "_" + yearMonth;
            targetTables.add(targetTable);
        }

        // 使用 ShardingUtils 过滤并排序
        List<String> filteredTables = ShardingUtils.filterAndSortTargetTables(targetTables, availableTargetNames);
        return new HashSet<>(filteredTables);
    }

    /**
     * 获取算法类型标识
     * 
     * @return 算法类型字符串
     * @author billow
     */
    @Override
    public String getType() {
        return "ORDER_COMPLEX_YEAR_MONTH_SHARDING";
    }
}