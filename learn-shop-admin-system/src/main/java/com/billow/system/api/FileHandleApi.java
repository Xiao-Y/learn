package com.billow.system.api;

import cn.hutool.core.io.FileUtil;
import com.billow.system.pojo.ex.FileHandleEx;
import com.billow.system.properties.CommonProperties;
import com.billow.system.properties.CustomProperties;
import com.billow.tools.generator.SequenceUtil;
import com.billow.tools.utlis.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件处理API
 *
 * @author liuyongtao
 * @create 2019-07-26 15:01
 */
@Slf4j
@RestController
@RequestMapping("/fileHandleApi")
@Api(value = "文件处理API")
public class FileHandleApi {

    @Autowired
    private CustomProperties customProperties;

    @ApiOperation("上传文件，保存图片信息")
    @PostMapping("/upload")
    public List<FileHandleEx> upload(@RequestParam("file") List<MultipartFile> files) throws IOException {
        CommonProperties common = customProperties.getCommon();
        String wordResourceDandler = common.getWordResourceDandler();
        String sub = ".";

        List<FileHandleEx> list = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile multipartFile = files.get(i);
            // 保存图片
            String last = StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), sub);
            String newFileName = SequenceUtil.createSequenceBySuffix(sub + last);

            String filePath = common.getWordImgPath() + newFileName;
            FileUtil.writeFromStream(multipartFile.getInputStream(), filePath);

            FileHandleEx ex = new FileHandleEx();
            ex.setPos((i + 1));
            ex.setFileUrl(wordResourceDandler + "/" + newFileName);
            list.add(ex);

        }
        return list;
    }
}
