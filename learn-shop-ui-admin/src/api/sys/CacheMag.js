import requestUtils from '../../utils/requestUtils'


const baseUrl = '../admin-system/cacheApi';

/**
 * 初始化所有缓存
 * @constructor
 */
export const LoadDataCacheAll = () => requestUtils.put(baseUrl + "/initAll");

/**
 * 初始化指定的缓存
 * @param cacheType
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const LoadDataCacheByType = (cacheType) => requestUtils.put(baseUrl + "/init/" + cacheType);

/**
 * 清空指定mybatis产生的缓存
 * @param cacheNamespace 命名空间
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ClearCacheNamespace = (cacheNamespace) => requestUtils.put(baseUrl + "/clearCacheNamespace/" + cacheNamespace);

