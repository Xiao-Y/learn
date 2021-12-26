const getters = {
  // language: state => state.app.language,

  token: state => state.loginHandle.token,
  avatar: state => state.loginHandle.avatar,
  roles: state => state.loginHandle.roles,
  name: state => state.loginHandle.name,

  // 追加新路由
  addRouters: state => state.menuPerm.addRouters,
  menus: state => state.menuPerm.menus,
  // 按钮权限
  buttonPerm: state => state.buttonPerm.permissionCodes
}
export default getters
