package com.billow.file.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.billow.file.dao.MinioObjectMapper;
import com.billow.file.pojo.po.MinioObject;
import com.billow.file.service.MinioObjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MinIO Object的Service实现类
 * 实现具体的业务逻辑
 */
@Service
public class MinioObjectServiceImpl extends ServiceImpl<MinioObjectMapper, MinioObject> implements MinioObjectService {

    @Override
    public List<MinioObject> listByUserId(String userId) {
        QueryWrapper qw = QueryWrapper.create()
                .eq(MinioObject::getUserId, userId)
                .eq(MinioObject::getDeleteFlag, 0);
        return mapper.selectListByQuery(qw);
    }

    @Override
    public List<MinioObject> listByBucket(String bucket) {
        QueryWrapper qw = QueryWrapper.create()
                .eq(MinioObject::getBucket, bucket)
                .eq(MinioObject::getDeleteFlag, 0);
        return mapper.selectListByQuery(qw);
    }

    @Override
    public MinioObject getByMd5(String md5) {
        QueryWrapper qw = QueryWrapper.create()
                .eq(MinioObject::getMd5, md5)
                .eq(MinioObject::getDeleteFlag, 0);
        return mapper.selectOneByQuery(qw);
    }
}
