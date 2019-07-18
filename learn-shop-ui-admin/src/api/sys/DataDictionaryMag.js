import request from '@/utils/request'

const baseUrl = 'admin-system/dataDictionaryApi';

/**
 * 查询 system 系统指定的 FieldType 的字典
 * @param roleFilter
 * @constructor
 */
export function LoadSysDataDictionary(fieldType) {
  return request({
    url: baseUrl + '/findDataDictionary/adminSystem/' + fieldType,
    method: 'get'
  })
}
