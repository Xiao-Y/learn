package com.billow;

import com.billow.pojo.po.TestPo;
import com.billow.pojo.vo.CityVo;
import com.billow.pojo.vo.TestVo;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;

import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://119.23.27.78:3306/learn?useUnicode=true&characterEncoding=utf8&useSSL=true";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args) throws Exception {
//        this.getConnectionTest();

//        TestPo test = new TestPo();
//        test.setName("123123");
//        test.setCreateTime(new Date());
//        reflectTest(test);

        convert();
    }

    public static void convert() {
        TestVo test = new TestVo();
        test.setCreateTime(new Date());
        test.setId(1L);
        test.setName("123123");

        CityVo cityVo = new CityVo();
        cityVo.setName("RRRR");
        test.setCityVo(cityVo);

        List<CityVo> cityVos = new ArrayList<>();
        CityVo cityVo2 = new CityVo();
        cityVo2.setName("RRRR2222");
        cityVos.add(cityVo);

        test.setCityVos(cityVos);

//        TestVo convert = Convert.convert(TestVo.class, test);
        TestVo convert = ConvertUtils.convert(test,TestVo.class);
        System.out.println(convert);
    }

    public static void reflectTest(TestPo test) throws Exception {
        Class<? extends TestPo> clazz = test.getClass();
        Field[] fields = clazz.getDeclaredFields();
        if (ToolsUtils.isNotEmpty(fields)) {
            for (Field field : fields) {
                field.setAccessible(true);
                Class<?> type = field.getType();
                String fieldName = field.getName();
                Object fieldValue = field.get(test);
                System.out.println(type);
                System.out.println(fieldName);
                System.out.println(fieldValue);
            }
        }
    }


    private static void getConnectionTest() {
        java.sql.Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象..." + conn);

            // 完成后关闭

            conn.close();
        } catch (Exception se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}
