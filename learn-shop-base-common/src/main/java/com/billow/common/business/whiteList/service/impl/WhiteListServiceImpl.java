package com.billow.common.business.whiteList.service.impl;

import com.billow.common.business.whiteList.dao.WhiteListDao;
import com.billow.common.business.whiteList.pojo.po.WhiteListPo;
import com.billow.common.business.whiteList.pojo.vo.WhiteListVo;
import com.billow.common.business.whiteList.service.WhiteListService;

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
        List<WhiteListPo> whiteListPos = whiteListDao.findByIpAndModuleAndValidInd(ip, module, validind);
        List<WhiteListVo> whiteListVos = ConvertUtils.convert(whiteListPos, WhiteListVo.class);
        return whiteListVos;
    }
}
