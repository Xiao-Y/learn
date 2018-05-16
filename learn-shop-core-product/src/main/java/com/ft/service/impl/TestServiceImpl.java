package com.ft.service.impl;

import com.ft.dao.TestDao;
import com.ft.po.TestPo;
import com.ft.service.TestService;
import com.ft.service.UserService;
import com.ft.utlis.FieldUtils;
import com.ft.utlis.PageUtil;
import com.ft.vo.TestVo;
import com.ft.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 测试
 *
 * @author liuyongtao
 * @create 2018-05-16 9:59
 */
@Service
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {

    @Autowired
    private TestDao testDao;
    @Autowired
    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TestVo saveTest(TestVo testVo) throws Exception {
        userService.saveUser(new UserVo());

        testVo.setAge(22);
        testVo.setName("billow");
        FieldUtils.setCommonFieldByInsertWithValidInd(testVo, "admin");
        TestPo testPo = PageUtil.convert(testVo, TestPo.class);
        testDao.save(testPo);
        return PageUtil.convert(testPo, TestVo.class);
    }

    @Override
    public TestVo findTestById(Long id) throws Exception {
        TestPo testPo = testDao.findOne(id);
        testPo.setName("6666");
        TestVo convert = PageUtil.convert(testPo, TestVo.class);
        return convert;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public TestVo updateTestById(Long id) throws Exception {
        TestPo testPo = testDao.findOne(id);
        Optional.of(testPo);
        testPo.setName("9999");

        //会先查询，再执行update,没有set的都会更新为null
//        TestPo testPo = new TestPo();
//        testPo.setName("99229").setId(id);
        FieldUtils.setCommonFieldByUpdate(testPo, "adminUpdate");
        testDao.save(testPo);
        return PageUtil.convert(testPo,TestVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteTestById(Long id) throws Exception {
        testDao.delete(id);
    }
}
