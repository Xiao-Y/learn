package com.billow.excel.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典转换
 *
 * @author 千面
 * @date 2023/11/24 10:44
 */
public interface ExcelCover {

    /**
     * 导入时转换
     *
     * @author 千面
     */
    default Map<String, String> importCover() {
        return new HashMap<>();
    }

    /**
     * 导出时转换
     *
     * @author 千面
     */
    default Map<String, String> exportCover() {
        return new HashMap<>();
    }
}
