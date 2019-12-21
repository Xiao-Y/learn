import requestUtils from '../utils/requestUtils'


const baseUrl = '../public-auth';

export const login = (username, password) => requestUtils.post(baseUrl + '/login', {
  username: username,
  password: password
});

export const logout = () => requestUtils.post(baseUrl + '/logout');

export const DataRecovery = () => requestUtils.get('../dataRecovery/initData');
