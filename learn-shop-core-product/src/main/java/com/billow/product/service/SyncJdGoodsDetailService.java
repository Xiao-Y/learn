package com.billow.product.service;

import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.pojo.vo.SyncJdGoods;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * 导入商品列表数据
 *
 * @author 千面
 * @date 2021/11/23 11:08
 */
public interface SyncJdGoodsDetailService {

    /**
     * 请求商品详细信息
     *
     * @param spuNo 商品no
     * @return {@link Element}
     * @author xiaoy
     * @since 2021/11/27 17:12
     */
    Element startRequest(String spuNo) throws Exception;

    /**
     * 构建 sku,和规格信息
     *
     * @param element 通过 sku 请求的商品页面
     * @return {@link List< GoodsSkuVo>}
     * @author xiaoy
     * @since 2021/11/27 19:41
     */
    SyncJdGoods parseGoodsSku(Element element) throws Exception;
}
