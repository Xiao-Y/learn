package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.common.amqp.config.MqSecKillOrderConfig;
import com.billow.common.amqp.expand.SendMessage;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.seckill.common.cache.SeckillCache;
import com.billow.seckill.common.enums.SeckillStatEnum;
import com.billow.seckill.dao.SeckillDao;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.search.SeckillSearchParam;
import com.billow.seckill.pojo.vo.ExposerVo;
import com.billow.seckill.pojo.vo.SeckillExecutionVo;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import com.billow.seckill.service.SeckillService;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.utlis.FieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
public class SeckillServiceImpl extends HighLevelServiceImpl<SeckillDao, SeckillPo, SeckillSearchParam> implements SeckillService {

    // 设置盐值字符串，随便定义，用于混淆MD5值
    @Value("${seckill.gen-salt:wq<<.((0kkoe$$%}")
    private String salt;
    // 自动任务加载秒杀开始前多少分钟的数据加到缓存中（单位：分钟）
    @Value("${seckill.load-data-start-before:10}")
    private long loadDataStartBefore;
    // 提前 loadDataStartBefore 加载秒杀商品数据
    private long loadDataStartBeforeTime = loadDataStartBefore * 60 * 1000;
    // url 失效时间（单位：秒）
    @Value("${seckill.url-invalid:20}")
    private long urlInvalid;
    // 订单过期时间（单位：分钟）
    @Value("${seckill.order-exp:30}")
    private int seckillOrderExp;

    @Autowired
    private SeckillCache seckillCache;
    @Autowired
    private MqSecKillOrderConfig mqSecKillOrderConfig;

    @Override
    public void genQueryCondition(LambdaQueryWrapper<SeckillPo> wrapper, SeckillSearchParam seckillSearchParam) {
        super.genQueryCondition(wrapper, seckillSearchParam);
    }

    @Override
    public ExposerVo genSeckillUrl(Long seckillId) {
        SeckillPo seckillPo = seckillCache.findSeckillCache(seckillId);
        //说明没有查询到
        if (seckillPo == null) {
            return new ExposerVo(false, seckillId);
        }
        // 当前时间
        long nowLong = System.currentTimeMillis();
        // 活动开始时间
        long startLong = seckillPo.getStartTime().getTime();
        // 活动结束时间
        long endLong = seckillPo.getEndTime().getTime();
        // 判断活动是否开始
        if (startLong > nowLong || nowLong > endLong) {
            return new ExposerVo(false, seckillId, nowLong, startLong, endLong);
        }
        // 判断库存
        int stock = seckillCache.findSeckillStockCache(seckillId);
        if (stock < 1) {
            throw new GlobalException(ResCodeEnum.RESCODE_ERROR_KILL_EMPTY);
        }
        Long expire = LocalDateTime.now().plusSeconds(urlInvalid).toEpochSecond(ZoneOffset.UTC);
        // 生成 md5 链接
        String md5 = this.getMD5(seckillId, expire);
        return new ExposerVo(true, md5, seckillId, expire);
    }

    @Override
    public SeckillExecutionVo executionSeckill(String md5, Long seckillId, String userCode, Long expire) {
        // 校验 md5
        this.verifyMd5(md5, seckillId, expire);
        if (Objects.nonNull(expire)) {
            // 请求url 过期
            if (expire < LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) {
                throw new GlobalException(ResCodeEnum.RESCODE_RULE_UNMATCH);
            }
        }
        // 查询库存
        int stock = seckillCache.findSeckillStockCache(seckillId);
        if (stock <= 0) {
            return new SeckillExecutionVo(seckillId, SeckillStatEnum.STOCK_OUT);
        }
        // 获取秒杀商品数据
        SeckillPo seckillPo = seckillCache.findSeckillCache(seckillId);
        if (Objects.isNull(seckillPo)) {
            return new SeckillExecutionVo(seckillId, SeckillStatEnum.END);
        }
        // 构建订单数据
        SuccessKilledVo killedVo = new SuccessKilledVo();
        killedVo.setSeckillId(seckillId);
        killedVo.setUsercode(userCode);
        killedVo.setKillState(SeckillStatEnum.SUCCESS.getState());
        Integer paymentExp = seckillPo.getPaymentExp();
        seckillOrderExp = paymentExp == null ? seckillOrderExp : paymentExp;
        killedVo.setExpire(DateUtils.addMinutes(new Date(), seckillOrderExp));
        FieldUtils.setCommonFieldByInsert(killedVo, userCode);
        SendMessage.send(mqSecKillOrderConfig.secKillOrderExchange().getName(), killedVo);
        // 执行秒杀
        SeckillStatEnum statEnum = seckillCache.executeSeckill(killedVo);
        // 秒杀成功，订单系统保存订单数据
        if (SeckillStatEnum.SUCCESS.equals(statEnum)) {
            SendMessage.send(mqSecKillOrderConfig.secKillOrderExchange().getName(), killedVo);
        }
        return new SeckillExecutionVo(seckillId, statEnum);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void loadSeckillJob() {
        // 当前时间
        Date now = new Date();
        // 延迟加载时间
        Date loadDataDate = new Date(now.getTime() + loadDataStartBeforeTime);
        LambdaQueryWrapper<SeckillPo> wrapper = Wrappers.lambdaQuery();
        wrapper.le(SeckillPo::getStartTime, loadDataDate)
                .ge(SeckillPo::getEndTime, now)
                .eq(SeckillPo::getLoadCache, false);
        List<SeckillPo> seckillPos = this.list(wrapper);
        seckillPos.forEach(f -> {
            try {
                this.updatePojoAndCache(now, f);
            } catch (Exception e) {
                log.error("自动任务：加载缓存数据失败.{}", e.getMessage(), e);
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePojoAndCache(Date nowDate, SeckillPo seckillPo) {
        seckillPo.setLoadCache(true);
        this.updateById(seckillPo);
        // 更新缓存
        String seckillStockKey = seckillCache.saveSeckillStockCache(seckillPo.getId(), seckillPo.getEndTime(), nowDate, seckillPo.getStock());
        log.info("seckillStockKey:{}", seckillStockKey);
        String seckillProductKey = seckillCache.saveSeckillCache(seckillPo, nowDate);
        log.info("seckillProductKey:{}", seckillProductKey);
    }

    /**
     * 生成MD5值
     *
     * @param seckillId
     * @return {@link String}
     * @author liuyongtao
     * @since 2021-1-22 9:32
     */
    private String getMD5(Long seckillId, Object... param) {
        Set<String> obj = new TreeSet<>();
        obj.add(seckillId.toString());
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
     * @param md5
     * @param seckillId
     * @param param
     * @author liuyongtao
     * @since 2021-8-21 14:23
     */
    private void verifyMd5(String md5, Long seckillId, Object... param) {
        String md51 = this.getMD5(seckillId, param);
        if (!StringUtils.equals(md51, md5)) {
            throw new GlobalException(ResCodeEnum.RESCODE_SIGNATURE_ERROR);
        }
    }
}

