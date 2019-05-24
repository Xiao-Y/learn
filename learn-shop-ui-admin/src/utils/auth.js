import Cookies from 'js-cookie'
const AccessTokenKey = 'AccessToken'
const RefreshTokenKey = 'RefreshToken'

export function getAccessToken() {
  var accessToken = Cookies.get(AccessTokenKey);
  return accessToken
}

export function getRefresToken() {
  var refreshToken = Cookies.get(RefreshTokenKey);
  return refreshToken
}

export function setToken(token) {
  Cookies.set(AccessTokenKey, token.accessToken);
  Cookies.set(RefreshTokenKey, token.refreshToken);
}

export function removeToken() {
  Cookies.remove(AccessTokenKey);
  Cookies.remove(RefreshTokenKey);
}
