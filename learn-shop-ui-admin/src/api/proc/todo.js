import requestUtils from '../../utils/requestUtils'

const baseUrlLeave = 'admin-system/leaveApi';

/**
 * 提交请假申请
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const SubmitLeave = (leaveInfo) => requestUtils.post(baseUrlLeave + '/submitLeave', Object.assign(leaveInfo));

/**
 * 查询请假申请
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const FindLeaveById = (id) => requestUtils.get(baseUrlLeave + '/findLeaveById/' + id);
