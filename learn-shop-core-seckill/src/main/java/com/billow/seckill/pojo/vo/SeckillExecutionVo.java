package com.billow.seckill.pojo.vo;

import com.billow.seckill.enums.SeckillStatEnum;
import lombok.Data;

/**
 * 秒杀返回对象
 *
 * @author liuyongtao
 * @since 2021-1-22 9:42
 */
@Data
public class SeckillExecutionVo {

    private Long seckillId;

    //秒杀执行结果状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功的订单对象
    private SuccessKilledVo successKilledVo;

    public SeckillExecutionVo(Long seckillId, SeckillStatEnum seckillStatEnum, SuccessKilledVo successKilledVo) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
        this.successKilledVo = successKilledVo;
    }

    public SeckillExecutionVo(Long seckillId, SeckillStatEnum seckillStatEnum) {
        this.seckillId = seckillId;
        this.state = seckillStatEnum.getState();
        this.stateInfo = seckillStatEnum.getStateInfo();
    }
}
