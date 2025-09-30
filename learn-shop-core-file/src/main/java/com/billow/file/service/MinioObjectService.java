package com.billow.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.file.pojo.po.MinioObject;

import java.util.List;

/**
 * MinIO Object的Service接口
 * 定义业务逻辑方法
 */
public interface MinioObjectService extends IService<MinioObject> {

    /**
     * 根据用户ID查询对象列表
     * @param userId 用户ID
     * @return 对象列表
     */
    List<MinioObject> listByUserId(String userId);

    /**
     * 根据Bucket查询对象列表
     * @param bucket 桶名
     * @return 对象列表
     */
    List<MinioObject> listByBucket(String bucket);

    /**
     * 根据MD5查询对象
     * @param md5 文件MD5值
     * @return 对应的对象
     */
    MinioObject getByMd5(String md5);
}
