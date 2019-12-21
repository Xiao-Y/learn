import requestUtils from '../../utils/requestUtils'

const baseUrl = '../admin-system/mailTemplateApi';

/**
 * 根据条件查询邮件模板信息
 * @param mailTemplateFilter
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const LoadDataMailTemplateList = mailTemplateFilter => requestUtils.post(baseUrl + '/findMailTemplateList', Object.assign(mailTemplateFilter));

/**
 * 保存邮件模板信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SaveMailTemplate = data => requestUtils.post(baseUrl + '/saveMailTemplate', Object.assign(data));

/**
 * 更新邮件模板信息
 * @param data
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const UpdateMailTemplate = data => requestUtils.put(baseUrl + '/updateMailTemplate', Object.assign(data));

/**
 * 根据id删除邮件模板信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const DeleteMailTemplateById = id => requestUtils.del(baseUrl + '/deleteMailTemplateById/' + id);

/**
 * 根据id禁用邮件模板信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ProhibitMailTemplateById = id => requestUtils.put(baseUrl + '/prohibitMailTemplateById/' + id);

/**
 * 根据id获取邮件模板信息
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const FindMailTemplateById = id => requestUtils.get(baseUrl + '/findMailTemplateById/' + id);

/**
 * 查询 mailCode 的个数
 * @param userCode
 * @constructor
 */
export const CheckMailCode = mailCode => requestUtils.get(baseUrl + '/checkMailCode/' + mailCode);
