package com.billow.pojo.vo;


import com.billow.pojo.po.ScheduleJobPo;

import java.io.Serializable;


public class ScheduleJobVo extends ScheduleJobPo implements Serializable {

    private static final long serialVersionUID = -549836299658030636L;

    //运行状态
    private String statusName;
    //任务是否有状态,0-无（单线程），1-有（多线程）
    private String isConcurrentName;

    /**
     * 运行状态
     *
     * @return
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 运行状态
     *
     * @param statusName
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 任务是否有状态,0-无（单线程），1-有（多线程）
     *
     * @return
     */
    public String getIsConcurrentName() {
        return isConcurrentName;
    }

    /**
     * 任务是否有状态,0-无（单线程），1-有（多线程）
     *
     * @param isConcurrentName
     */
    public void setIsConcurrentName(String isConcurrentName) {
        this.isConcurrentName = isConcurrentName;
    }
}
