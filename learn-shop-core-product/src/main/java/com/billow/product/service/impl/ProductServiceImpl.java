package com.billow.product.service.impl;

import cn.hutool.core.io.FileUtil;
import com.billow.common.base.DefaultSpec;
import com.billow.product.dao.ProductDao;
import com.billow.product.dao.ProductImageDao;
import com.billow.product.dao.ProductSpec;
import com.billow.product.pojo.po.ProductImagePo;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.vo.ProductImageVo;
import com.billow.product.pojo.vo.ProductVo;
import com.billow.product.service.ProductService;
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
        // 删除商品信息
        ProductPo productPo = productDao.findOne(id);
        ProductVo productVo = ConvertUtils.convert(productPo, ProductVo.class);
        productPo.setDeleFlag("0");
        FieldUtils.setCommonFieldByUpdate(productPo, userCode);
        productDao.save(productPo);
        // 删除商品图片
        List<ProductImagePo> productImagePos = productImageDao.findByProductIdAndValidInd(id, true);
        if (ToolsUtils.isNotEmpty(productImagePos)) {
            productImagePos.stream().forEach(item -> {
                item.setValidInd(false);
                FieldUtils.setCommonFieldByUpdate(item, userCode);
            });
            productImageDao.save(productImagePos);
        }
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
        productImagePo.setValidInd(true);
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteProductImageById(String id) throws Exception {
        ProductImagePo productImagePo = productImageDao.findOne(id);
        if (productImagePo != null) {
            productImagePo.setValidInd(false);
            productImageDao.save(productImagePo);
        }
    }
}
