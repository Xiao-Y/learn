package com.billow.file.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 视频处理工具类
 * 专门负责视频截图功能
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Component
public class VideoProcessKit {

    @Value("${file.temp.dir:${java.io.tmpdir}}")
    private String tempDir;

    // 支持的视频文件类型
    private static final List<String> VIDEO_TYPES = Arrays.asList(
            "mp4", "avi", "mov", "wmv", "flv", "mkv", "webm", "m4v", "3gp", "rmvb"
    );

    /**
     * 为视频文件生成缩略图
     *
     * @param videoFilePath 视频文件路径
     * @param targetSecond  截取时间点（秒）
     * @return 缩略图的字节数组
     * @throws IOException 处理异常
     */
    public byte[] generateVideoThumbnail(String videoFilePath,String thumbnailFileName, double targetSecond) throws IOException {
        File videoFile = new File(videoFilePath);
        if (!videoFile.exists()) {
            log.warn("视频文件不存在: {}", videoFilePath);
            return null;
        }

        String fileExtension = FileUtil.extName(videoFile.getName());
        if (!isVideoFile(fileExtension)) {
            log.warn("文件类型不是视频文件: {}", fileExtension);
            return null;
        }

        String tempThumbnailPath = null;

        try {
//            // 1. 智能选择截取时间点
//            double optimalTime = getOptimalThumbnailTime(videoFilePath, targetSecond);

            // 2. 生成缩略图文件路径
            tempThumbnailPath = Paths.get(tempDir, thumbnailFileName).toString();

            // 3. 使用 VideoFrameExtractor 截取视频帧
            extractFrameAtSecond(videoFilePath, tempThumbnailPath, targetSecond);

            // 4. 读取缩略图文件为字节数组
            File thumbnailFile = new File(tempThumbnailPath);
            if (thumbnailFile.exists() && thumbnailFile.length() > 0) {
                byte[] thumbnailBytes = Files.readAllBytes(thumbnailFile.toPath());
                log.info("视频缩略图生成成功，大小: {} bytes", thumbnailBytes.length);
                return thumbnailBytes;
            } else {
                log.warn("缩略图文件生成失败或文件为空: {}", tempThumbnailPath);
                return null;
            }

        } catch (Exception e) {
            log.error("生成视频缩略图失败: {}", e.getMessage(), e);
            throw new IOException("生成视频缩略图失败", e);
        } finally {
            // 5. 清理临时缩略图文件
            cleanupTempFiles(tempThumbnailPath);
        }
    }

    /**
     * 检查文件是否为视频文件
     *
     * @param fileType 文件类型
     * @return 是否为视频文件
     */
    public boolean isVideoFile(String fileType) {
        if (StrUtil.isBlank(fileType)) {
            return false;
        }
        return VIDEO_TYPES.contains(fileType.toLowerCase());
    }

    /**
     * 获取视频文件信息
     *
     * @param videoFilePath 视频文件路径
     * @return 视频时长（秒）
     * @throws IOException 处理异常
     */
    public double getVideoDuration(String videoFilePath) throws IOException {
        File videoFile = new File(videoFilePath);
        if (!videoFile.exists()) {
            throw new IllegalArgumentException("视频文件不存在: " + videoFilePath);
        }

        String fileExtension = FileUtil.extName(videoFile.getName());
        if (!isVideoFile(fileExtension)) {
            throw new IllegalArgumentException("不是视频文件");
        }


        FFmpegFrameGrabber grabber = null;
        try {
            grabber = new FFmpegFrameGrabber(videoFilePath);
            grabber.start();
            return grabber.getLengthInTime() / (1000 * 1000 * 1000.0);
        } catch (Exception e) {
            log.error("获取视频时长失败：{}", e.getMessage(), e);
            throw new IOException("获取视频时长失败：" + e.getMessage(), e);
        } finally {
            if (grabber != null) {
                try {
                    grabber.stop();
                    grabber.close();
                } catch (Exception e) {
                    log.warn("关闭视频抓取器失败：{}", e.getMessage());
                }
            }
        }
    }

    /**
     * 智能选择截取时间点
     * 如果指定时间超出视频长度，则选择视频中间位置
     *
     * @param videoFilePath 视频文件路径
     * @param targetSecond  目标时间点
     * @return 实际截取时间点
     * @throws IOException 处理异常
     */
    public double getOptimalThumbnailTime(String videoFilePath, double targetSecond) throws IOException {
        double duration = getVideoDuration(videoFilePath);

        // 如果目标时间超出视频长度，选择视频中间位置
        if (targetSecond >= duration) {
            return Math.max(1.0, duration / 2);
        }

        // 如果目标时间小于1秒，设为1秒
        return Math.max(1.0, targetSecond);
    }

    /**
     * 截取视频指定时间的帧并保存为图片
     *
     * @param videoPath     视频文件路径（如：D:/test.mp4）
     * @param outputImgPath 输出图片路径（如：D:/frame.jpg）
     * @param targetSecond  要截取的时间（秒，如3表示第3秒）
     */
    public static void extractFrameAtSecond(String videoPath, String outputImgPath, double targetSecond) throws IOException {
        FFmpegFrameGrabber grabber = null;
        Java2DFrameConverter converter = new Java2DFrameConverter(); // 帧转BufferedImage工具

        try {
            // 初始化帧抓取器
            grabber = new FFmpegFrameGrabber(videoPath);
            grabber.start(); // 启动抓取器

            // 校验目标时间是否在视频时长范围内
            double videoTotalSeconds = grabber.getLengthInTime() / (1000 * 1000 * 1000.0);
            if (targetSecond < 0 || targetSecond > videoTotalSeconds) {
                log.warn("目标时间超出视频范围！视频总时长：{}秒，目标时间：{}秒", videoTotalSeconds, targetSecond);
                // 如果超出范围，取视频中间位置
                targetSecond = Math.min(targetSecond, videoTotalSeconds / 2);
            }

            // 设置抓取位置（单位：微秒，1秒=1000000微秒）
            grabber.setTimestamp((long) (targetSecond * 1000000));

            // 抓取帧并保存
            Frame frame;
            while ((frame = grabber.grabImage()) != null) {
                BufferedImage bufferedImage = converter.convert(frame);
                if (bufferedImage != null) {
                    // 保存图片（支持JPG/PNG/BMP等格式）
                    ImageIO.write(bufferedImage, "jpg", new File(outputImgPath));
                    log.debug("帧截取成功！保存路径：{}", outputImgPath);
                    break; // 仅截取一帧，跳出循环
                }
            }
        } catch (Exception e) {
            log.error("截取视频帧失败：{}", e.getMessage(), e);
            throw new IOException("截取视频帧失败：" + e.getMessage(), e);
        } finally {
            // 释放资源（必须执行，否则内存泄漏）
            if (grabber != null) {
                try {
                    grabber.stop();
                    grabber.close();
                } catch (Exception e) {
                    log.warn("关闭视频抓取器失败：{}", e.getMessage());
                }
            }
        }
    }

    /**
     * 清理临时文件
     */
    private void cleanupTempFiles(String... filePaths) {
        for (String filePath : filePaths) {
            if (StrUtil.isNotBlank(filePath)) {
                try {
                    File file = new File(filePath);
                    if (file.exists()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            log.debug("清理临时文件成功: {}", filePath);
                        } else {
                            log.warn("清理临时文件失败: {}", filePath);
                        }
                    }
                } catch (Exception e) {
                    log.warn("清理临时文件异常: {}", filePath, e);
                }
            }
        }
    }
}