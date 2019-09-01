package com.billow.system.dao;

import com.billow.system.pojo.po.CityPo;
import com.billow.system.pojo.po.LeavePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 请假申请
 *
 * @author LiuYongTao
 * @date 2019/7/11 10:56
 */
public interface LeaveDao extends JpaRepository<LeavePo, Long>, JpaSpecificationExecutor<LeavePo> {

}
