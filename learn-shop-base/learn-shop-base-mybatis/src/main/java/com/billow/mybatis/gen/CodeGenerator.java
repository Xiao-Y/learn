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

    String projectPath = System.getProperty("user.dir") + "/learn-shop-base/learn-shop-base-mybatis";
    String template = "/template2";

    /**
     * 自定义配置
     *
     * @return com.baomidou.mybatisplus.generator.InjectionConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:46
     */
    private InjectionConfig getInjectionConfig(PackageConfig pc) {
        String srcJava = "/src/test/java/com/billow/";
        String srcRes = "/src/test/resources";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 如果模板引擎是 freemarker
        String templatePath = template + "/mapper.xml.ftl";
        // 如果模板引擎是 velocity
//        String templatePath = "/templates/mapper.xml.vm";
        // 自定义配置:mapper.xml
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcRes + "/mapper/base/" + tableInfo.getXmlName() + StringPool.DOT_XML;
            }
        });

        // 自定义配置:controller.java
        templatePath = template + "/controller.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/api/" + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:dao.java
        templatePath = template + "/mapper.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/dao/" + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxVo.java
        templatePath = template + "/vo.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/pojo/vo/" +
                        tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "Vo" + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxVo.java
        templatePath = template + "/build.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/pojo/build/" +
                        tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "BuildParam" + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxSearch.java
        templatePath = template + "/search.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/pojo/search/" +
                        tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "SearchParam" + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxService.java
        templatePath = template + "/IService.java.ftl";
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + srcJava + pc.getModuleName() + "/service/" +
                        tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });

        // 自定义配置:xxServiceImpl.java
        templatePath = template + "/ServiceImpl.java.ftl";
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
        dsc.setUrl("jdbc:mysql://192.168.137.200:36005/learn?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("learn_shop");
        dsc.setPassword("pass123");
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
        gc.setOutputDir(projectPath + "/src/test/java");
        gc.setAuthor("billow");
        gc.setOpen(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        gc.setFileOverride(true);
        gc.setEnableCache(true);

        gc.setIdType(IdType.ASSIGN_ID);

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
        // 生成表配置
        this.setGenTableRule(strategy);
        // 包配置
        PackageConfig pc = this.getPackageConfig(strategy);
        // 自定义配置
        InjectionConfig cfg = this.getInjectionConfig(pc);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(gc)
                .setDataSource(dsc)
                .setStrategy(strategy)
                .setPackageInfo(pc)
                .setCfg(cfg);
        // 设置模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null)
                .setController(null)
                .setService(null)
                .setServiceImpl(null)
                .setMapper(null);
        // 设置模板
        mpg.setTemplate(templateConfig)
                // 设置模板引擎
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .execute();
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
        // 是否为链式模型（默认 false）
        strategy.setChainModel(true);
        // 是否生成字段常量（默认 false）
//        strategy.setEntityColumnConstant(true);
        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id", "create_time", "creator_code", "update_time", "updater_code", "valid_ind");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        return strategy;
    }


    /**
     * 包配置
     *
     * @return com.baomidou.mybatisplus.generator.config.PackageConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:11
     */
    private PackageConfig getPackageConfig(StrategyConfig strategy) {
        PackageConfig pc = new PackageConfig();
        String parent = "";
        if (strategy.getTablePrefix().contains("pms_") || strategy.getTablePrefix().contains("sms_")) {
            parent = "product";
        } else if (strategy.getTablePrefix().contains("sk_")) {
            parent = "seckill";
        } else if (strategy.getTablePrefix().contains("sys_") || strategy.getTablePrefix().contains("v_")) {
            parent = "system";
        } else if (strategy.getTablePrefix().contains("oms_")) {
            parent = "order";
        }

        pc.setParent("com.billow");
        pc.setModuleName(parent);
        pc.setEntity("pojo.po");
        pc.setController("api");
        pc.setMapper("dao");
        return pc;
    }

    /**
     * 表生成规则配置
     *
     * @param strategy
     * @author liuyongtao
     * @since 2021-9-8 8:26
     */
    private void setGenTableRule(StrategyConfig strategy) {
        //        strategy.setInclude("oms_cart_item"
//                , "oms_company_address"
//                , "oms_order"
//                , "oms_order_item"
//                , "oms_order_operate_history"
//                , "oms_order_return_apply"
//                , "oms_order_return_reason"
//                , "oms_order_setting");
//        strategy.setTablePrefix("oms_");


//        strategy.setInclude("sms_seckill"
//                , "sms_seckill_log"
//                , "sms_seckill_product"
//                , "sms_seckill_session"
//        );
//        strategy.setTablePrefix("sms_");

//        strategy.setInclude("sk_seckill", "sk_success_killed");
//        strategy.setTablePrefix("sk_");

//        strategy.setInclude("pms_goods_brand"
//                , "pms_goods_category"
//                , "pms_goods_comment"
//                , "pms_goods_comment_replay"
//                , "pms_goods_operate_log"
//                , "pms_goods_safeguard"
//                , "pms_goods_sku"
//                , "pms_goods_sku_safeguard"
//                , "pms_goods_sku_spec_value"
//                , "pms_goods_spec_key"
//                , "pms_goods_spec_value"
//                , "pms_goods_spu"
//                , "pms_goods_spu_spec"
//                , "pms_goods_vertify_record"
//                , "pms_shop_info"
//        );
//        strategy.setTablePrefix("pms_");

//        strategy.setInclude("sys_apply_info",
//                "sys_city",
//                "sys_data_dictionary",
//                "sys_menu",
//                "sys_permission",
//                "sys_role",
//                "sys_white_list"
//                );
        strategy.setTablePrefix("sys_");
        strategy.setInclude("sys_permission",
                "r_role_menu",
                "r_role_permission",
                "r_user_role"
        );
//        strategy.setTablePrefix("r_");

//        strategy.setInclude("v_mytasklist");
//        strategy.setTablePrefix("v_");
    }

    public static void main(String[] args) {
        CodeGenerator cg = new CodeGenerator();
        cg.gen();
    }
}