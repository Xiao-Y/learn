package com.billow.seckill.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billow.common.redis.RedisUtils;
import com.billow.seckill.config.LuaConfiguration;
import com.billow.seckill.enums.LuaScriptEnum;
import com.billow.seckill.enums.SeckillStatEnum;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
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
public class SeckillCache {

    /**
     * 设置 redis 中秒杀活动结束后多少分钟数据过期（单位：分钟）
     *
     * @author liuyongtao
     * @since 2021-6-11 11:21
     */
    @Value("${seckill.clear-data-end-arfer:30}")
    private long clearDataEndArfer;

    /**
     * 订单过期时间（单位：分钟）
     *
     * @author liuyongtao
     * @since 2021-6-11 11:21
     */
    @Value("${seckill.order-exp:30}")
    private String seckillOrderExp;

    /**
     * 秒杀 lua 脚本
     *
     * @author liuyongtao
     * @see LuaConfiguration#seckillScript()
     * @since 2021-6-11 11:12
     */
//    @Resource
    private DefaultRedisScript<String> seckillScript;

    /**
     * 库存缓存过期时间
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
     * @param seckillId 秒杀id
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:03
     */
    public String genSeckillProductKey(Long seckillId) {
        return RedisCst.genKey(RedisCst.SECKILL_PRODUCT, seckillId.toString());
    }

    /**
     * 生成 库存key
     *
     * @param seckillId 秒杀id
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:03
     */
    public String genSeckillStockKey(Long seckillId) {
        return RedisCst.genKey(RedisCst.SECKILL_STOCK, seckillId.toString());
    }

    /**
     * 秒杀用户key
     *
     * @param seckillId 秒杀id
     * @param userCode  用户code
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:05
     */
    public String genSeckillLockKey(Long seckillId, String userCode) {
        return RedisCst.genKey(RedisCst.SECKILL_LOCK, seckillId.toString(), userCode);
    }

    /**
     * 更新秒杀库存缓存
     *
     * @param seckillId id
     * @param endTime   秒杀结束时间
     * @param nowDate   当前时间
     * @param stock     库存
     * @return {@link String}
     * @author liuyongtao
     * @since 2021-6-11 10:31
     */
    public String saveSeckillStockCache(Long seckillId, Date endTime, Date nowDate, Integer stock) {
        String seckillStockKey = this.genSeckillStockKey(seckillId);
        // 延迟后的时间
        long exp = endTime.getTime() - nowDate.getTime() + clearDataEndArferTime;
        redisUtils.setObj(seckillStockKey, stock, exp, TimeUnit.SECONDS);
        return seckillStockKey;
    }

    /**
     * 查询库存数据
     *
     * @param seckillId
     * @return {@link long}
     * @author liuyongtao
     * @since 2021-6-11 10:34
     */
    public int findSeckillStockCache(Long seckillId) {
        String seckillStockKey = this.genSeckillStockKey(seckillId);
        Integer stock = redisUtils.getObj(seckillStockKey);
        return stock == null ? 0 : stock;
    }

    /**
     * 执行秒杀
     * <p>
     * 1.减少库存（SECKILL:STOCK:{seckillId}:{userCode}）
     * <p>
     * 2.保存秒杀成功数据（SECKILL:LOCK:{seckillId}:{userCode}）
     * <p>
     * 3.设置秒杀成功数据缓存时间（seckill.order-exp:30）
     *
     * @param killedPo
     * @return {@link SeckillStatEnum}
     * @author liuyongtao
     * @since 2021-6-11 11:10
     */
    public SeckillStatEnum executeSeckill(SuccessKilledPo killedPo) {
        String successKilledJson = JSON.toJSONString(killedPo);
        log.info("秒杀成功时需要插入的订单数据：{}", successKilledJson);
        String seckillLockKey = this.genSeckillLockKey(killedPo.getSeckillId(), killedPo.getUsercode());
        String seckillStockKey = this.genSeckillStockKey(killedPo.getSeckillId());
        // 脚本配置，key 集合，秒杀商信息，付款过期时间（单位：秒）
//        List<String> keys = Arrays.asList(seckillStockKey, seckillLockKey);
//        log.info("入参：seckillOrderExp:{},keys:{}", seckillOrderExp, keys);
//        String execute = redisTemplate.execute(seckillScript, keys, successKilledJson, seckillOrderExp);
        Long execute = LuaUtil.execute(LuaScriptEnum.SEC_KILL,
                Arrays.asList(seckillStockKey, seckillLockKey),
                successKilledJson,
                seckillOrderExp);
        if (execute == null) {
            return SeckillStatEnum.UNDEFIND;
        }
        SeckillStatEnum statEnum = SeckillStatEnum.of(execute.intValue());
        log.info("返回值：execute：{} {}", execute, statEnum);
        return statEnum;
    }

    /**
     * 获取秒杀成功的数据
     *
     * @param seckillId
     * @param userCode
     * @return {@link SuccessKilledPo}
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public SuccessKilledPo findSuccessKilledCache(Long seckillId, String userCode) {
        String seckillLockKey = this.genSeckillLockKey(seckillId, userCode);
        String killedPoJson = redisTemplate.opsForValue().get(seckillLockKey);
        return JSONObject.parseObject(killedPoJson, SuccessKilledPo.class);
    }

    /**
     * 获取秒杀商品数据
     *
     * @param seckillId
     * @return {@link SeckillPo}
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public SeckillPo findSeckillCache(Long seckillId) {
        String seckillProductKey = this.genSeckillProductKey(seckillId);
        String killedPoJson = redisTemplate.opsForValue().get(seckillProductKey);
        return JSONObject.parseObject(killedPoJson, SeckillPo.class);
    }

    /**
     * 保存秒杀商品数据
     *
     * @param seckillPo
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public void saveSeckillCache(SeckillPo seckillPo) {
        String seckillProductKey = this.genSeckillProductKey(seckillPo.getId());
        redisUtils.setObj(seckillProductKey, seckillPo);
    }

//    public SeckillStatEnum executeSeckill1(SuccessKilledPo killedPo) {
////        String successKilledJson = JSON.toJSONString(killedPo);
////        log.info("秒杀成功数据：{}", successKilledJson);
////        Long seckillId = killedPo.getSeckillId();
////        String userCode = killedPo.getUsercode();
////        String seckillLockKey = this.genSeckillLockKey(seckillId, userCode);
////        String seckillStockKey = this.genSeckillStockKey(seckillId);
////        List<String> keys = Arrays.asList(seckillStockKey, seckillLockKey);
////        // 脚本配置，key 集合，秒杀商信息，付款过期时间（单位：秒）
////        Long execute = redisTemplate.execute(seckillScript, keys, successKilledJson, seckillOrderExp);
//////        SeckillStatEnum statEnum = SeckillStatEnum.of(execute.intValue());
////        log.info("seckillLockKey:{},seckillStockKey:{},statEnum:{}", seckillLockKey, seckillStockKey, execute);
//        this.executeSeckill(killedPo);
//        return SeckillStatEnum.SUCCESS;
//    }
}
