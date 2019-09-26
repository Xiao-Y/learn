package com.billow.test.autoTask;

import com.billow.tools.date.DateUtils;
import org.apache.log4j.Logger;

/**
 * 测试自动任务
 *
 * @author liuyongtao
 * @date 2017年5月12日 下午7:27:53
 */
public class TestAutoTask {
    private static final Logger logger = Logger.getLogger(TestAutoTask.class);

    public void test() {
        try {
            System.out.println("=====================" + TestAutoTask.class.getName() + "=====" + DateUtils.getSimpleDateFormat() + "======================");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
//            System.out.println(1/0);
        //logger.info("=====================" + TestAutoTask.class.getName() + DateTime.getSimpleDateFormat() + "======================");
    }
}
