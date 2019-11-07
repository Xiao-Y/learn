package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.common.redis.RedisUtils;
import com.billow.system.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字典
 *
 * @author liuyongtao
 * @create 2019-07-11 10:59
 */
@Slf4j
@RestController
@RequestMapping("/dataDictionaryApi")
@Api(value = "数据字典管理")
public class DataDictionaryApi extends BaseApi {

    private final static String SYS_MODULE = RedisCst.COMM_DICTIONARY_SYS_MODULE;
    private final static String FIELD_TYPE_KEY = RedisCst.COMM_DICTIONARY_FIELD_TYPE;
    private final static String SYS_MODULE_LIST = RedisCst.COMM_DICTIONARY_SYS_MODULE_LIST;

    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    @Qualifier("initDictionary")
    private IStartLoading initDictionary;

    @ApiOperation(value = "查询数据字典，指定 systemModule 和 fieldType")
    @GetMapping("/findDataDictionary/{systemModule}/{fieldType}")
    public List<DataDictionaryVo> findDataDictionary(@PathVariable("systemModule") String systemModule, @PathVariable("fieldType") String fieldType) throws Exception {
        String redisKey = SYS_MODULE + systemModule;
        // 从 redis 中获取
        List<DataDictionaryVo> redisData = redisUtils.getArray(redisKey, DataDictionaryVo.class);
        if (ToolsUtils.isNotEmpty(redisData)) {
            return redisData.stream().filter(f -> f.getFieldType().equals(fieldType)).collect(Collectors.toList());
        }
        DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();
        dataDictionaryVo.setSystemModule(systemModule);
        dataDictionaryVo.setFieldType(fieldType);
        dataDictionaryVo.setValidInd(true);
        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
        // 保存到 redis 中
        redisUtils.setObj(redisKey, ConvertUtils.convertIgnoreBase(dataDictionaryVos, DataDictionaryPo.class));
        return dataDictionaryVos;
    }

    @ApiOperation(value = "查询数据字典，指定 fieldType")
    @GetMapping("/findDataDictionary/{fieldType}")
    public List<DataDictionaryVo> findDataDictionary(@PathVariable("fieldType") String fieldType) throws Exception {
        DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();
        dataDictionaryVo.setFieldType(fieldType);
        dataDictionaryVo.setValidInd(true);
        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
        return dataDictionaryVos;
    }

    @ApiOperation("根据条件查询数据字典信息")
    @PostMapping("/list")
    public Page<DataDictionaryPo> listByPage(@RequestBody DataDictionaryVo dataDictionaryVo) {
        return dataDictionaryService.listByPage(dataDictionaryVo);
    }

    @ApiOperation("字典下拉字段分类")
    @GetMapping("/findFieldType")
    public List<DataDictionaryPo> findFieldType() {
        List<String> fieldTypes = this.getRedisValues(FIELD_TYPE_KEY, String.class);
        if (ToolsUtils.isEmpty(fieldTypes)) {
            fieldTypes = dataDictionaryService.findFieldType();
            // 放入缓存中
            this.setRedisObject(FIELD_TYPE_KEY, fieldTypes);
        }

        List<DataDictionaryPo> dataDictionaryPos = new ArrayList<>();
        fieldTypes.stream().forEach(fieldType -> {
            DataDictionaryPo po = new DataDictionaryPo();
            po.setFieldValue(fieldType);
            po.setFieldDisplay(fieldType);
            dataDictionaryPos.add(po);
        });

        return dataDictionaryPos;
    }
    @ApiOperation("字典下拉系统模块")
    @GetMapping("/findSysModule")
    public List<DataDictionaryPo> findSysModule() {
        List<String> sysModules = this.getRedisValues(SYS_MODULE_LIST, String.class);
        if (ToolsUtils.isEmpty(sysModules)) {
            sysModules = dataDictionaryService.findSysModule();
            // 放入缓存中
            this.setRedisObject(SYS_MODULE_LIST, sysModules);
        }

        List<DataDictionaryPo> dataDictionaryPos = new ArrayList<>();
        sysModules.stream().forEach(sysModule -> {
            DataDictionaryPo po = new DataDictionaryPo();
            po.setFieldValue(sysModule);
            po.setFieldDisplay(sysModule);
            dataDictionaryPos.add(po);
        });

        return dataDictionaryPos;
    }

    @ApiOperation("保存/更新数据字典")
    @PutMapping("/saveOrUpdate")
    public DataDictionaryVo saveOrUpdate(@RequestBody DataDictionaryVo dataDictionaryVo) {
        dataDictionaryService.saveOrUpdate(dataDictionaryVo);
        initDictionary.init();
        return dataDictionaryVo;
    }

    @ApiOperation("根据id删除数据字典")
    @DeleteMapping("/del/{id}")
    public void delById(@PathVariable Long id) {
        dataDictionaryService.delById(id);
        initDictionary.init();
    }

    @ApiOperation("根据id禁用数据字典")
    @PutMapping("/prohibit/{id}")
    public DataDictionaryVo prohibitById(@PathVariable Long id) {
        DataDictionaryVo dataDictionaryVo = dataDictionaryService.prohibitById(id);
        return dataDictionaryVo;
    }
}
