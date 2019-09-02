package com.billow.system.service;

import com.billow.tools.enums.ApplyTypeEnum;

public interface ApplyInfoService<T> {

    /**
     * 提交申请信息，如果存在 applyType + StartApplyProcess 的实现类，会执行其中的方法
     *
     * @param operator
     * @param t
     * @param applyType
     * @return void
     * @author LiuYongTao
     * @date 2019/9/2 17:21
     */
    void submitApplyInfo(String operator, ApplyTypeEnum applyType, T t);
}
