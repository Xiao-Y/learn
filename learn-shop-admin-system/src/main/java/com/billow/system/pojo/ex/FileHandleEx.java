package com.billow.system.pojo.ex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyongtao
 * @create 2019-07-26 15:03
 */
@Data
public class FileHandleEx implements Serializable {

    @Schema(title = "文件的位置")
    private int pos;

    @Schema(title = "文件路径")
    private String fileUrl;

    @Schema(title = "文件名（不带后缀）")
    private String newFileName;

    @Schema(title = "文件保存的完整路径")
    private String filePath;
}
