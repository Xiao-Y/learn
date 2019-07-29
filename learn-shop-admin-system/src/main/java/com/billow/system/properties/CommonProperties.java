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
    private String wordResourceDandler;
}
