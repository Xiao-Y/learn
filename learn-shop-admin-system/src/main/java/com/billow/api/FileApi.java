package com.billow.api;

import com.billow.common.base.BaseApi;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api("文件上传下载API")
@RestController
@RequestMapping("/fileApi")
public class FileApi extends BaseApi {

    @PostMapping("/uploadProductImage/{productId}")
    public String uploadProductImage(@PathVariable String productId,
                                     @RequestParam("file") MultipartFile multipartFile) {
        String userCode = super.findUserCode();
        System.out.println(productId);
        System.out.println(multipartFile.getOriginalFilename());
        return null;
    }
}
