import request from '@/utils/request'

const baseUrl = 'admin-system/roleApi';

/**
 * 根据条件查询商品信息
 * @param roleFilter
 * @constructor
 */
export function LoadDataRoleList(roleFilter) {
  return request({
    url: baseUrl + '/findRoleList',
    method: 'post',
    data: Object.assign(roleFilter)
  })
}

/**
 * 根据角色ID查询权限ID
 * @param roleFilter
 * @constructor
 */
export function LoadDataPermissionIdList(roleId) {
  return request({
    url: baseUrl + '/findPermissionByRoleId/' + roleId,
    method: 'get'
  })
}

/**
 * 根据角色ID查询菜单ID
 * @param roleFilter
 * @constructor
 */
export function LoadDataMenuIdList(roleId) {
  return request({
    url: baseUrl + '/findMenuByRoleId/' + roleId,
    method: 'get'
  })
}

/**
 * 保存商品信息
 * @param data
 * @constructor
 */
export function SaveRole(data) {
  // console.info(Object.assign(data));
  return request({
    url: baseUrl + '/saveRole',
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
    url: 'core-product/productApi/updateProduct',
    method: 'put',
    data: Object.assign(data)
  });
}

/**
 * 根据id删除商品信息
 * @param id
 * @constructor
 */
export function DeleteRoleById(id) {
  return request({
    url: 'core-product/productApi/deleteProductById/' + id,
    method: 'delete'
  });
}

/**
 * 查询商品图片
 * @param productId 商品id
 * @constructor
 */
export function GetProuctImageByProductId({productId}) {
  return request({
    url: 'core-product/productApi/findProductImageByProductId/' + productId,
    method: 'get'
  });
}

/**
 * 通过图片id 删除商品图上
 * @param id 图片id
 * @constructor
 */
export function DeleteProductImageById(id) {
  return request({
    url: 'core-product/productApi/deleteProductImageById/' + id,
    method: 'delete'
  });
}
