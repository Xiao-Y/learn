package com.ft;

import com.ft.model.CityModel;
import com.ft.model.TestModel;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
@ServletComponentScan
public class AdminUserApp {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        SpringApplication.run(AdminUserApp.class, args);
//        List<CityModel> cityModels = new ArrayList<>();
//        CityModel cm = new CityModel();
//        cm.setName("21312");
//        TestModel tm = new TestModel();
//        tm.setName("4545");
//        List<TestModel> testModels = new ArrayList<>();
//        testModels.add(tm);
//        cm.setTestModels(testModels);
//        cityModels.add(cm);
//
//        List<CityModel> temp = new ArrayList<>();
//        //BeanUtils.copyProperties(temp, cityModels);
//        //AbstractList
//        String[] str = new String[3];
//        Map<String, String> map = new HashMap<>();
////        System.out.println(cm.getClass().getSuperclass());//TestModel
//        System.out.println(tm.getClass().getSuperclass());//Object
//        System.out.println(str.getClass().getSuperclass());//Object
//        System.out.println(map.getClass().getSuperclass());//AbstractMap
//        System.out.println(temp.getClass().getSuperclass());//AbstractList
//        //List<CityModel> bean = (List<CityModel>) ConvertUtils.convert(cityModels, List.class);
//        List<CityModel> bean = com.ft.utlis.BeanUtils.convert(cityModels, CityModel.class);
//        cityModels.get(0).setName("6666");
//        cityModels.get(0).getTestModels().get(0).setName("88888");
//        System.out.println(bean.get(0));
    }
}
