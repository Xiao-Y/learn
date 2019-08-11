import requestUtils from '../utils/requestUtils'


const baseUrl = 'public-auth';

export const login = (username, password) => requestUtils.post(baseUrl + '/login', {
  username: username,
  password: password
});

// export const login = function(username, password){
//   const params = new URLSearchParams();
//   params.append('username', username);
//   params.append('password', password);
//
//   request.post(baseUrl + '/authentication/form', params);
// }

export const logout = () => requestUtils.post(baseUrl + '/logout');

export const DataRecovery = () => requestUtils.get('dataRecovery/initData');
