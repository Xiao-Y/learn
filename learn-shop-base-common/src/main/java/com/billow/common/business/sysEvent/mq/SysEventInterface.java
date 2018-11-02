package com.billow.common.business.sysEvent.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SysEventInterface {

    /**
     * 系统事务处理,待发布
     */
    String OUT_SYS_EVENT_PUBLISH = "outSysEventPublish";
    String IN_SYS_EVENT_PUBLISH = "inSysEventPublish";
    /**
     * 系统事务处理,待处理
     */
    String OUT_SYS_EVENT_PROCESS = "sysEventProcess";

    @Output(OUT_SYS_EVENT_PUBLISH)
    MessageChannel outSysEventPublish();

    @Input(IN_SYS_EVENT_PUBLISH)
    SubscribableChannel inSysEventPublish();

    @Output(OUT_SYS_EVENT_PROCESS)
    MessageChannel outSysEventProcess();

}
