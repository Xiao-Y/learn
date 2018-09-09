package com.billow.service.impl;

import cn.hutool.core.io.FileUtil;
import com.billow.common.base.DefaultSpec;
import com.billow.common.utils.QueryUtils;
import com.billow.dao.ProductDao;
import com.billow.dao.ProductImageDao;
import com.billow.dao.ProductSpec;
import com.billow.pojo.po.product.ProductImagePo;
import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductImageVo;
import com.billow.pojo.vo.product.ProductVo;
import com.billow.service.ProductService;
import com.billow.tools.generator.SequenceUtil;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImageDao productImageDao;

    private final static String COMMON_PATH_MIN = "D:/project/IDEAProjects/learn/learn-shop-ui-admin/src";
    private final static String BASE_PATH = "/static/proudct/min/";

    @Override
    public Page<ProductPo> findProductList(ProductVo productVo) {
        Pageable pageable = new PageRequest(productVo.getPageNo(), productVo.getPageSize());
//        ProductPo convert = ConvertUtils.convert(productVo, ProductPo.class);
//        DefaultSpec<ProductPo> defaultSpec = new DefaultSpec<>(convert);
        productVo.setDeleFlag("1");
        Page<ProductPo> productPos = productDao.findAll(ProductSpec.byProductList(productVo), pageable);
        return productPos;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveProduct(ProductVo productVo, String userCode) throws Exception {
        productVo.setId(SequenceUtil.createSequence());
        productVo.setDeleFlag("1");
        FieldUtils.setCommonFieldByInsertWithValidInd(productVo, userCode);
        ProductPo convert = ConvertUtils.convert(productVo, ProductPo.class);
        productDao.save(convert);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateProduct(ProductVo productVo, String userCode) throws Exception {
        FieldUtils.setCommonFieldByUpdate(productVo, userCode);
        ProductPo convert = ConvertUtils.convert(productVo, ProductPo.class);
        productDao.save(convert);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductVo deleteProductById(String id, String userCode) throws Exception {
        ProductPo productPo = productDao.findOne(id);
        ProductVo productVo = ConvertUtils.convert(productPo, ProductVo.class);
        productPo.setDeleFlag("0");
        FieldUtils.setCommonFieldByUpdate(productPo, userCode);
        productDao.save(productPo);
        return productVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void uploadProductImage(ProductImageVo productImageVo, String userCode) throws Exception {
        // 保存图片信息
        FieldUtils.setCommonFieldByInsertWithValidInd(productImageVo, userCode);
        productImageVo.setId(SequenceUtil.createSequence());
        String newFileName = SequenceUtil.createSequenceBySuffix(".jpg");
        productImageVo.setNewImageName(newFileName);
        ProductImagePo productImagePo = ConvertUtils.convert(productImageVo, ProductImagePo.class);
        productImageDao.save(productImagePo);

        // 保存图片
        String filePath = COMMON_PATH_MIN + BASE_PATH + newFileName;
        FileUtil.writeFromStream(productImageVo.getInputStream(), filePath);
    }

    @Override
    public List<ProductImageVo> findProductImageByProductId(String productId, ProductImageVo productImageVo) throws Exception {
        ProductImagePo productImagePo = ConvertUtils.convert(productImageVo, ProductImagePo.class);
        productImagePo.setProductId(productId);
        DefaultSpec<ProductImagePo> defaultSpec = new DefaultSpec<>(productImagePo);
        List<ProductImagePo> productImagePos = productImageDao.findAll(defaultSpec, new Sort(Sort.Direction.ASC, "id"));

        List<ProductImageVo> convert = ConvertUtils.convert(productImagePos, ProductImageVo.class);
        if (ToolsUtils.isNotEmpty(convert)) {
            convert.stream().forEach(item -> {
                item.setImagePath(BASE_PATH + item.getNewImageName());
            });
        }
        return convert;
    }
}
