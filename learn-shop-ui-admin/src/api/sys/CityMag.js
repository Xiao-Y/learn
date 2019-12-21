import requestUtils from '../../utils/requestUtils'
  import {
    getCityData,
    setCityData
  } from '../../utils/cookieUtils';

const baseUrl = '../admin-system/cityApi';

/**
 * 查询省市区树形结构
 */
export const LoadCityData = ()=> {
  // 先从本地获取，如果没有再去查询数据库
  if(getCityData()){
    return new Promise((resolve, reject) => {
      resolve(getCityData());
    });
  }
  // 查询数据库
  return new Promise((resolve, reject) => {
    requestUtils.get(baseUrl + '/findCity/100000').then(res=>{
      setCityData(res.resData);
      resolve(res.resData);
    }).catch(res => {
        reject('查询省市区异常');
      })
  });
};
