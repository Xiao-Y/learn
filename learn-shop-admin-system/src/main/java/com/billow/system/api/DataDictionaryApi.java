package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.common.redis.RedisUtils;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final static String key = RedisCst.COMM_DICTIONARY;

    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "查询数据字典，指定 systemModule 和 fieldType")
    @GetMapping("/findDataDictionary/{systemModule}/{fieldType}")
    public List<DataDictionaryVo> findDataDictionary(@PathVariable("systemModule") String systemModule, @PathVariable("fieldType") String fieldType) throws Exception {
        String redisKey = key + systemModule;
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

//    @PostMapping("/findDataDictionaryByCondition")
//    public List<DataDictionaryVo> findDataDictionaryByCondition(DataDictionaryVo dataDictionaryVo) throws Exception {
//        dataDictionaryVo.setValidInd(true);
//        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
//        return dataDictionaryVos;
//    }
}
