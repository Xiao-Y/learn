package com.billow.file.job;

import cn.hutool.core.date.DateUtil;
import com.billow.file.pojo.po.MinioObject;
import com.billow.file.service.MinioObjectService;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 文件清理定时任务
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Component
public class FileCleanupJob {

    @Autowired
    private MinioObjectService minioObjectService;

    @Autowired
    private MinioClient minioClient;

    /**
     * 每天凌晨2点执行过期文件清理
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanExpiredFiles() {
        log.info("开始执行过期文件清理任务");
        
        try {
            // 查询所有过期文件
            List<MinioObject> expiredFiles = minioObjectService.queryChain()
                    .lt(MinioObject::getExpireTime, new Date())
                    .list();

            int cleanedCount = 0;
            int failedCount = 0;

            for (MinioObject expiredFile : expiredFiles) {
                try {
                    // 从 MinIO 删除文件
                    minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(expiredFile.getBucket())
                            .object(expiredFile.getObject())
                            .build());

                    // 如果有缩略图，也要删除
                    if (expiredFile.getThumbnailUrl() != null) {
                        // 根据缩略图URL找到对应的缩略图对象并删除
                        MinioObject thumbnail = minioObjectService.queryChain()
                                .eq(MinioObject::getUrl, expiredFile.getThumbnailUrl())
                                .one();
                        if (thumbnail != null) {
                            minioClient.removeObject(RemoveObjectArgs.builder()
                                    .bucket(thumbnail.getBucket())
                                    .object(thumbnail.getObject())
                                    .build());
                            minioObjectService.removeById(thumbnail.getId());
                        }
                    }

                    // 从数据库删除记录
                    minioObjectService.removeById(expiredFile.getId());
                    cleanedCount++;
                    
                    log.debug("清理过期文件: {}", expiredFile.getName());
                } catch (Exception e) {
                    failedCount++;
                    log.error("清理过期文件失败: {}, 错误: {}", expiredFile.getName(), e.getMessage());
                }
            }

            log.info("过期文件清理任务完成，共清理 {} 个文件，失败 {} 个", cleanedCount, failedCount);
        } catch (Exception e) {
            log.error("执行过期文件清理任务失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 每小时执行一次文件状态检查（可选）
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void checkFileStatus() {
        log.debug("执行文件状态检查任务");
        
        try {
            // 统计即将过期的文件数量（24小时内过期）
            long soonExpireCount = minioObjectService.queryChain()
                    .between(MinioObject::getExpireTime, 
                            LocalDateTime.now(), 
                            LocalDateTime.now().plusHours(24))
                    .count();

            if (soonExpireCount > 0) {
                log.info("发现 {} 个文件将在24小时内过期", soonExpireCount);
            }

            // 统计总文件数量
            long totalCount = minioObjectService.count();
            log.debug("当前系统中共有 {} 个文件", totalCount);

        } catch (Exception e) {
            log.error("执行文件状态检查任务失败: {}", e.getMessage(), e);
        }
    }
}