package com.billow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.billow.common.ResData.BaseResponse;
import com.billow.dao.OrderDao;
import com.billow.common.enums.ResCodeEnum;
import com.billow.common.enums.SysEventEunm;
import com.billow.common.enums.SysEventTypeEunm;
import com.billow.tools.generator.UUID;
import com.billow.pojo.po.OrderPo;
import com.billow.remote.TestUserRemote;
import com.billow.service.CoreOrderService;
import com.billow.common.sysEvent.model.expand.SysEventPublishDto;
import com.billow.common.sysEvent.service.SysEventPublishService;
import com.billow.tools.utlis.PageUtil;
import com.billow.pojo.vo.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author liuyongtao
 * @create 2018-03-01 14:56
 */
@Service
public class CoreOrderServiceImpl implements CoreOrderService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysEventPublishService sysEventPublishService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private TestUserRemote testUserRemote;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendOrderCar() {
        //执行业务操作....
        this.save(new OrderVo());

        //添加远程要执行的事务
        SysEventPublishDto sysEventPublishDto = new SysEventPublishDto();
        sysEventPublishDto.setClassName("123123");
        sysEventPublishDto.setCreateDate(new Date());
        sysEventPublishDto.setUpdateDate(new Date());
        sysEventPublishDto.setEventType(SysEventTypeEunm.event_type_orderToUser_test.getStatusCode());
        sysEventPublishDto.setId(UUID.generate());
        sysEventPublishDto.setStatus(SysEventEunm.status_publish.getStatusCode());
        sysEventPublishService.save(sysEventPublishDto);

        logger.debug("业务执行完毕...");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void save(OrderVo orderVo) {
        orderVo.setProductName("袜子");
        orderVo.setProductNo("123");
        orderVo.setCreatorCode("billow");
        orderVo.setCreateTime(new Date());
        orderVo.setUpdaterCode("billow");
        orderVo.setUpdateTime(new Date());
        OrderPo orderPo = PageUtil.convert(orderVo, OrderPo.class);
        orderDao.save(orderPo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse<OrderVo> saveUserAndOrder() {
        //弊端：只能先本地事务再远程事务
        this.save(new OrderVo());
        BaseResponse<OrderVo> res = new BaseResponse<>(ResCodeEnum.OK);
        String jsonRes = testUserRemote.saveUser("testOrder");
        JSONObject jsonObject = JSONObject.parseObject(jsonRes);
        String resCode = jsonObject.get("resCode").toString();
        if (!ResCodeEnum.OK.equals(resCode)) {
            res.setResCode(resCode);
            throw new RuntimeException(jsonObject.get("resMsg").toString());
        }
        return res;
    }

    @Override
    @TxTransaction(isStart = true)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse<OrderVo> saveUserAndOrderTx() {
        //远程事务已经提交后，本地异常，远程事务会回滚
        BaseResponse<OrderVo> res = new BaseResponse<>(ResCodeEnum.OK);

        String jsonRes = testUserRemote.saveUser("testOrder");
        this.save(new OrderVo());

        JSONObject jsonObject = JSONObject.parseObject(jsonRes);
        String resCode = jsonObject.get("resCode").toString();

        //测试分布式事务，如果远程成功，手动抛出异常
        if (ResCodeEnum.OK.equals(resCode)) {
//            res.setResCode(ResCodeEnum.FAIL);
            //throw new RuntimeException(ResCodeEnum.FAIL_NAME);
        }
        return res;
    }
}
