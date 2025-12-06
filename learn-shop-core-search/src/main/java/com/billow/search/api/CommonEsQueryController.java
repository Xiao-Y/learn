package com.billow.search.api;

import com.billow.search.dao.DocumentMapper;
import com.billow.search.pojo.po.Document;
import com.billow.search.service.CommonEsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 通用 ES 查询 Controller
 *
 * @author billow
 * @since 2025-01-01
 */
@Tag(name = "通用 ES 查询接口")
@RestController
@RequestMapping("/es/common")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonEsQueryController {

    private final CommonEsService commonEsService;
    private final DocumentMapper documentMapper;

    /**
     * 通用查询接口
     * <p>
     * 支持的查询格式：
     * - "param1":"a"                                    // 模糊查询
     * - "param2":"=a"                                   // 精确查询
     * - "param3":"2025-01-01 12:23:11~2025-12-01 12:23:11"  // 时间范围查询
     * - "param3":"~2025-12-01 12:23:11"                 // 查询时间小于等于
     * - "param4":"2025-12-01 12:23:11~"                 // 查询时间大于等于
     * - "param5":"a,b,c,d"                              // 多值精确匹配（IN）
     * - "param6":">7"                                   // 大于
     * - "param7":"<7"                                   // 小于
     * - "param8":">=7"                                  // 大于等于
     * - "param9":"<=7"                                  // 小于等于
     *
     * @param params 查询参数
     * @return 查询结果
     */
    @Operation(summary = "通用查询 - Document 示例")
    @PostMapping("/query/document")
    public List<Document> queryDocument(@RequestBody Map<String, String> params) {
        return commonEsService.query(documentMapper, params, Document.class);
    }

    /**
     * 通用分页查询接口
     *
     * @param params   查询参数
     * @param pageNum  页码（从1开始）
     * @param pageSize 每页大小
     * @return 查询结果
     */
    @Operation(summary = "通用分页查询 - Document 示例")
    @PostMapping("/query/document/page")
    public List<Document> queryDocumentPage(
            @RequestBody Map<String, String> params,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return commonEsService.queryPage(documentMapper, params, Document.class, pageNum, pageSize);
    }

    /**
     * 通用查询总数接口
     *
     * @param params 查询参数
     * @return 总数
     */
    @Operation(summary = "通用查询总数 - Document 示例")
    @PostMapping("/query/document/count")
    public Long countDocument(@RequestBody Map<String, String> params) {
        return commonEsService.count(documentMapper, params, Document.class);
    }
}
