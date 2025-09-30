package com.billow.mybatis.gen;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.billow.mybatis.base.HighLevelApi;
import com.billow.mybatis.base.HighLevelService;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.mybatis.pojo.BasePo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 *
 * @author LiuYongTao
 * @date 2019/10/29 9:30
 */
public class CodeGenerator {

    String projectPath = System.getProperty("user.dir") + "/learn-shop-base/learn-shop-base-mybatis";
    String template = "/template";
    String srcJava = "/src/test/java";
    String srcRes = "/src/test/resources";

    /**
     * 自定义配置
     *
     * @return com.baomidou.mybatisplus.generator.InjectionConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:46
     */
    private InjectionConfig getInjectionConfig(PackageConfig pc) {
        String parent = pc.getParent();
        String replace = "";
        if (StringUtils.isNoneBlank(parent)) {
            replace = parent.replace(".", "/");
        }
        String javaPath = srcJava + "/" + replace + "/";

        // 自定义输出配置
        List<CustomFile> focList = new ArrayList<>();

        // 如果模板引擎是 freemarker
        // 自定义配置:mapper.xml
        focList.add(new CustomFile.Builder()
                .filePath(projectPath + srcRes + "/mapper/base/")
                .formatNameFunction(TableInfo::getXmlName)
                .fileName(StringPool.DOT_XML)
                .templatePath(template + "/mapper.xml.ftl")
                .enableFileOverride()
                .build());

//        // 自定义配置:xxVo.java
//        focList.add(new CustomFile.Builder()
//                .filePath(projectPath + javaPath + "/pojo/vo/")
//                .formatNameFunction(tableInfo -> tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "Vo")
//                .fileName(StringPool.DOT_JAVA)
//                .templatePath(template + "/vo.java.ftl")
//                .enableFileOverride()
//                .build());
//
//        // 自定义配置:xxBuild.java
//        focList.add(new CustomFile.Builder()
//                .filePath(projectPath + javaPath + "/pojo/build/")
//                .formatNameFunction(tableInfo -> tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "BuildParam")
//                .fileName(StringPool.DOT_JAVA)
//                .templatePath(template + "/build.java.ftl")
//                .enableFileOverride()
//                .build());

        // 自定义配置:xxSearch.java
        focList.add(new CustomFile.Builder()
                .filePath(projectPath + javaPath + "/pojo/search/")
                .formatNameFunction(tableInfo -> tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "SearchParam")
                .fileName(StringPool.DOT_JAVA)
                .templatePath(template + "/search.java.ftl")
                .enableFileOverride()
                .build());
        // 自定义配置
        return new InjectionConfig.Builder()
                .customFile(focList)
                .build();
    }

    /**
     * 数据源配置
     *
     * @return com.baomidou.mybatisplus.generator.config.DataSourceConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:10
     */
    private DataSourceConfig getDataSourceConfig() {
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/learn?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "root";
        return new DataSourceConfig.Builder(url, username, password)
                .driverClassName(driverName)
                .build();
    }

    /**
     * 全局配置
     *
     * @return com.baomidou.mybatisplus.generator.config.GlobalConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:07
     */
    private GlobalConfig getGlobalConfig() {
        return new GlobalConfig.Builder()
                .outputDir(projectPath + srcJava)
                .author("billow")
                .disableOpenDir()
                .enableSwagger()
                .build();

    }

    public void gen() {
        // 全局配置
        GlobalConfig gc = this.getGlobalConfig();
        // 数据源配置
        DataSourceConfig dsc = this.getDataSourceConfig();
        // 策略配置
        StrategyConfig.Builder builder = this.getStrategyConfig();
        // 生成表配置
        this.setGenTableRule(builder);
        StrategyConfig strategy = builder.build();
        // 包配置
        PackageConfig pc = this.getPackageConfig(strategy);
        // 自定义配置
        InjectionConfig cfg = this.getInjectionConfig(pc);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);
        mpg.global(gc)
                .strategy(strategy)
                .packageInfo(pc)
                .injection(cfg)
                // 设置模板引擎
                .execute(new FreemarkerTemplateEngine());
    }

    /**
     * 策略配置
     *
     * @return com.baomidou.mybatisplus.generator.config.StrategyConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:20
     */
    private StrategyConfig.Builder getStrategyConfig() {
        StrategyConfig.Builder builder = new StrategyConfig.Builder();
        builder.entityBuilder()
                .formatFileName("%sPo")
                .javaTemplate(template + "/entity.java")
                .superClass(BasePo.class)
                .enableSerialAnnotation()
                .enableChainModel()
                .enableLombok()
                .enableTableFieldAnnotation()
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addSuperEntityColumns("id", "create_time", "creator_code", "update_time", "updater_code", "valid_ind")
//                .idType(IdType.AUTO)
                .enableFileOverride()
                .fieldUseJavaDoc(true)

                .controllerBuilder()
                .formatFileName("%sApi")
                .template(template + "/controller.java")
                .superClass(HighLevelApi.class)
                .enableHyphenStyle()
                .enableRestStyle()
                .enableFileOverride()

                .serviceBuilder()
                .formatServiceFileName("%sService")
                .serviceTemplate(template + "/IService.java")
                .superServiceClass(HighLevelService.class)

                .formatServiceImplFileName("%sServiceImpl")
                .serviceImplTemplate(template + "/ServiceImpl.java")
                .superServiceImplClass(HighLevelServiceImpl.class)
                .enableFileOverride()

                .mapperBuilder()
                .formatMapperFileName("%sDao")
                .mapperTemplate(template + "/mapper.java")
                .enableBaseResultMap()
                .enableBaseColumnList()
                .cache(MybatisRedisCache.class)
                .enableFileOverride()
                .disableMapperXml()
        ;
        return builder;
    }


    /**
     * 包配置
     *
     * @return com.baomidou.mybatisplus.generator.config.PackageConfig
     * @author LiuYongTao
     * @date 2019/10/29 9:11
     */
    private PackageConfig getPackageConfig(StrategyConfig strategy) {
        String moduleName = "";
        if (strategy.getTablePrefix().contains("pms_") || strategy.getTablePrefix().contains("sms_")) {
            moduleName = "product";
        } else if (strategy.getTablePrefix().contains("sk_")) {
            moduleName = "seckill";
        } else if (strategy.getTablePrefix().contains("sys_") || strategy.getTablePrefix().contains("v_")) {
            moduleName = "system";
        } else if (strategy.getTablePrefix().contains("oms_")) {
            moduleName = "order";
        } else if (strategy.getTablePrefix().contains("u_")) {
            moduleName = "user";
        }

        String javaPath = srcJava + "/" + moduleName + "/";
        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.parent, projectPath + "/learn-shop-base/learn-shop-base-mybatis" + javaPath);

        return new PackageConfig.Builder("com.billow", moduleName)
                .entity("pojo.po")
                .mapper("dao")
                .service("service")
                .serviceImpl("service.impl")
                .xml("mapper")
                .controller("api")
                .pathInfo(pathInfo)
                .build();
    }

    /**
     * 表生成规则配置
     *
     * @param strategy
     * @author liuyongtao
     * @since 2021-9-8 8:26
     */
    private void setGenTableRule(StrategyConfig.Builder strategy) {
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

        strategy.addTablePrefix("sys_")
                .addInclude("sys_apply_info",
//                "sys_city",
//                "sys_data_dictionary",
                        "sys_menu",
//                "sys_permission",
//                "sys_role",
                        "sys_user_role",
                        "sys_role_menu",
                        "sys_role_permission",
                        "sys_permission",
                        "sys_menu_permission",
                        "sys_white_list");


//        strategy.setInclude("v_mytasklist");
//        strategy.setTablePrefix("v_");

//        strategy.setTablePrefix("u_");
//        strategy.setInclude("u_user"
//        );
    }

    public static void main(String[] args) {
        CodeGenerator cg = new CodeGenerator();
        cg.gen();
    }
}