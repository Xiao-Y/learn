import request from '@/utils/request'

/**
 * 根据条件查询商品信息
 */
export function LoadDataProductList(productFilter) {
  return request({
    url: 'core-product/product/findProductList',
    method: 'post',
    data: Object.assign(productFilter)
  })
}
