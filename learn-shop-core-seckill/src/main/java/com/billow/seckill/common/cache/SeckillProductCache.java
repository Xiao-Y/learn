package com.billow.seckill.common.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billow.common.redis.RedisUtils;
import com.billow.seckill.common.enums.LuaScriptEnum;
import com.billow.seckill.common.enums.SeckillStatEnum;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.vo.SeckillProductVo;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀缓存操作
 *
 * @author liuyongtao
 * @since 2021-6-11 10:11
 */
@Slf4j
@Component
@RefreshScope
public class SeckillProductCache {

    /**
     * 设置 redis 中秒杀活动结束后多少分钟数据过期（单位：分钟）
     *
     * @author liuyongtao
     * @since 2021-6-11 11:21
     */
    @Value("${seckill.clear-data-end-arfer:30}")
    private long clearDataEndArfer;

    /**
     * 库存/秒杀商品缓存过期时间
     *
     * @author liuyongtao
     * @since 2021-6-11 11:21
     */
    public long clearDataEndArferTime = clearDataEndArfer * 60 * 1000;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 生成 秒杀商品key
     *
     * @param seckillProductId 秒杀id
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:03
     */
    public String genSeckillProductKey(Long seckillProductId) {
        return RedisCst.genKey(RedisCst.SECKILL_PRODUCT, seckillProductId.toString());
    }

    /**
     * 生成 库存key
     *
     * @param seckillProductId 秒杀id
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:03
     */
    public String genSeckillStockKey(Long seckillProductId) {
        return RedisCst.genKey(RedisCst.SECKILL_STOCK, seckillProductId.toString());
    }

    /**
     * 秒杀用户key
     *
     * @param seckillProductId 秒杀id
     * @param userCode  用户code
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:05
     */
    public String genSeckillLockKey(Long seckillProductId, String userCode) {
        return RedisCst.genKey(RedisCst.SECKILL_LOCK, seckillProductId.toString(), userCode);
    }

    /**
     * 更新秒杀库存缓存
     *
     * @param seckillProductId id
     * @param endTime   秒杀结束时间
     * @param nowDate   当前时间
     * @param stock     库存
     * @return {@link String}
     * @author liuyongtao
     * @since 2021-6-11 10:31
     */
    public String saveSeckillStockCache(Long seckillProductId, Date endTime, Date nowDate, Integer stock) {
        String seckillStockKey = this.genSeckillStockKey(seckillProductId);
        // 延迟后的时间
        long exp = Math.max(endTime.getTime() - nowDate.getTime() + clearDataEndArferTime, 0);
        redisUtils.setObj(seckillStockKey, stock, exp, TimeUnit.MILLISECONDS);
        return seckillStockKey;
    }

    /**
     * 保存秒杀商品数据
     *
     * @param seckillPo 秒杀商品数据
     * @param nowDate   当前时间
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public String saveSeckillCache(SeckillPo seckillPo, Date nowDate) {
        String seckillProductKey = this.genSeckillProductKey(seckillPo.getId());
        // 延迟后的时间
        long exp = Math.max(seckillPo.getEndTime().getTime() - nowDate.getTime() + clearDataEndArferTime, 0);
        redisUtils.setObj(seckillProductKey, seckillPo, exp, TimeUnit.MILLISECONDS);
        return seckillProductKey;
    }

    /**
     * 查询库存数据
     *
     * @param seckillProductId
     * @return {@link long}
     * @author liuyongtao
     * @since 2021-6-11 10:34
     */
    public int findSeckillStockCache(Long seckillProductId) {
        String seckillStockKey = this.genSeckillStockKey(seckillProductId);
        Integer stock = redisUtils.getObj(seckillStockKey);
        return stock == null ? 0 : stock;
    }

    /**
     * 执行秒杀
     * <p>
     * 1.减少库存（SECKILL:STOCK:{seckillProductId}:{userCode}）
     * <p>
     * 2.保存秒杀成功数据（SECKILL:LOCK:{seckillProductId}:{userCode}）
     * <p>
     * 3.设置秒杀成功数据缓存时间（seckill.order-exp:30）
     *
     * @param killedVo
     * @return {@link SeckillStatEnum}
     * @author liuyongtao
     * @since 2021-6-11 11:10
     */
    public SeckillStatEnum executeSeckill(SuccessKilledVo killedVo) {
        String successKilledJson = JSON.toJSONString(killedVo);
        log.info("秒杀成功时需要插入的订单数据：{}", successKilledJson);
        String seckillLockKey = this.genSeckillLockKey(killedVo.getSeckillId(), killedVo.getUsercode());
        String seckillStockKey = this.genSeckillStockKey(killedVo.getSeckillId());
        log.info("入参：[seckillLockKey]->{},[seckillStockKey]->{}", seckillLockKey, seckillStockKey);
        // 脚本配置，key 集合，秒杀商信息，付款过期时间（单位：秒）
        Long execute = LuaUtil.execute(LuaScriptEnum.SEC_KILL,
                Arrays.asList(seckillStockKey, seckillLockKey),
                successKilledJson);
        if (execute == null) {
            log.info("秒杀异常,[seckillLockKey]->{},[seckillStockKey]->{}", seckillLockKey, seckillStockKey);
            return SeckillStatEnum.FAIL;
        }
        SeckillStatEnum statEnum = SeckillStatEnum.of(execute.intValue());
        log.info("返回值：execute：{} {}", execute, statEnum);
        return statEnum;
    }

    /**
     * 获取秒杀商品数据
     *
     * @param seckillProductId
     * @return {@link SeckillPo}
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public SeckillPo findSeckillCache(Long seckillProductId) {
        String seckillProductKey = this.genSeckillProductKey(seckillProductId);
        SeckillProductCache seckillProductCache = redisUtils.getObj(seckillProductKey);
        if(seckillProductCache != null){
            seckillProductCache
        }

        return JSONObject.parseObject(killedPoJson, SeckillPo.class);
    }
}
