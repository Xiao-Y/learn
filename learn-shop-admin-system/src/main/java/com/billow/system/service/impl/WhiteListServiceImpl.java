package com.billow.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.billow.system.dao.WhiteListDao;
import com.billow.system.pojo.po.WhiteListPo;
import com.billow.system.pojo.vo.WhiteListVo;
import com.billow.system.service.WhiteListService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyongtao
 * @create 2018-05-19 14:29
 */
@Service
public class WhiteListServiceImpl implements WhiteListService {

    @Autowired
    private WhiteListDao whiteListDao;

    @Override
    public List<WhiteListVo> findByIpAndModuleAndValidInd(String ip, String module, Boolean validind) {
        LambdaQueryWrapper<WhiteListPo> condition = Wrappers.lambdaQuery();
        condition.eq(WhiteListPo::getIp, ip)
                .eq(WhiteListPo::getModule, module)
                .eq(WhiteListPo::getValidInd, validind);
        List<WhiteListPo> whiteListPos = whiteListDao.selectList(condition);
        List<WhiteListVo> whiteListVos = ConvertUtils.convert(whiteListPos, WhiteListVo.class);
        return whiteListVos;
    }
}
