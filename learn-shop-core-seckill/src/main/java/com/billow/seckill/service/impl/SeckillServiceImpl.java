package com.billow.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.seckill.dao.SeckillDao;
import com.billow.seckill.dao.SuccessKilledDao;
import com.billow.seckill.enums.SeckillStatEnum;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.seckill.pojo.vo.ExposerVo;
import com.billow.seckill.pojo.vo.SeckillExecutionVo;
import com.billow.seckill.pojo.vo.SeckillVo;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import com.billow.seckill.service.SeckillService;
import com.billow.seckill.service.SuccessKilledService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.UserTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Arrays;
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
public class SeckillServiceImpl extends ServiceImpl<SeckillDao, SeckillPo> implements SeckillService {

    //设置盐值字符串，随便定义，用于混淆MD5值
    private final String salt = "wq<<.((0kkoe$$%";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private DefaultRedisScript<Long> seckillScript;

    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private UserTools userTools;
    @Autowired
    private SuccessKilledService successKilledService;

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
        SeckillPo seckillPo = this.getById(seckillId);
        //说明没有查询到
        if (seckillPo == null) {
            return new ExposerVo(false, seckillId);
        }
        if (seckillPo.getStock() < 1) {
            throw new GlobalException(ResCodeEnum.RESCODE_ERROR_KILL_EMPTY);
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
        // 库存key
        String seckillStockKey = RedisCst.SECKILL_STOCK + seckillId;

        // 先查询库存
        String seckillStock = redisTemplate.opsForValue().get(seckillStockKey);
        if (seckillStock == null || Integer.parseInt(seckillStock) <= 0) {
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

        // 秒杀用户key
        String seckillLockKey = RedisCst.SECKILL_LOCK + seckillId + ":" + userCode;
        List<String> keys = Arrays.asList(seckillStockKey, seckillLockKey);
        // 脚本配置，key 集合，秒杀商信息，付款过期时间（单位：秒）
        Long execute = redisTemplate.execute(seckillScript, keys, JSON.toJSONString(killedPo), "600");
        SeckillStatEnum statEnum = SeckillStatEnum.of(execute.intValue());
        SuccessKilledVo successKilledVo = null;
        // 秒杀成功
        if (SeckillStatEnum.SUCCESS.equals(statEnum)) {
            // 构建返回数据
            successKilledVo = ConvertUtils.convert(killedPo, SuccessKilledVo.class);
        }
        SeckillExecutionVo executionVo = new SeckillExecutionVo(seckillId, statEnum, successKilledVo);
        log.info("秒杀信息：{}", JSON.toJSONString(executionVo));
        return executionVo;
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

