
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.search.GoodsSpuSearchParam;
import com.billow.product.pojo.vo.GoodsSpuVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * spu表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
public interface GoodsSpuService extends HighLevelService<GoodsSpuPo, GoodsSpuSearchParam> {

    /**
     * 添加或者更新
     *
     * @param goodsSpuVo
     * @return void
     * @author LiuYongTao
     * @date 2019/12/5 17:04
     */
    void addOrUpdate(GoodsSpuVo goodsSpuVo);

    /**
     * 异步导出商品SPU数据
     *
     * @param response
     * @return String 任务ID
     * @author 千面
     * @date 2025/3/12 20:03
     */
    String asyncExport(HttpServletResponse response);

    /**
     * 异步导入商品SPU数据
     *
     * @param file Excel文件
     * @return 导入任务ID
     */
    String asyncImport(MultipartFile file) throws ExecutionException, InterruptedException;
}