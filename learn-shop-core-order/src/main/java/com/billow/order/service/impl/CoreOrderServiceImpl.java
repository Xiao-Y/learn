package com.billow.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.billow.order.dao.OrderDao;
import com.billow.order.pojo.po.OrderPo;
import com.billow.order.pojo.vo.OrderVo;
import com.billow.order.remote.TestUserRemote;
import com.billow.order.service.CoreOrderService;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;
import com.billow.tools.utlis.ConvertUtils;
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
    private OrderDao orderDao;
    @Autowired
    private TestUserRemote testUserRemote;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sendOrderCar() {
        //执行业务操作....
        this.save(new OrderVo());

        //添加远程要执行的事务


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
        OrderPo orderPo = ConvertUtils.convert(orderVo, OrderPo.class);
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
