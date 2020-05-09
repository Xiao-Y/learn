package com.billow.mybatis.gen;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 *
 * @author LiuYongTao
 * @date 2019/10/29 9:30
 */
public class CodeGenerator {

    String projectPath = System.getProperty("user.dir") + "/learn-shop-base-mybatis";

    /**
     * 自定义配置
     *
     * @return com.baomidou.mybatisplus.generator.InjectionConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:46
     */
    private InjectionConfig getInjectionConfig(PackageConfig pc) {
        String srcJava = "/src/main/java/com/billow/";
        String srcRes = "/src/main/resources";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 如果模板引擎是 freemarker
        String templatePath = "/template/mapper.xml.ftl";
        // 如果模板引擎是 velocity
//        String templatePath = "/templates/mapper.xml.vm";
        // 自定义配置:mapper.xml
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcRes + "/mapper/" + tableInfo.getXmlName() + StringPool.DOT_XML;
            }
        });

        // 自定义配置:controller.java
        templatePath = "/template/controller.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/api/" + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:dao.java
        templatePath = "/template/mapper.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/dao/" + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxVo.java
        templatePath = "/template/vo.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/pojo/vo/" +
                        tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "Vo" + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxService.java
        templatePath = "/template/IService.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/service/" +
                        tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxServiceImpl.java
        templatePath = "/template/ServiceImpl.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/service/impl/" +
                        tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 数据源配置
     *
     * @return com.baomidou.mybatisplus.generator.config.DataSourceConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:10
     */
    private DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/learn?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        return dsc;
    }

    /**
     * 全局配置
     *
     * @return com.baomidou.mybatisplus.generator.config.GlobalConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:07
     */
    private GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("billow");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setFileOverride(true);
        gc.setEnableCache(true);
        gc.setIdType(IdType.ID_WORKER);

        gc.setServiceName("%sService");
        gc.setEntityName("%sPo");
        gc.setControllerName("%sApi");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        return gc;
    }

    public void gen() {
        // 全局配置
        GlobalConfig gc = this.getGlobalConfig();
        // 数据源配置
        DataSourceConfig dsc = this.getDataSourceConfig();
        // 策略配置
        StrategyConfig strategy = this.getStrategyConfig();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc);
        mpg.setDataSource(dsc);
        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = this.getPackageConfig(mpg);
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = this.getInjectionConfig(pc);
        mpg.setCfg(cfg);
        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setController(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper(null);
        mpg.setTemplate(templateConfig);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        mpg.execute();
    }

    /**
     * 策略配置
     *
     * @return com.baomidou.mybatisplus.generator.config.StrategyConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:20
     */
    private StrategyConfig getStrategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 公共父类
        strategy.setSuperEntityClass("com.billow.mybatis.pojo.BasePo");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id", "create_time", "creator_code", "update_time", "updater_code", "valid_ind");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setInclude("p_goods_brand", "p_goods_category", "p_goods_sku"
                , "p_goods_sku_spec_value", "p_goods_spec_key", "p_goods_spec_value", "p_goods_spu", "p_goods_spu_spec", "p_shop_info");
        strategy.setTablePrefix("p_");
        return strategy;
    }


    /**
     * 包配置
     *
     * @return com.baomidou.mybatisplus.generator.config.PackageConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:11
     */
    private PackageConfig getPackageConfig(AutoGenerator mpg) {
        PackageConfig pc = new PackageConfig();
        StrategyConfig strategy = mpg.getStrategy();

        String parent = "";
        if (strategy.getTablePrefix()[0].equals("p_")) {
            parent = "product";
        }
        pc.setParent("com.billow");
        pc.setModuleName(parent);
        pc.setEntity("pojo.po");
        pc.setController("api");
        pc.setMapper("dao");
        return pc;
    }


    public static void main(String[] args) {
        CodeGenerator cg = new CodeGenerator();
        cg.gen();
    }
}