package com.billow.system.dao;

import com.billow.system.pojo.po.ApplyInfoPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ApplyInfoDao extends JpaRepository<ApplyInfoPo, Long>, JpaSpecificationExecutor<ApplyInfoPo> {
}
