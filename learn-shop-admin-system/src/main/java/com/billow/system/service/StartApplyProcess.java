package com.billow.system.service;

import com.billow.system.pojo.po.ApplyInfoPo;

import java.util.HashMap;
import java.util.Map;

/**
 * 启动申请流程实例的回调<br/>
 * 实现类的名称是 ApplyTypeEnum 中的 applyType（首字母大写） + StartApplyProcess， 如：LeaveStartApplyProcess。<br/>
 * 如果不遵循请设置spring 容器中的名称为 applyType + StartApplyProcess
 *
 * @author LiuYongTao
 * @date 2019/9/2 17:36
 */
public interface StartApplyProcess<T> {

    /**
     * 构建 applyData 数据
     *
     * @param t 调用 submitApplyInfo 方法传入的参数类型一致
     * @return java.lang.String
     * @author billow
     * @date 2019/9/8 20:06
     */
    default String genApplyData(T t) {
        return null;
    }


    /**
     * 流程启动之前，主要用于设置启动参数
     *
     * @param t 调用 submitApplyInfo 方法传入的参数类型一致
     * @return 启动工作流的参数
     * @author LiuYongTao
     * @date 2019/9/2 17:41
     */
    default Map<String, Object> startProcessBefore(T t) {
        return new HashMap<>();
    }

    /**
     * 流程启动之后，主要用于返回启动后参数
     *
     * @param applyInfo
     * @return void
     * @author LiuYongTao
     * @date 2019/9/2 17:41
     */
    default void startProcessAfter(ApplyInfoPo applyInfo) {
    }

//    /**
//     * 主要用于退回后重新提交时，设置工作流运行参数
//     *
//     * @param t
//     * @return 启动工作流的参数
//     * @author billow
//     * @date 2019/9/8 20:21
//     */
//    default Map<String, Object> submitReWorkBefore(T t) {
//        return new HashMap<>();
//    }
}
