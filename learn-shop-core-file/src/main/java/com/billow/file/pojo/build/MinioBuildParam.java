package com.billow.file.pojo.build;

import lombok.Data;

/**
 * @author 千面
 * @date 2025-09-16 20:45:53
 */
@Data
public class MinioBuildParam {
    /**
     * 对象名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件url
     */
    private String url;
}
