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

