import requestUtils from '../../utils/requestUtils'

const baseUrl = '../admin-system/dataDictionaryApi';


/**
 * 查询 systemModule 指定的 FieldType 的字典
 * @param roleFilter
 * @constructor
 */
export const LoadDataDictionary = (systemModule, fieldType) => requestUtils.get(baseUrl + '/findDataDictionary' + (systemModule ? ('/' + systemModule + '/') : '/') + fieldType);

/**
 * 查询 system 系统指定的 FieldType 的字典
 * @param roleFilter
 * @constructor
 */
export const LoadSysDataDictionary = fieldType => requestUtils.get(baseUrl + '/findDataDictionary/adminSystem/' + fieldType);

/**
 * 查询 user 系统指定的 FieldType 的字典
 * @param roleFilter
 * @constructor
 */
export const LoadUserDataDictionary = (fieldType) => requestUtils.get(baseUrl + '/findDataDictionary/adminUser/' + fieldType);

/**
 * 查询 job 系统指定的 FieldType 的字典
 * @param roleFilter
 * @constructor
 */
export const LoadJobDataDictionary = (fieldType) => requestUtils.get(baseUrl + '/findDataDictionary/publicJob/' + fieldType);

/**
 * 根据条件查询数据字典信息
 * @param data
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const LoadDictionaryList = data => requestUtils.post(baseUrl + '/list', Object.assign(data));

/**
 * 根据下拉字段分类
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindFieldType = () => requestUtils.get(baseUrl + '/findFieldType');

/**
 * 字典下拉系统模块
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const FindSysModule = () => requestUtils.get(baseUrl + '/findSysModule');

/**
 * 保存/更新数据字典
 * @returns {*|Promise|Promise<any>}
 * @constructor
 */
export const SaveOrUpdate = data => requestUtils.put(baseUrl + '/saveOrUpdate', Object.assign(data));

/**
 * 根据id删除
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DelById = id => requestUtils.del(baseUrl + '/del/' + id);

/**
 * 根据id禁用数据字典
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitById = id => requestUtils.put(baseUrl + '/prohibit/' + id);
