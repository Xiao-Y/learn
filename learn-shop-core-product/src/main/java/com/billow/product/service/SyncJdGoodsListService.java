package com.billow.product.service;

import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.po.ShopInfoPo;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * 导入商品列表数据
 *
 * @author 千面
 * @date 2021/11/23 11:08
 */
public interface SyncJdGoodsListService {

    /**
     * 请求商品列表
     *
     * @param keyword 查询关键字
     * @param page    分页
     * @return {@link Element}
     * @author xiaoy
     * @since 2021/11/27 17:12
     */
    Element startRequest(String keyword, int page) throws Exception;

    /**
     * 解析商品列表页面数据
     *
     * @param body 整个商品列表页面元素
     * @return {@link List < GoodsSpuPo>}
     * @author xiaoy
     * @since 2021/11/22 21:19
     */
    List<GoodsSpuPo> parseGoodsList(Element body);

    /**
     * 解析 品牌信息
     *
     * @param body 整个商品列表页面元素
     * @return {@link List< GoodsBrandPo>}
     * @author xiaoy
     * @since 2021/11/27 17:25
     */
    List<GoodsBrandPo> parseBrand(Element body);

    /**
     * 解析店铺信息
     *
     * @param goodsList 商品列表元素
     * @return {@link List< ShopInfoPo>}
     * @author xiaoy
     * @since 2021/11/27 17:05
     */
    List<ShopInfoPo> parseShopInfo(Element goodsList);
}
