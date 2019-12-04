import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/home/Home.vue'

Vue.use(Router)

// 静态路由
export const constantRouterMap = [{
  path: '/',
  redirect: '/login',
  hidden: true
}, {
  path: '/login',
  name: '登录页面',
  hidden: true,
  component: resolve => require(['../views/login/Login.vue'], resolve)
}, {
  path: '/error',
  name: 'error',
  component: Home,
  children: [{
    name: '404',
    path: '404',
    component: resolve => require(['../views/error/404.vue'], resolve)
  }]
}];

// 异步挂载的路由（动态需要根据权限加载的路由表,
// 子路由的 name = parentPath + childrenPath。必须要唯一，要与 sys_menu 中的 menu_code 一致，不然路由加载不出来）

export const asyncRouterMap = [{
  path: '/home',
  index: 'home',
  name: 'home',
  component: Home,
  children: [{
    path: 'index',
    name: 'homeIndex',
    component: resolve => require(['../views/home/HomeIndex.vue'], resolve)
  }]
}, {
  path: '/sys',
  name: 'sys',
  component: Home,
  children: [{
    name: 'sysWhiteListIndex',
    path: 'whiteList/index',
    component: resolve => require(['../views/sys/WhiteList.vue'], resolve)
  }, {
    name: 'sysMenuListIndex',
    path: 'menuList/index',
    component: resolve => require(['../views/sys/MenuList.vue'], resolve)
  }, {
    name: 'sysRoleListIndex',
    path: 'roleList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/RoleList.vue'], resolve)
  }, {
    name: 'sysRoleEdit',
    path: 'roleList/roleEdit',
    component: resolve => require(['../views/sys/components/RoleEdit.vue'], resolve)
  }, {
    name: 'sysPermissionListIndex',
    path: 'permissionList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/PermissionList.vue'], resolve)
  }, {
    name: 'sysPermissionEdit',
    path: 'permissionList/permissionEdit',
    component: resolve => require(['../views/sys/components/PermissionEdit.vue'], resolve)
  }, {
    name: 'sysCacheMagIndex',
    path: 'sysCacheMagIndex/index',
    component: resolve => require(['../views/sys/CacheMag.vue'], resolve)
  }, {
    name: 'sysMailTemplateListIndex',
    path: 'mailTemplateList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/MailTemplateList.vue'], resolve)
  }, {
    name: 'sysMailTemplateEdit',
    path: 'mailTemplateList/mailTemplateEdit',
    component: resolve => require(['../views/sys/components/MailTemplateEdit.vue'], resolve)
  }, {
    name: 'sysDataDictionaryList',
    path: 'dataDictionaryList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/DataDictionaryList.vue'], resolve)
  }, {
    name: 'sysDataDictionaryEdit',
    path: 'dataDictionaryList/dataDictionaryEdit',
    component: resolve => require(['../views/sys/components/DataDictionaryEdit.vue'], resolve)
  }]
}, {
  path: '/job',
  name: 'job',
  component: Home,
  children: [{
    name: 'jobAutoTaskListIndex',
    path: 'autoTaskList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/job/AutoTaskList.vue'], resolve)
  }, {
    name: 'jobAutoTaskEdit',
    path: 'autoTaskList/autoTaskEdit',
    component: resolve => require(['../views/job/components/AutoTaskEdit.vue'], resolve)
  }, {
    name: 'jobAutoTaskCharts',
    path: 'autoTaskList/autoTaskCharts',
    component: resolve => require(['../views/job/AutoTaskCharts.vue'], resolve)
  }]
}, {
  path: '/pro',
  name: 'pro',
  component: Home,
  children: [{
    name: 'proGoodsSpuListIndex',
    path: 'goodsSpuList/index',
    component: resolve => require(['../views/product/GoodsSpuList.vue'], resolve)
  }, {
    name: 'proGoodsSpuEdit',
    path: 'goodsSpuList/goodsSpuEdit',
    component: resolve => require(['../views/product/components/GoodsSpuEdit.vue'], resolve)
  }, {
    name: 'proGoodsSpuImageEdit',
    path: 'goodsSpuList/goodsSpuImageEdit',
    component: resolve => require(['../views/product/components/GoodsSpuImageEdit.vue'], resolve)
  }, {
    name: 'proGoodsCategoryListIndex',
    path: 'goodsCategoryList/index',
    component: resolve => require(['../views/product/GoodsCategoryList.vue'], resolve)
  }, {
    name: 'proGoodsCategoryEdit',
    path: 'goodsCategoryList/goodsCategoryEdit',
    component: resolve => require(['../views/product/components/GoodsCategoryEdit.vue'], resolve)
  }]
}, {
  path: '/user',
  name: 'user',
  component: Home,
  children: [{
    name: 'userUserListIndex',
    path: 'userList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/user/UserList.vue'], resolve)
  }, {
    name: 'userUserEdit',
    path: 'userList/userEdit',
    component: resolve => require(['../views/user/components/UserEdit.vue'], resolve)
  }]
}, {
  path: '/edit',
  name: 'edit',
  component: Home, children: [{
    name: 'editMarkdown',
    path: 'markdown',
    component: resolve => require(['../views/edit/Markdown.vue'], resolve)
  }]
}, {
  path: '/proc',
  name: 'proc',
  component: Home,
  children: [{
    name: 'procProcDeployListIndex',
    path: 'procDeployList/index',
    component: resolve => require(['../views/process/ProcDeployList.vue'], resolve)
  }, {
    name: 'procProcDefListIndex',
    path: 'procDefList/index',
    component: resolve => require(['../views/process/ProcDefList.vue'], resolve)
  }]
}, {
  path: '/workbench',
  name: 'workbench',
  component: Home,
  children: [{
    name: 'workbenchApplyList',
    path: 'workbenchApply/list',
    component: resolve => require(['../views/process/apply/applyList.vue'], resolve)
  }, {
    name: 'workbenchApplyInfo',
    path: 'workbenchApplyInfo/index',
    component: resolve => require(['../views/process/apply/applyInfo/applyInfo.vue'], resolve)
  },{
    name: 'workbenchMyTaskListIndex',
    path: 'workbenchMyTaskList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/process/MyTaskList.vue'], resolve)
  }]
}, {
  name: 'procViewProcessImg',
  path: '/procViewProcessImg', // 单独页面打开
  component: resolve => require(['../views/process/ViewProcessImg.vue'], resolve)
}]

export default new Router({
  routes: constantRouterMap
})
