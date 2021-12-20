const getters = {
  // language: state => state.app.language,

  token: state => state.loginHandle.token,
  avatar: state => state.loginHandle.avatar,
  roles: state => state.loginHandle.roles,
  name: state => state.loginHandle.name,

  // name: state => state.user.name,
  routers: state => state.menuPerm.routers,
  addRouters: state => state.menuPerm.addRouters,
  menus: state => state.menuPerm.menus,
  // 按钮权限
  buttonPerm: state => state.buttonPerm.permissionCodes
}
export default getters
