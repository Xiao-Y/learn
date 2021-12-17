package com.billow.promotion.service.impl;

import com.billow.notice.amqp.properties.NoticeMqYml;
import com.billow.notice.amqp.service.SendMQService;
import com.billow.promotion.cache.SeckillProductCache;
import com.billow.promotion.common.enums.OrderTypeEnum;
import com.billow.promotion.common.enums.SeckillStatEnum;
import com.billow.promotion.pojo.cache.SeckillCacheDto;
import com.billow.promotion.pojo.cache.SeckillProductCacheDto;
import com.billow.promotion.pojo.cache.SeckillSessionCacheDto;
import com.billow.promotion.pojo.vo.ExposerVo;
import com.billow.promotion.pojo.vo.OrderMqVo;
import com.billow.promotion.pojo.vo.SeckillExecutionVo;
import com.billow.promotion.pojo.vo.SeckillInfoVo;
import com.billow.promotion.service.SeckillService;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.utlis.FieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * <p>
 * 秒杀库存表 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
@Slf4j
@Service
@RefreshScope
public class SeckillServiceImpl implements SeckillService {

    // 设置盐值字符串，随便定义，用于混淆MD5值
    @Value("${seckill.gen-salt:wq<<.((0kkoe$$%}")
    private String salt;
    // url 失效时间（单位：秒）
    @Value("${seckill.url-invalid:20}")
    private long urlInvalid;

    @Autowired
    private SeckillProductCache seckillProductCache;
    @Autowired
    private SendMQService sendMQService;
    @Autowired
    private NoticeMqYml noticeMqYml;

    @Override
    public ExposerVo genSeckillUrl(Long seckillProductId) {
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 校验秒杀活动信息
        ExposerVo vo = this.checkSeckillProduct(seckillProductId, now);
        if (vo != null) {
            return vo;
        }
        // 判断库存
        int stock = seckillProductCache.findSeckillStockCache(seckillProductId);
        if (stock < 1) {
            throw new GlobalException(ResCodeEnum.RESCODE_ERROR_KILL_EMPTY);
        }
        // 秒杀链接过期时间
        Long expire = now.plusSeconds(urlInvalid).toEpochSecond(ZoneOffset.UTC);
        // 生成 md5 链接
        String md5 = this.getMD5(seckillProductId, null);
        return new ExposerVo(true, md5, seckillProductId, expire);
    }

    /**
     * 校验秒杀活动信息
     *
     * @param seckillProductId 秒杀id
     * @param now              当前时间
     * @return {@link ExposerVo}
     * @author liuyongtao
     * @since 2021-8-31 10:03
     */
    private ExposerVo checkSeckillProduct(Long seckillProductId, LocalDateTime now) {
        SeckillInfoVo seckillInfoVo = seckillProductCache.findSeckillInfoCache(seckillProductId);
        //说明没有查询到
        if (seckillInfoVo == null) {
            return new ExposerVo(false, seckillProductId);
        }
        SeckillCacheDto seckillCacheDto = seckillInfoVo.getSeckillCacheDto();
        // 当天没有秒杀活动
        if (seckillCacheDto.getEndDate().isBefore(now)) {
            return new ExposerVo(false, seckillProductId);
        }
        // 活动场次
        SeckillSessionCacheDto seckillSessionCacheDto = seckillInfoVo.getSeckillSessionCacheDto();
        // 如果不限制就立即开始
        if (seckillSessionCacheDto == null) {
            return null;
        }
        // 活动开始、结束时间
        LocalTime startTime = seckillSessionCacheDto.getStartTime();
        LocalTime endTime = seckillSessionCacheDto.getEndTime();
        // 活动已经结束
        if (endTime.isBefore(now.toLocalTime())) {
            return new ExposerVo(false, seckillProductId);
        }
        // 活动未开始
        if (startTime.isAfter(now.toLocalTime())) {
            return new ExposerVo(false, seckillProductId, now, startTime, endTime);
        }
        return null;
    }

    @Override
    public SeckillExecutionVo executionSeckill(String md5, Long seckillProductId, String userCode, Long expire) {
        // 校验 md5
        this.verifyMd5(md5, seckillProductId, expire);
        if (Objects.nonNull(expire)) {
            // 请求url 过期
            if (expire < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) {
                throw new GlobalException(ResCodeEnum.RESCODE_RULE_UNMATCH);
            }
        }
        // 查询库存
        int stock = seckillProductCache.findSeckillStockCache(seckillProductId);
        if (stock <= 0) {
            return new SeckillExecutionVo(seckillProductId, SeckillStatEnum.STOCK_OUT);
        }
        // 获取秒杀商品数据
        SeckillInfoVo seckillInfoVo = seckillProductCache.findSeckillInfoCache(seckillProductId);
        if (Objects.isNull(seckillInfoVo)) {
            return new SeckillExecutionVo(seckillProductId, SeckillStatEnum.END);
        }
        SeckillProductCacheDto seckillProductCacheDto = seckillInfoVo.getSeckillProductCacheDto();
        // 执行秒杀
        SeckillStatEnum statEnum = seckillProductCache.executeSeckill(seckillProductId, userCode, seckillProductCacheDto.getSeckillLimit());
        // 秒杀成功，订单系统保存订单数据
        if (SeckillStatEnum.SUCCESS.equals(statEnum)) {
            // 构建订单数据
            OrderMqVo killedVo = new OrderMqVo();
            killedVo.setProductId(seckillProductCacheDto.getProductId());
            killedVo.setPrice(seckillProductCacheDto.getSeckillPrice());
            killedVo.setSkuId(seckillProductCacheDto.getSkuId());
            killedVo.setOrderType(OrderTypeEnum.SEC_KILL.getType());
            killedVo.setUsercode(userCode);
            killedVo.setCount(1);
            FieldUtils.setCommonFieldByInsert(killedVo, userCode);
            String exchange = noticeMqYml.getMqCollect().get("secKillToCoreOrder").getExchange();
            sendMQService.send(exchange, killedVo);
        }
        return new SeckillExecutionVo(seckillProductId, statEnum);
    }

    /**
     * 生成MD5值
     *
     * @param seckillProductId 秒杀id
     * @return {@link String}
     * @author liuyongtao
     * @since 2021-1-22 9:32
     */
    private String getMD5(Long seckillProductId, Object... param) {
        Set<String> obj = new TreeSet<>();
        obj.add(seckillProductId.toString());
        obj.add(salt);
        if (Objects.nonNull(param)) {
            Set<String> collect = Arrays.stream(param)
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toSet());
            obj.addAll(collect);
        }
        String md5 = DigestUtils.md5DigestAsHex(String.join("/", obj).getBytes());
        log.info("obj:{},md5:{}", obj, md5);
        return md5;
    }

    /**
     * 验证MD5
     *
     * @param md5              签名
     * @param seckillProductId 秒杀id
     * @param param            参数列表
     * @author liuyongtao
     * @since 2021-8-21 14:23
     */
    private void verifyMd5(String md5, Long seckillProductId, Object... param) {
        String md51 = this.getMD5(seckillProductId, param);
        if (!StringUtils.equals(md51, md5)) {
            throw new GlobalException(ResCodeEnum.RESCODE_SIGNATURE_ERROR);
        }
    }
}

