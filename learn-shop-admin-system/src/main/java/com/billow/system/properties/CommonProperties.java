package com.billow.system.properties;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-07-26 17:14
 */
@Data
public class CommonProperties {

    LinuxProperties linux = new LinuxProperties();

    WinProperties win = new WinProperties();

    /**
     * 映射处理的路径，当 url 中有 /displayImag 时，资源映射到 D:/temp-rainy/
     *
     * @author LiuYongTao
     * @date 2019/7/26 17:24
     */
    private String imageMapping;


    /**
     * markdown 图片保存的路径
     *
     * @author LiuYongTao
     * @date 2019/8/8 10:21
     */
    private String markdownImgPath;

    /**
     * userIcon 图片保存的路径
     *
     * @author LiuYongTao
     * @date 2019/8/8 10:21
     */
    private String userIconImgPath;

    /**
     * 启动时，是否初始数据（首次启动时需要设置为true,默认为true）
     *
     * @author LiuYongTao
     * @date 2019/8/8 10:21
     */
    private Boolean startInitData = true;
}
