package com.billow.search.consumer.handle;

import com.billow.search.pojo.vo.CanalDbVo;

/**
 * 更新表数据
 *
 * @author 千面
 * @date 2022/8/9 9:14
 */
public interface UpdateTableData {

    /**
     * 执行方法
     *
     * @param canalDbVo
     * @return void
     * @author 千面
     * @date 2022/8/9 9:14
     */
    void execute(CanalDbVo canalDbVo);
}
