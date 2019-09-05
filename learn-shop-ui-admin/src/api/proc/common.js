// 定义申请页面映射
const apply = new Map();
apply.set("leave", "procApplyLeave");

/**
 * 获取申请页面
 * @param key
 * @returns {V}
 * @constructor
 */
export const FindApplyPage = (key) => {
  return apply.get(key);
};

/**
 * 根据id获取流程部署图片
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ViewExecutionImgById = id => {
  return baseUrl + '/viewExecutionImgById/' + id;
};

/**
 * 根据id获取流程部署图片
 * @param id
 * @returns {*|AxiosPromise}
 * @constructor
 */
export const ViewDeployImgById = id => {
  return baseUrl + '/viewDeployImgById/' + id;
};
