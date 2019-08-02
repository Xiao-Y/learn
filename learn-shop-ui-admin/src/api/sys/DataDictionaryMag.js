import requestUtils from '../../utils/requestUtils'

const baseUrl = 'admin-system/dataDictionaryApi';

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
