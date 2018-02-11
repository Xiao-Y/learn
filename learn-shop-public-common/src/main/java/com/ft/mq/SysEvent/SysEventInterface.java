package com.ft.mq.SysEvent;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SysEventInterface {

    /**
     * 系统事务处理,待发布
     */
    String OUT_SYS_EVENT_PUBLISH = "sysEventPublish";
    /**
     * 系统事务处理,待处理
     */
    String OUT_SYS_EVENT_PROCESS = "sysEventProcess";

    @Output(OUT_SYS_EVENT_PUBLISH)
    MessageChannel outSysEventPublish();

    @Output(OUT_SYS_EVENT_PROCESS)
    MessageChannel outSysEventProcess();

}
