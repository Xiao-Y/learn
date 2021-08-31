package com.billow.promotion.cache;

import com.billow.common.redis.RedisUtils;
import com.billow.promotion.common.cache.LuaUtil;
import com.billow.promotion.common.enums.LuaScriptEnum;
import com.billow.promotion.common.enums.SeckillStatEnum;
import com.billow.promotion.pojo.cache.SeckillCacheDto;
import com.billow.promotion.pojo.cache.SeckillProductCacheDto;
import com.billow.promotion.pojo.cache.SeckillSessionCacheDto;
import com.billow.promotion.pojo.vo.SeckillInfoVo;
import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

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

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SeckillCache seckillCache;
    @Autowired
    private SeckillSessionCache seckillSessionCache;

    /**
     * 生成 库存key
     *
     * @param seckillProductId 秒杀商品关联id
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:03
     */
    public String genSeckillStockKey(Long seckillProductId) {
        return RedisCst.genKey(RedisCst.SECKILL_PRODUCT_STOCK, seckillProductId.toString());
    }

    /**
     * 秒杀用户key
     *
     * @param seckillProductId 秒杀id
     * @param userCode         用户code
     * @return {@link String}
     * @author xiaoy
     * @since 2021/1/24 10:05
     */
    public String genSeckillLockKey(Long seckillProductId, String userCode) {
        return RedisCst.genKey(RedisCst.SECKILL_LOCK, seckillProductId.toString(), userCode);
    }

    /**
     * 查询库存数据
     *
     * @param seckillProductId 秒杀商品关联id
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
     *
     * @param seckillProductId 秒杀商品关联id
     * @param userCode         秒杀用户
     * @param seckillLimit     秒杀限制数量
     * @return {@link SeckillStatEnum}
     * @author liuyongtao
     * @since 2021-6-11 11:10
     */
    public SeckillStatEnum executeSeckill(Long seckillProductId, String userCode, Integer seckillLimit) {
        Assert.notNull(seckillProductId, "seckillProductId 不能为空");
        Assert.notNull(userCode, "userCode 不能为空");
        Assert.notNull(seckillLimit, "seckillLimit 不能为空");

        String seckillLockKey = this.genSeckillLockKey(seckillProductId, userCode);
        String seckillStockKey = this.genSeckillStockKey(seckillProductId);
        log.info("入参：[seckillLockKey]->{},[seckillStockKey]->{}", seckillLockKey, seckillStockKey);
        // 脚本配置，key 集合，秒杀商信息，付款过期时间（单位：秒）
        Long execute = LuaUtil.execute(LuaScriptEnum.SEC_KILL,
                Arrays.asList(seckillStockKey, seckillLockKey),
                seckillLimit);
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
     * @param seckillProductId 秒杀商品关联id
     * @return {@link SeckillInfoVo}
     * @author liuyongtao
     * @since 2021-6-11 11:17
     */
    public SeckillInfoVo findSeckillInfoCache(Long seckillProductId) {
        SeckillInfoVo vo = null;
        SeckillProductCacheDto seckillProductCache = redisUtils.getHash(RedisCst.SECKILL_PRODUCT_INFO, seckillProductId.toString());
        if (seckillProductCache != null) {
            vo = new SeckillInfoVo();
            // 秒杀商品信息
            vo.setSeckillProductCacheDto(seckillProductCache);
            // 活动场次信息
            SeckillCacheDto seckillCacheDto = seckillCache.getSeckillCache(seckillProductCache.getSeckillId());
            vo.setSeckillCacheDto(seckillCacheDto);
            // 活动时间信息
            SeckillSessionCacheDto seckillSessionCache = this.seckillSessionCache.getSeckillSessionCache(seckillProductCache.getSeckillSessionId());
            vo.setSeckillSessionCacheDto(seckillSessionCache);
        }
        return vo;
    }
}
