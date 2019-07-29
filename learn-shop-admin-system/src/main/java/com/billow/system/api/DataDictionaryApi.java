package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.system.pojo.vo.DataDictionaryVo;
import com.billow.system.service.DataDictionaryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @GetMapping("/findDataDictionary/{systemModule}/{fieldType}")
    public List<DataDictionaryVo> findDataDictionary(@PathVariable("systemModule")String systemModule,@PathVariable("fieldType")String fieldType) throws Exception {
        DataDictionaryVo dataDictionaryVo = new DataDictionaryVo();
        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
        return dataDictionaryVos;
    }

//    @PostMapping("/findDataDictionaryByCondition")
//    public List<DataDictionaryVo> findDataDictionaryByCondition(DataDictionaryVo dataDictionaryVo) throws Exception {
//        List<DataDictionaryVo> dataDictionaryVos = dataDictionaryService.findDataDictionaryByCondition(dataDictionaryVo);
//        return dataDictionaryVos;
//    }
}
