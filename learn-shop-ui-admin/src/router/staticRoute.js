const staticRoute = [{
  path: '/',
  redirect: '/login'
}, {
  path: '/error',
  component: () => import ( /* webpackChunkName: 'error' */ '../page/error'),
  children: [{
    path: '401',
    component: () => import ( /* webpackChunkName: 'error' */ '../page/error/401')
  }, {
    path: '403',
    component: () => import ( /* webpackChunkName: 'error' */ '../page/error/403')
  }, {
    path: '404',
    component: () => import ( /* webpackChunkName: 'error' */ '../page/error/404')
  }, {
    path: '500',
    component: () => import ( /* webpackChunkName: 'error' */ '../page/error/500')
  }]
}, {
  path: '/login',
  component: () => import ( /* webpackChunkName: 'login' */ '../page/login')
}, {
  path: '*',
  redirect: '/error/404'
}]

export default staticRoute
