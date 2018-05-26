const getters = {
  language: state => state.app.language,

  token: state => state.user.token,
  name: state => state.user.name,
  avatar: state => state.user.avatar,
  roles: state => state.user.roles,

  routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,
  menus: state => state.permission.menus
}
export default getters
