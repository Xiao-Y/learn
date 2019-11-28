import Cookies from 'js-cookie'

const ACCESS_TOKEN = 'AccessToken';
const REFRESH_TOKEN = 'RefreshToken';
const CityData = 'CityData';

/**
 * 获取token
 * @returns {*}
 * @constructor
 */
export function getAccessToken() {
  // var accessToken = Cookies.get(ACCESS_TOKEN);
  var accessToken = localStorage.getItem(ACCESS_TOKEN);
  return accessToken
}

/**
 * 获取刷新token
 * @returns {*}
 * @constructor
 */
export function getRefresToken() {
  // var refreshToken = Cookies.get(REFRESH_TOKEN);
  var refreshToken = localStorage.getItem(REFRESH_TOKEN);
  return refreshToken
}

/**
 * 保存token
 * @returns {*}
 * @constructor
 */
export function setToken(token) {
  // Cookies.set(ACCESS_TOKEN, token.accessToken);
  // Cookies.set(REFRESH_TOKEN, token.refssToken);
  localStorage.setItem(ACCESS_TOKEN, token.accessToken);
  localStorage.setItem(REFRESH_TOKEN, token.refreshToken);
}

/**
 * 删除token
 * @returns {*}
 * @constructor
 */
export function removeToken() {
  localStorage.removeItem(ACCESS_TOKEN);
  localStorage.removeItem(REFRESH_TOKEN);
  // Cookies.remove(ACCESS_TOKEN);
  // Cookies.remove(REFRESH_TOKEN);
}

/**
 * 退出时，清理数据
 */
export function logOut() {
  localStorage.clear();
}

/**
 * 获取 CityData
 * @returns {*}
 * @constructor
 */
export function getCityData() {
  var cityData = localStorage.getItem(CityData);
  if (cityData) {
    return JSON.parse(cityData);
  }
  return cityData;
}

/**
 * 设置 CityData
 * @returns {*}
 * @constructor
 */
export function setCityData(cityData) {
  localStorage.setItem(CityData, JSON.stringify(cityData))
}
