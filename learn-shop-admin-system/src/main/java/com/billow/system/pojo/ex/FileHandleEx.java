package com.billow.system.pojo.ex;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyongtao
 * @create 2019-07-26 15:03
 */
@Data
public class FileHandleEx implements Serializable {

    @ApiModelProperty("文件的位置")
    private int pos;

    @ApiModelProperty("文件路径")
    private String fileUrl;

    @ApiModelProperty("文件名（不带后缀）")
    private String newFileName;

    @ApiModelProperty("文件保存的完整路径")
    private String filePath;
}
