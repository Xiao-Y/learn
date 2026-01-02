package com.billow.common.shardingsphere.rule;

import com.billow.common.utils.ShardingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.util.Collection;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 分片规则：取分片字段最后两位数字，对指定分表数量取模
 * 支持分片字段类型：String/Number（Integer/Long/Short等）/CharSequence
 * 支持自定义分表数量（numTables），表名后缀规则：_0、_1 ... _(numTables-1)
 * 使用 ShardingUtils 工具类进行统一处理
 *
 * @author billow
 */
@Slf4j
public class LastTwoDigitsModStandardAlgorithm implements StandardShardingAlgorithm<Comparable<?>> {

    // 分表数量（默认10张表，支持配置自定义）
    private int numTables = 10;

    /**
     * 初始化：读取配置中的自定义参数（numTables）
     * 
     * @param props 配置属性
     * @author billow
     */
    @Override
    public void init(Properties props) {
        // 读取分表数量配置
        if (props.containsKey("numTables")) {
            try {
                numTables = Integer.parseInt(props.getProperty("numTables"));
                // 校验分表数量合法性（必须是正整数）
                if (numTables <= 0) {
                    throw new IllegalArgumentException("分表数量numTables必须是正整数（如10、20）");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("分表数量numTables必须是整数类型", e);
            }
        }
    }

    /**
     * 精准分片：处理 =/IN 条件（核心逻辑：最后两位取模）
     * 
     * @param availableTargetNames 可用目标表名集合
     * @param shardingValue 分片值
     * @return 目标表名
     * @author billow
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Comparable<?>> shardingValue) {
        // 1. 获取分片键值并校验非空
        Comparable<?> value = shardingValue.getValue();
        if (value == null) {
            throw new IllegalArgumentException(String.format("表[%s]的分片键[%s]值不能为空",
                    shardingValue.getLogicTableName(), shardingValue.getColumnName()));
        }

        // 2. 提取分片字段最后两位数字（使用 ShardingUtils）
        int lastTwoDigits = ShardingUtils.extractLastTwoDigits(value);

        // 3. 计算分片索引（对分表数量取模，索引从0开始）
        int shardIndex = lastTwoDigits % numTables;

        // 4. 拼接目标物理表名（逻辑表名 + _ + 分片索引，如 t_order_0）
        String targetTable = shardingValue.getLogicTableName() + "_" + shardIndex;

        // 5. 使用新的验证方法，如果表不存在则返回所有表并记录警告
        Collection<String> validatedTables = ShardingUtils.validateAndGetTargetTables(
                targetTable, availableTargetNames, shardingValue.getLogicTableName(), shardingValue.getValue());
        
        // 对于精准查询，返回第一个表（如果验证通过则是目标表，否则是所有表中的第一个）
        return validatedTables.iterator().next();
    }

    /**
     * 范围分片：处理 BETWEEN AND 条件
     * 【注意】因取最后两位取模的特性，范围查询无法精准路由到具体分片表，返回所有可用表
     * 
     * @param availableTargetNames 可用目标表名集合
     * @param shardingValue 分片值
     * @return 目标表名集合
     * @author billow
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Comparable<?>> shardingValue) {
        // 1. 校验范围值非空
        if (shardingValue.getValueRange().lowerEndpoint() == null || shardingValue.getValueRange().upperEndpoint() == null) {
            throw new IllegalArgumentException(String.format("表[%s]的分片键[%s]范围值不能为空",
                    shardingValue.getLogicTableName(), shardingValue.getColumnName()));
        }

        // 2. 因取模逻辑无法精准匹配范围，返回所有可用表（也可根据业务需求调整，如提示范围查询全表扫描）
        // 排序保证查询顺序（_0→_9→_10...）（使用 ShardingUtils）
        return availableTargetNames.stream()
                .sorted(Comparator.comparingInt(ShardingUtils::extractShardIndexFromTableName))
                .collect(Collectors.toList());
    }

    /**
     * 算法类型标识（最后两位取模分片）
     * 
     * @return 算法类型字符串
     * @author billow
     */
    @Override
    public String getType() {
        return "LAST_TWO_DIGITS_MOD_STANDARD";
    }
}