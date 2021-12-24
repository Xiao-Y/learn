// import Home from "../views/home/Home";
//
// /**
//  * 固定路由
//  */
// export const constantRouterMap = [{
//   path: '/',
//   redirect: '/login',
//   hidden: true
// }, {
//   path: '/login',
//   name: '登录页面',
//   hidden: true,
//   component: resolve => require(['../views/login/Login.vue'], resolve)
// }, {
//   path: '/error',
//   name: 'error',
//   component: Home,
//   children: [{
//     name: '404',
//     path: '404',
//     component: resolve => require(['../views/error/404.vue'], resolve)
//   }]
// }];
//
// export default constantRouterMap
