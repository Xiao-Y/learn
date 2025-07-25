package com.billow.excel.provider.impl;

import com.billow.excel.annotation.ExcelDict;
import com.billow.excel.config.ExcelProperties;
import com.billow.excel.provider.DictProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 枚举字典提供者
 */
@Slf4j
@Order(1)
public class EnumDictProvider implements DictProvider, InitializingBean {

    /**
     * 字典枚举接口
     */
    public interface DictEnum {
        String getValue();

        String getLabel();
    }

    private final Map<String, Class<? extends DictEnum>> enumRegistry = new HashMap<>();
    private final ExcelProperties excelProperties;
    private final PathMatchingResourcePatternResolver resourcePatternResolver;
    private final CachingMetadataReaderFactory metadataReaderFactory;

    public EnumDictProvider(ExcelProperties excelProperties) {
        this.excelProperties = excelProperties;
        this.resourcePatternResolver = new PathMatchingResourcePatternResolver();
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("==========枚举字典-加载开始==========");
        if (!excelProperties.getDict().isEnableEnumScan()) {
            log.info("枚举字典自动扫描已禁用");
            log.info("==========枚举字典-加载结束==========");
            return;
        }

        String[] scanPackages = excelProperties.getDict().getEnumScanPackages();
        if (scanPackages.length == 0) {
            log.warn("未配置枚举字典扫描包路径");
            log.info("==========枚举字典-加载结束==========");
            return;
        }

        for (String basePackage : scanPackages) {
            log.info("枚举字典扫描包路径：{}", basePackage);
            String packageSearchPath = "classpath*:" + ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/*.class";
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                ClassMetadata classMetadata = metadataReader.getClassMetadata();

                if (classMetadata.isIndependent() && !classMetadata.isInterface() && !classMetadata.isAbstract()) {
                    String className = classMetadata.getClassName();
                    Class<?> enumClass = Class.forName(className);

                    if (enumClass.isEnum() && DictEnum.class.isAssignableFrom(enumClass)) {
                        @SuppressWarnings("unchecked")
                        Class<? extends DictEnum> dictEnumClass = (Class<? extends DictEnum>) enumClass;
                        String dictCode = dictEnumClass.getSimpleName();
                        enumRegistry.put(dictCode, dictEnumClass);
                        log.info("注册枚举字典：{} -> {}", dictCode, className);
                    }
                }
            }
        }
        log.info("==========枚举字典-加载结束==========");
    }

    @Override
    public boolean support(String dictCode) {
        return enumRegistry.containsKey(dictCode);
    }

    @Override
    public Map<String, String> getDictData(String dictCode) {
        Class<? extends DictEnum> enumClass = enumRegistry.get(dictCode);
        if (enumClass == null) {
            return null;
        }

        return Arrays.stream(enumClass.getEnumConstants())
                .collect(Collectors.toMap(
                        DictEnum::getValue,
                        DictEnum::getLabel
                ));
    }

    @Override
    public ExcelDict.DictType getType() {
        return ExcelDict.DictType.ENUM;
    }
} 