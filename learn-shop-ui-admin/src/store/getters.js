const getters = {
  // language: state => state.app.language,

  token: state => state.loginHandle.token,
  avatar: state => state.loginHandle.avatar,
  roles: state => state.loginHandle.roles,
  name: state => state.loginHandle.name,

  // name: state => state.user.name,

  routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
  menus: state => state.permission.menus
}
export default getters
