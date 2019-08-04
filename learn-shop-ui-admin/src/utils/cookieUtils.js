import Cookies from 'js-cookie'

const AccessTokenKey = 'AccessToken';
const RefreshTokenKey = 'RefreshToken';
const CityData = 'CityData';

/**
 * 获取token
 * @returns {*}
 * @constructor
 */
export function getAccessToken() {
  var accessToken = Cookies.get(AccessTokenKey);
  return accessToken
}

/**
 * 获取刷新token
 * @returns {*}
 * @constructor
 */
export function getRefresToken() {
  var refreshToken = Cookies.get(RefreshTokenKey);
  return refreshToken
}

/**
 * 保存token
 * @returns {*}
 * @constructor
 */
export function setToken(token) {
  Cookies.set(AccessTokenKey, token.accessToken);
  Cookies.set(RefreshTokenKey, token.refreshToken);
}

/**
 * 删除token
 * @returns {*}
 * @constructor
 */
export function removeToken() {
  Cookies.remove(AccessTokenKey);
  Cookies.remove(RefreshTokenKey);
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
