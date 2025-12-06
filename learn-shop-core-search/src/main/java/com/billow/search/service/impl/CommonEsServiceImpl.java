package com.billow.search.service.impl;

import com.billow.search.service.CommonEsService;
import com.billow.search.utils.EsQueryBuilder;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;
import org.dromara.easyes.core.kernel.BaseEsMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 通用 ES 查询服务实现
 *
 * @author billow
 * @since 2025-01-01
 */
@Service
public class CommonEsServiceImpl implements CommonEsService {

    @Override
    public <T> List<T> query(BaseEsMapper<T> mapper, Map<String, String> params, Class<T> clazz) {
        LambdaEsQueryWrapper<T> wrapper = buildWrapper(params, clazz);
        return mapper.selectList(wrapper);
    }

    @Override
    public <T> List<T> queryPage(BaseEsMapper<T> mapper, Map<String, String> params, Class<T> clazz, Integer pageNum, Integer pageSize) {
        LambdaEsQueryWrapper<T> wrapper = buildWrapper(params, clazz);
        wrapper.from((pageNum - 1) * pageSize);
        wrapper.size(pageSize);
        return mapper.selectList(wrapper);
    }

    @Override
    public <T> Long count(BaseEsMapper<T> mapper, Map<String, String> params, Class<T> clazz) {
        LambdaEsQueryWrapper<T> wrapper = buildWrapper(params, clazz);
        return mapper.selectCount(wrapper);
    }

    @Override
    public <T> LambdaEsQueryWrapper<T> buildWrapper(Map<String, String> params, Class<T> clazz) {
        LambdaEsQueryWrapper<T> wrapper = new LambdaEsQueryWrapper<>();
        return EsQueryBuilder.getInstance().buildQuery(wrapper, params, clazz);
    }
}
