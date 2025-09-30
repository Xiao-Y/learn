package com.billow.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.file.pojo.po.MinioObject;
import org.apache.ibatis.annotations.Mapper;

/**
 * MinIO Object的Mapper接口
 * 提供数据库操作方法
 */
@Mapper
public interface MinioObjectMapper extends BaseMapper<MinioObject> {

    // 继承BaseMapper后，已包含基本的CRUD方法
    // 如需自定义查询，可以在此添加方法
}
