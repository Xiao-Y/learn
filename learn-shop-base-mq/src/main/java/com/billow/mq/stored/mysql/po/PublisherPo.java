package com.billow.mq.stored.mysql.po;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liuyongtao
 * @create 2019-09-30 9:02
 */
@Data
@Entity
@Table(name = "mq_publisher")
public class PublisherPo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String exchangeName;
    private String message;
    private String rabbitTemplateName;
    private String routingKey;
    private String body;
    private String status;
    private String correlationId;
    // 重试次数
    private Integer tryCount;
    // 下次重试时间
    private Date nextRetry;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss.SSS")
    private Date updateTime;

}
