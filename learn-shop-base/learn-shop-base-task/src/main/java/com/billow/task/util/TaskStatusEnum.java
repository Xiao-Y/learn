package com.billow.task.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TaskStatusEnum
 * <p>
 * S：执行成功，W：未执行，I：执行中，F：执行失败
 *
 * @author 千面
 * @date 2026/1/20 8:53
 */
@Getter
@AllArgsConstructor
public enum TaskStatusEnum {

    W("未执行"),
    I("执行中"),
    S("执行成功"),
    F("执行失败");
    private String desc;

}
