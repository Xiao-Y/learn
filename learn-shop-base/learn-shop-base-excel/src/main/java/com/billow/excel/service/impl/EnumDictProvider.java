package com.billow.excel.service.impl;

import com.billow.excel.annotation.ExcelColumn.DictType;
import com.billow.excel.service.impl.CompositeDictService.DictProvider;
import com.billow.excel.spring.boot.autoconfigure.ExcelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 枚举字典提供者
 */
@Slf4j
@Order(1)
@Component
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
        if (!excelProperties.getDictConfig().isEnableEnumScan()) {
            log.info("枚举字典自动扫描已禁用");
            return;
        }
        
        String[] scanPackages = excelProperties.getDictConfig().getEnumScanPackages();
        if (scanPackages.length == 0) {
            log.warn("未配置枚举字典扫描包路径");
            return;
        }
        
        for (String basePackage : scanPackages) {
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
                        String dictCode = getDictCode(dictEnumClass);
                        enumRegistry.put(dictCode, dictEnumClass);
                        log.info("注册枚举字典：{} -> {}", dictCode, className);
                    }
                }
            }
        }
    }
    
    private String getDictCode(Class<? extends DictEnum> enumClass) {
        // 默认使用枚举类名转换为下划线格式作为dictCode
        return StringUtils.capitalize(enumClass.getSimpleName())
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase();
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
    public DictType getType() {
        return DictType.ENUM;
    }
} 