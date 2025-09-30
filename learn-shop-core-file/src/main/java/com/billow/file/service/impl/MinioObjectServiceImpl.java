package com.billow.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        QueryWrapper<MinioObject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("delete_flag", 0);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<MinioObject> listByBucket(String bucket) {
        QueryWrapper<MinioObject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bucket", bucket)
                .eq("delete_flag", 0);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public MinioObject getByMd5(String md5) {
        QueryWrapper<MinioObject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5)
                .eq("delete_flag", 0);
        return baseMapper.selectOne(queryWrapper);
    }
}
