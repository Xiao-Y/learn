package com.billow.system.autoTask;

import com.billow.tools.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试自动任务
 *
 * @author liuyongtao
 * @date 2017年5月12日 下午7:27:53
 */
public class TestAutoTask {
    private static final Logger logger = LoggerFactory.getLogger(TestAutoTask.class);

    public void test() {
        try {
            System.out.println("=====================" + TestAutoTask.class.getName() + "=====" + DateUtils.getSimpleDateFormat() + "======================");
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("异常：", e);
        }
//            System.out.println(1/0);
        //logger.info("=====================" + TestAutoTask.class.getName() + DateTime.getSimpleDateFormat() + "======================");
    }
}
