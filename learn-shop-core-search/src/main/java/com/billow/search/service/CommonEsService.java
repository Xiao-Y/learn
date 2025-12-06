package com.billow.search.service;

import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.kernel.BaseEsMapper;

import java.util.List;
import java.util.Map;

/**
 * 通用 ES 查询服务接口
 *
 * @author billow
 * @since 2025-01-01
 */
public interface CommonEsService {

    /**
     * 通用查询方法
     *
     * @param mapper ES Mapper
     * @param params 查询参数
     * @param clazz  实体类
     * @param <T>    实体类型
     * @return 查询结果列表
     */
    <T> List<T> query(BaseEsMapper<T> mapper, Map<String, String> params, Class<T> clazz);

    /**
     * 通用分页查询方法
     *
     * @param mapper   ES Mapper
     * @param params   查询参数
     * @param clazz    实体类
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param <T>      实体类型
     * @return 查询结果列表
     */
    <T> List<T> queryPage(BaseEsMapper<T> mapper, Map<String, String> params, Class<T> clazz, Integer pageNum, Integer pageSize);

    /**
     * 通用查询总数方法
     *
     * @param mapper ES Mapper
     * @param params 查询参数
     * @param clazz  实体类
     * @param <T>    实体类型
     * @return 总数
     */
    <T> Long count(BaseEsMapper<T> mapper, Map<String, String> params, Class<T> clazz);

    /**
     * 构建查询包装器
     *
     * @param params 查询参数
     * @param clazz  实体类
     * @param <T>    实体类型
     * @return 查询包装器
     */
    <T> LambdaEsQueryWrapper<T> buildWrapper(Map<String, String> params, Class<T> clazz);
}
