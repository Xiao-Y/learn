package com.billow.system.dao;

import com.billow.system.pojo.po.WhiteListPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhiteListDao extends JpaRepository<WhiteListPo, Long> {

    /**
     * 根据ip和模块查询出有效白名单
     *
     * @param ip       ip
     * @param module   模块
     * @param validind 是否有效
     * @return java.util.List<com.billow.pojo.po.sys.WhiteListPo>
     * @author LiuYongTao
     * @date 2018/5/19 14:27
     */
    List<WhiteListPo> findByIpAndModuleAndValidInd(String ip, String module, Boolean validind);
}
