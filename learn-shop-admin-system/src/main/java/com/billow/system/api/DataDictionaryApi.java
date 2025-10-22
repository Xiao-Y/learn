package com.billow.system.api;

import cn.hutool.core.util.StrUtil;
import com.billow.common.base.BaseApi;
import com.billow.redis.util.RedisUtils;
import com.billow.system.common.init.IStartLoading;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@Tag(name = "DataDictionaryApi", description = "数据字典管理")
public class DataDictionaryApi extends BaseApi {

    private final static String FIELD_TYPE_KEY = RedisCst.COMM_DICTIONARY_FIELD_TYPE;

    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    @Qualifier("initDictionary")
    private IStartLoading initDictionary;

    @Operation(summary = "查询数据字典，指定 systemModule 和 fieldType")
    @GetMapping("/findDataDictionary/{systemModule}/{fieldType}")
    public List<DataDictionaryVo> findDataDictionary(@PathVariable("systemModule") String systemModule, @PathVariable("fieldType") String fieldType) throws Exception {
        // 从 redis 中获取
        String key = RedisCst.COMM_DICTIONARY_FIELD_TYPE + ":" + StrUtil.replace(StrUtil.toUnderlineCase(systemModule), "_", "-");
        List<DataDictionaryPo> redisData = redisUtils.getHash(key, fieldType, DataDictionaryPo.class);
        if (ToolsUtils.isNotEmpty(redisData)) {
            return ConvertUtils.convertIgnoreBase(redisData, DataDictionaryVo.class);
        }
        DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();
        dataDictionaryVo.setSystemModule(systemModule);
        dataDictionaryVo.setFieldType(fieldType);
        dataDictionaryVo.setValidInd(true);
        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
        // 保存到 redis 中
        redisUtils.setHash(key, fieldType, ConvertUtils.convertIgnoreBase(dataDictionaryVos, DataDictionaryPo.class));
        return dataDictionaryVos;
    }

    @Operation(summary = "查询数据字典，指定 fieldType")
    @GetMapping("/findDataDictionary/{fieldType}")
    public List<DataDictionaryVo> findDataDictionary(@PathVariable("fieldType") String fieldType) throws Exception {
        DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();
        dataDictionaryVo.setFieldType(fieldType);
        dataDictionaryVo.setValidInd(true);
        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
        return dataDictionaryVos;
    }

    @Operation(summary = "根据条件查询数据字典信息")
    @PostMapping("/list")
    public Page<DataDictionaryPo> listByPage(@RequestBody DataDictionaryVo dataDictionaryVo) {
        return dataDictionaryService.listByPage(dataDictionaryVo);
    }

    @Operation(summary = "字典下拉字段分类")
    @GetMapping("/findFieldType")
    public List<DataDictionaryPo> findFieldType() {
        List<DataDictionaryPo> dataDictionaryPos = new ArrayList<>();
        Map<String, String> routeInfoMap = redisUtils.getHashAllObj(RedisCst.COMM_ROUTE_INFO);
        for (Map.Entry<String, String> entry : routeInfoMap.entrySet()) {
            List<String> fieldTypes = redisUtils.getHashKeys(RedisCst.COMM_DICTIONARY_FIELD_TYPE + ":" + entry.getKey());
            if (ToolsUtils.isEmpty(fieldTypes)) {
                //fieldTypes = dataDictionaryService.findFieldType();
                continue;
            }
            fieldTypes.stream().forEach(fieldType -> {
                DataDictionaryPo po = new DataDictionaryPo();
                po.setFieldValue(fieldType);
                po.setFieldDisplay(fieldType);
                dataDictionaryPos.add(po);
            });
        }
        return dataDictionaryPos;
    }

    @Operation(summary = "字典下拉系统模块")
    @GetMapping("/findSysModule")
    public List<DataDictionaryPo> findSysModule() {
        Map<String, String> routeInfoMap = redisUtils.getHashAllObj(RedisCst.COMM_ROUTE_INFO);
        List<DataDictionaryPo> dataDictionaryPos = routeInfoMap.entrySet().stream().map(m -> {
            DataDictionaryPo po = new DataDictionaryPo();
            po.setFieldValue(m.getKey());
            po.setFieldDisplay(m.getKey());
            return po;
        }).collect(Collectors.toList());


        return dataDictionaryPos;
    }

    @Operation(summary = "保存/更新数据字典")
    @PutMapping("/saveOrUpdate")
    public DataDictionaryVo saveOrUpdate(@RequestBody DataDictionaryVo dataDictionaryVo) {
        dataDictionaryService.saveOrUpdate(dataDictionaryVo);
        initDictionary.init();
        return dataDictionaryVo;
    }

    @Operation(summary = "根据id删除数据字典")
    @DeleteMapping("/del/{id}")
    public void delById(@PathVariable Long id) {
        dataDictionaryService.delById(id);
        initDictionary.init();
    }

    @Operation(summary = "根据id禁用数据字典")
    @PutMapping("/prohibit/{id}")
    public DataDictionaryVo prohibitById(@PathVariable Long id) {
        DataDictionaryVo dataDictionaryVo = dataDictionaryService.prohibitById(id);
        return dataDictionaryVo;
    }

    @Operation(summary = "加载缓存中路由信息")
    @GetMapping("/findDataRouteCache")
    public List<DataDictionaryVo> findDataRouteCache() {
        List<DataDictionaryVo> vos = new ArrayList<>();
        long id = 0;
        Map<String, String> routeInfoMap = redisUtils.getHashAllObj(RedisCst.COMM_ROUTE_INFO);
        for (Map.Entry<String, String> entry : routeInfoMap.entrySet()) {
            DataDictionaryVo vo = new DataDictionaryVo();
            vo.setId(id++);
            vo.setFieldValue("http://" + entry.getValue());
            vo.setFieldDisplay(entry.getKey());
            vos.add(vo);
        }
        return vos;
    }
}
