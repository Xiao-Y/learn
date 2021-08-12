package com.billow.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.cache.SeckillCache;
import com.billow.seckill.cache.StockRedisKit;
import com.billow.seckill.dao.SeckillDao;
import com.billow.seckill.enums.SeckillStatEnum;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.seckill.pojo.vo.ExposerVo;
import com.billow.seckill.pojo.vo.SeckillExecutionVo;
import com.billow.seckill.pojo.vo.SeckillVo;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import com.billow.seckill.service.SeckillService;
import com.billow.seckill.service.SuccessKilledService;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.FieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

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
public class SeckillServiceImpl extends ServiceImpl<SeckillDao, SeckillPo> implements SeckillService {

    // 设置盐值字符串，随便定义，用于混淆MD5值
    @Value("${seckill.gen-salt:wq<<.((0kkoe$$%}")
    private String salt;
    // 自动任务加载秒杀开始前多少分钟的数据加到缓存中（单位：分钟）
    @Value("${seckill.load-data-start-before:10}")
    private long loadDataStartBefore;
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledService successKilledService;
    @Autowired
    private SeckillCache seckillCache;
//    @Autowired
//    private StockRedisKit stockRedisKit;

    @Override
    public IPage<SeckillPo> findListByPage(SeckillVo seckillVo) {
        IPage<SeckillPo> page = new Page<>(seckillVo.getPageNo(), seckillVo.getPageSize());
        LambdaQueryWrapper<SeckillPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<SeckillPo> selectPage = seckillDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(Long id) {
        SeckillPo po = new SeckillPo();
        po.setValidInd(false);
        LambdaQueryWrapper<SeckillPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SeckillPo::getId, id);
        return seckillDao.update(po, wrapper) >= 1;
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
        // 生成 md5 链接
        String md5 = this.getMD5(seckillId);
        return new ExposerVo(true, md5, seckillId);
    }

    @Override
    @Transactional
    public SeckillExecutionVo executionSeckill(Long seckillId, String md5, String userCode) {
        // 校验 md5
        String md51 = this.getMD5(seckillId);
        if (!md51.equals(md5)) {
            throw new GlobalException(ResCodeEnum.RESCODE_SIGNATURE_ERROR);
        }
        int stock = seckillCache.findSeckillStockCache(seckillId);
        if (stock <= 0) {
            SeckillExecutionVo executionVo = new SeckillExecutionVo(seckillId, SeckillStatEnum.STOCK_OUT, null);
            log.info("===>> 秒杀信息：{}", JSON.toJSONString(executionVo));
            return executionVo;
        }
        // 构建订单数据
        SuccessKilledPo killedPo = new SuccessKilledPo();
        killedPo.setSeckillId(seckillId);
        killedPo.setUsercode(userCode);
        killedPo.setKillState(SeckillStatEnum.SUCCESS.getState());
        FieldUtils.setCommonFieldByInsert(killedPo, userCode);
        // 执行秒杀
        SeckillStatEnum statEnum = seckillCache.executeSeckill(killedPo);
        SuccessKilledVo successKilledVo = null;
        // 秒杀成功
        if (SeckillStatEnum.SUCCESS.equals(statEnum)) {
            // 构建返回数据
            successKilledVo = ConvertUtils.convert(killedPo, SuccessKilledVo.class);
            // 保存秒杀订单数据
            SuccessKilledPo successKilledPoCache = seckillCache.findSuccessKilledCache(seckillId, userCode);
//            successKilledService.saveAsync(successKilledPoCache);
        }
        SeckillExecutionVo executionVo = new SeckillExecutionVo(seckillId, statEnum, successKilledVo);
        log.info("秒杀信息：{}", JSON.toJSONString(executionVo));
        return executionVo;
    }

    @Override
    public void loadSeckillJob() {
        long loadDataStartBeforeTime = loadDataStartBefore * 60 * 1000;
        // 当前时间
        Date now = new Date();
        // 延迟加载时间
        Date loadDataDate = new Date(now.getTime() + loadDataStartBeforeTime);
        LambdaQueryWrapper<SeckillPo> wrapper = Wrappers.lambdaQuery();
        wrapper.ge(SeckillPo::getStartTime, loadDataDate)
                .eq(SeckillPo::getLoadCache, false);
        List<SeckillPo> seckillPos = this.list(wrapper);
        seckillPos.stream().forEach(f -> {
            this.updatePojoAndCache(now, f);
        });
    }

    /**
     * 更新秒杀商品信息，更新缓存
     *
     * @param nowDate   当前时间
     * @param seckillPo 更新对象，（完整对象）
     * @author xiaoy
     * @since 2021/1/24 10:39
     */
    private void updatePojoAndCache(Date nowDate, SeckillPo seckillPo) {
        try {
            seckillPo.setLoadCache(true);
            this.updateById(seckillPo);
            // 更新缓存
            seckillCache.saveSeckillStockCache(seckillPo.getId(), seckillPo.getEndTime(), nowDate, seckillPo.getStock());
            seckillCache.saveSeckillCache(seckillPo);
        } catch (Exception e) {
            log.error("自动任务：seckillStockKey:{} 加载缓存数据失败");
        }
    }

    /**
     * 生成MD5值
     *
     * @param seckillId
     * @return {@link String}
     * @author liuyongtao
     * @since 2021-1-22 9:32
     */
    private String getMD5(Long seckillId) {
        String base = seckillId + "/" + salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}

