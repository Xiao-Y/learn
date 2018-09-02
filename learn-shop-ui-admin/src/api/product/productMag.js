import request from '@/utils/request'

/**
 * 根据条件查询商品信息
 * @param productFilter
 * @constructor
 */
export function LoadDataProductList(productFilter) {
  return request({
    url: 'core-product/product/findProductList',
    method: 'post',
    data: Object.assign(productFilter)
  })
}

/**
 * 保存商品信息
 * @param data
 * @constructor
 */
export function SaveProduct(data) {
  return request({
    url: 'core-product/product/saveProduct',
    method: 'post',
    data: Object.assign(data)
  });
}

/**
 * 更新商品信息
 * @param data
 * @constructor
 */
export function UpdateProduct(data) {
  return request({
    url: 'core-product/product/updateProduct',
    method: 'put',
    data: Object.assign(data)
  });
}

/**
 * 根据id删除商品信息
 * @param id
 * @constructor
 */
export function DeleteProductById(id) {
  return request({
    url: 'core-product/product/deleteProductById/' + id,
    method: 'delete'
  });
}
