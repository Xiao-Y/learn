package com.billow.common.shardingsphere.rule;

import com.billow.common.utils.ShardingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 5.5.2 版本标准年月分片算法（满足泛型约束 T extends Comparable<?>）
 * 兼容 String/LocalDateTime/Date 三种时间类型（均实现 Comparable 接口）
 * 支持按年月分表，不同年份的相同月份存储在不同表中（表名后缀：_202401, _202402, ..., _202412, _202501, ...）
 *
 * @author billow
 */
@Slf4j
@Component
public class GeneralYearMonthStandardAlgorithm implements StandardShardingAlgorithm<Comparable<?>> {

    // 月份起始值（默认自然年1月，支持配置自定义，如配置为4则按4月为第1个分表月）
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
                // 校验月份起始值合法性（1~12）
                if (monthStart < 1 || monthStart > 12) {
                    throw new IllegalArgumentException("monthStart 必须是 1~12 的整数");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("monthStart 必须是整数类型", e);
            }
        }
    }

    /**
     * 精准分片：处理 =/IN 条件（泛型为 Comparable<?>，满足接口约束）
     * 
     * @param availableTargetNames 可用目标表名集合
     * @param shardingValue 分片值
     * @return 目标表名
     * @author billow
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Comparable<?>> shardingValue) {
        // 1. 转换分片键为 LocalDateTime（使用 ShardingUtils 统一处理）
        LocalDateTime time = ShardingUtils.convertToLocalDateTime(shardingValue.getValue());
        if (time == null) {
            throw new IllegalArgumentException(String.format("表[%s]的分片键[%s]值不能为空",
                    shardingValue.getLogicTableName(), shardingValue.getColumnName()));
        }

        // 2. 计算目标表名（使用 ShardingUtils）
        String targetTable = ShardingUtils.getTargetTableByYearMonth(time, shardingValue.getLogicTableName());

        // 3. 使用新的验证方法，如果表不存在则返回所有表并记录警告
        Collection<String> validatedTables = ShardingUtils.validateAndGetTargetTables(
                targetTable, availableTargetNames, shardingValue.getLogicTableName(), shardingValue.getValue());
        
        // 对于精准查询，返回第一个表（如果验证通过则是目标表，否则是所有表中的第一个）
        return validatedTables.iterator().next();
    }

    /**
     * 范围分片：处理 BETWEEN AND 条件（泛型为 Comparable<?>）
     * 
     * @param availableTargetNames 可用目标表名集合
     * @param shardingValue 分片值
     * @return 目标表名集合
     * @author billow
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Comparable<?>> shardingValue) {
        // 1. 解析范围起止时间（使用 ShardingUtils 统一处理）
        LocalDateTime start = ShardingUtils.convertToLocalDateTime(shardingValue.getValueRange().lowerEndpoint());
        LocalDateTime end = ShardingUtils.convertToLocalDateTime(shardingValue.getValueRange().upperEndpoint());
        if (start == null || end == null) {
            throw new IllegalArgumentException(String.format("表[%s]的分片键[%s]范围值不能为空",
                    shardingValue.getLogicTableName(), shardingValue.getColumnName()));
        }

        // 2. 生成范围内所有年月（使用 ShardingUtils）
        List<String> yearMonths = ShardingUtils.generateYearMonthRange(start.toLocalDate(), end.toLocalDate());

        // 3. 转换为目标表名并过滤可用表
        List<String> targetTables = new ArrayList<>();
        for (String yearMonth : yearMonths) {
            String targetTable = shardingValue.getLogicTableName() + "_" + yearMonth;
            targetTables.add(targetTable);
        }

        // 4. 过滤并排序（使用 ShardingUtils）
        return ShardingUtils.filterAndSortTargetTables(targetTables, availableTargetNames);
    }

    /**
     * 算法类型标识（年月分片）
     * 
     * @return 算法类型字符串
     * @author billow
     */
    @Override
    public String getType() {
        return "GENERAL_YEAR_MONTH_STANDARD";
    }
}