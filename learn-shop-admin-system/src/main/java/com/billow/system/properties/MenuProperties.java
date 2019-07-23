package com.billow.system.properties;

import lombok.Data;

/**
 * 菜单相关配置
 *
 * @author liuyongtao
 * @create 2019-07-23 9:28
 */
@Data
public class MenuProperties {

    // 菜单优先从缓存中读出
    private boolean readCache = false;
    // 菜单写入缓存中
    private boolean writeCache = false;
}
