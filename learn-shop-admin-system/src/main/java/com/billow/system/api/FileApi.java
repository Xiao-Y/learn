package com.billow.system.api;

import cn.hutool.core.io.FileUtil;
import com.billow.common.base.BaseApi;
import com.billow.system.pojo.ex.FileHandleEx;
import com.billow.system.properties.CommonProperties;
import com.billow.system.properties.CustomProperties;
import com.billow.tools.constant.CommonCst;
import com.billow.tools.generator.SequenceUtil;
import com.billow.tools.utlis.StringUtils;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("/fileApi")
@Api(value = "文件处理API")
public class FileApi extends BaseApi {

    private static final String PATH = "path";
    private static final String BASE_FILE_PATH = "baseFilePath";
    private static final String FILE_PATH = "filePath";

    @Autowired
    private CustomProperties customProperties;

    @ApiOperation("单个上传文件")
    @PostMapping(value = {"/singleUpload/{uploadType}", "/singleUpload/{uploadType}/{fileName}"})
    public FileHandleEx singleUpload(@PathVariable("uploadType") String uploadType,
                                     @PathVariable(value = "fileName", required = false) String newFileName,
                                     @RequestParam("file") MultipartFile file) throws IOException {
        // 获取上下文
        String contextPath = this.getContextPath();
        // 静态文件映射
        String imageMapping = customProperties.getCommon().getImageMapping();
        // 上传路径
        Map<String, String> map = this.getFileBasePath(uploadType);
        String path = map.get(PATH);
        // 保存图片
        FileHandleEx ex = this.saveFile(imageMapping, contextPath, map, file, newFileName, 0);
        return ex;
    }

    @ApiOperation("批量上传文件")
    @PostMapping(value = {"/batchUpload/{uploadType}", "/batchUpload/{uploadType}/{fileName}"})
    public List<FileHandleEx> batchUpload(@PathVariable("uploadType") String uploadType,
                                          @PathVariable(value = "fileName", required = false) String newFileName,
                                          @RequestParam("file") List<MultipartFile> files) throws IOException {
        List<FileHandleEx> list = new ArrayList<>();
        // 获取上下文
        String contextPath = this.getContextPath();
        // 静态文件映射
        String imageMapping = this.customProperties.getCommon().getImageMapping();
        // 上传路径
        Map<String, String> map = this.getFileBasePath(uploadType);
        String path = map.get(PATH);
        // 保存图片
        for (int i = 0; i < files.size(); i++) {
            FileHandleEx ex = this.saveFile(imageMapping, contextPath, map, files.get(i), newFileName, i);
            list.add(ex);
        }
        return list;
    }

    /**
     * 获取上下文
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/8 10:49
     */
    private String getContextPath() {
        String contextPath = request.getContextPath();
        contextPath = contextPath == null ? "" : contextPath.replaceFirst("/", "");
        return contextPath;
    }

    /**
     * 保存文件
     *
     * @param imageMapping 文件映射位置
     * @param contextPath  上下文
     * @param map
     * @param file         需要保存的文件
     * @param newFileName  新文件名
     * @param index
     * @return
     * @throws IOException
     */
    private FileHandleEx saveFile(String imageMapping, String contextPath, Map<String, String> map, MultipartFile file, String newFileName, int index) throws IOException {
        String sub = ".";
        // 获取文件后缀
        String last = StringUtils.substringAfterLast(file.getOriginalFilename(), sub);
        // 新文件名
        if (ToolsUtils.isEmpty(newFileName) || "undefined".equals(newFileName) || "null".equals(newFileName)) {
            newFileName = SequenceUtil.createSequence();
        }
        String newFile = newFileName + sub + last;
        String filePath = map.get(PATH) + "/" + newFile;
        FileUtil.writeFromStream(file.getInputStream(), filePath);

        FileHandleEx ex = new FileHandleEx();
        ex.setPos(index);
        // admin-system/displayImag/markdown/****.jpg
        ex.setFileUrl(contextPath + imageMapping + map.get(FILE_PATH) + "/" + newFile);
        ex.setNewFileName(newFileName);
        ex.setFilePath(filePath);
        return ex;
    }

    /**
     * 通过上传的文件类型，返回上传路径
     *
     * @param uploadType
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/8 10:32
     */
    private Map<String, String> getFileBasePath(String uploadType) {
        CommonProperties common = customProperties.getCommon();
        String baseFilePath = "";
        String filePath = "";

        // D:/uploadfile
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {  //如果是Windows系统
            baseFilePath = common.getWin().getBaseFilePath();
        } else {
            baseFilePath = common.getLinux().getBaseFilePath();
        }

        // markdown 的图片上传 /markdown
        if (CommonCst.UPLOAD_TYPE_MARKDOWN.equals(uploadType)) {
            filePath = common.getMarkdownImgPath();
        } else if (CommonCst.UPLOAD_TYPE_USER_ICON.equals(uploadType)) {
            filePath = common.getUserIconImgPath();
        }

        // D:/uploadfile/markdown
        String path = baseFilePath + filePath;
        if (!FileUtil.exist(path)) {
            FileUtil.mkdir(path);
        }
        Map<String, String> map = new HashMap<>();
        map.put(PATH, path);
        map.put(BASE_FILE_PATH, baseFilePath);
        map.put(FILE_PATH, filePath);
        return map;
    }
}
