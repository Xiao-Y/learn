package com.ft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 夸系统事务运行操作,记录待发布事件
 *
 * @author liuyongtao
 * @create 2018-02-09 15:03
 */
@Entity
@Table(name = "sys_event_publish")
public class SysEventPublish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //事件状态, 枚举类型. 现在只有两个状态: 待发布(NEW), 已发布(PUBLISHED)
    private String status;
    private int num;
    //发布事件的服务器ip
    private String ip;
    //发布事件的类名和处理方法名
    private String className;
    //事件类型
    private String eventType;
    //事件内容
    private String payload;
    //事件创建时候
    private Date createDate;
    //事件修改时间
    private Date updateDate;

    /**
     * 主键
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

}
