/**
 * asyncRouterList 转化为 map 方便取值
 * @returns {Map<any, any>} key-路由的名称，value-路由对象
 */
export function asyncRouterMap() {
  let routerMap = new Map();
  asyncRouterList.forEach((data) => {
    routerMap.set(data.name, data);
  });
  return routerMap;
}

/**
 * 异步挂载的路由（动态需要根据权限加载的路由表, 路由的 name  必须要唯一，要与 sys_menu 中的 menu_code 一致，不然路由加载不出来）
 * @type {any}
 */
const asyncRouterList = [
  // ===================================== 系统管理 ================================
  {
    // 白名单
    name: 'sysWhiteListIndex',
    path: '/sys/whiteList/index',
    component: resolve => require(['../views/sys/WhiteList.vue'], resolve)
  }, {
    // 菜单管理
    name: 'sysMenuListIndex',
    path: '/sys/menuList/index',
    component: resolve => require(['../views/sys/MenuList.vue'], resolve)
  }, {
    // 角色管理
    name: 'sysRoleListIndex',
    path: '/sys/roleList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/RoleList.vue'], resolve)
  }, {
    // 修改角色信息
    name: 'sysRoleEdit',
    path: '/sys/roleList/roleEdit',
    component: resolve => require(['../views/sys/components/RoleEdit.vue'], resolve)
  }, {
    // 权限管理
    name: 'sysPermissionListIndex',
    path: '/sys/permissionList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/PermissionList.vue'], resolve)
  }, {
    // 修改权限信息
    name: 'sysPermissionEdit',
    path: '/sys/permissionList/permissionEdit',
    component: resolve => require(['../views/sys/components/PermissionEdit.vue'], resolve)
  }, {
    // 缓存管理
    name: 'sysCacheMagIndex',
    path: '/sys/sysCacheMagIndex/index',
    component: resolve => require(['../views/sys/CacheMag.vue'], resolve)
  }, {
    // 邮件模板管理
    name: 'sysMailTemplateListIndex',
    path: '/sys/mailTemplateList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/MailTemplateList.vue'], resolve)
  }, {
    // 邮件模板修改
    name: 'sysMailTemplateEdit',
    path: '/sys/mailTemplateList/mailTemplateEdit',
    component: resolve => require(['../views/sys/components/MailTemplateEdit.vue'], resolve)
  }, {
    // 数据字典
    name: 'sysDataDictionaryList',
    path: '/sys/dataDictionaryList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/sys/DataDictionaryList.vue'], resolve)
  }, {
    // 修改数据字典
    name: 'sysDataDictionaryEdit',
    path: '/sys/dataDictionaryList/dataDictionaryEdit',
    component: resolve => require(['../views/sys/components/DataDictionaryEdit.vue'], resolve)
  },
  // ================================== 自动任务管理 ============================================
  {
    // 自动任务
    name: 'jobAutoTaskListIndex',
    path: '/job/autoTaskList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/job/AutoTaskList.vue'], resolve)
  }, {
    // 修改自动任务
    name: 'jobAutoTaskEdit',
    path: '/job/autoTaskList/autoTaskEdit',
    component: resolve => require(['../views/job/components/AutoTaskEdit.vue'], resolve)
  }, {
    // 任务概览
    name: 'jobAutoTaskCharts',
    path: '/job/autoTaskList/autoTaskCharts',
    component: resolve => require(['../views/job/AutoTaskCharts.vue'], resolve)
  },
  // ======================================== 产品管理 ====================================
  {
    // 产品管理
    name: 'proGoodsSpuListIndex',
    path: '/pro/goodsSpuList/index',
    component: resolve => require(['../views/product/GoodsSpuList.vue'], resolve)
  }, {
    // 修改产品信息
    name: 'proGoodsSpuEdit',
    path: '/pro/goodsSpuList/goodsSpuEdit',
    component: resolve => require(['../views/product/components/GoodsSpuEdit.vue'], resolve)
  }, {
    // 修改产品图片
    name: 'proGoodsSpuImageEdit',
    path: '/pro/goodsSpuList/goodsSpuImageEdit',
    component: resolve => require(['../views/product/components/GoodsSpuImageEdit.vue'], resolve)
  }, {
    // 产品分类
    name: 'proGoodsCategoryListIndex',
    path: '/pro/goodsCategoryList/index',
    component: resolve => require(['../views/product/GoodsCategoryList.vue'], resolve)
  }, {
    // 修改产品分类
    name: 'proGoodsCategoryEdit',
    path: '/pro/goodsCategoryList/goodsCategoryEdit',
    component: resolve => require(['../views/product/components/GoodsCategoryEdit.vue'], resolve)
  },
  // ================================= 用户管理 ==============================================
  {
    // 用户管理
    name: 'userUserListIndex',
    path: '/user/userList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/user/UserList.vue'], resolve)
  }, {
    // 用户修改/添加
    name: 'userUserEdit',
    path: '/user/userList/userEdit',
    component: resolve => require(['../views/user/components/UserEdit.vue'], resolve)
  },
  // ======================================= 内容管理 ==============================
  {
    // 编辑管理
    name: 'editMarkdown',
    path: '/edit/markdown',
    component: resolve => require(['../views/edit/Markdown.vue'], resolve)
  },
  // ======================================= 流程管理 ================================
  {
    // 流程部署
    name: 'procProcDeployListIndex',
    path: '/proc/procDeployList/index',
    component: resolve => require(['../views/process/ProcDeployList.vue'], resolve)
  }, {
    // 流程定义
    name: 'procProcDefListIndex',
    path: '/proc/procDefList/index',
    component: resolve => require(['../views/process/ProcDefList.vue'], resolve)
  }, {
    // 流程跟踪查询(单独页面打开)
    name: 'procViewProcessImg',
    path: '/procViewProcessImg',
    component: resolve => require(['../views/process/ViewProcessImg.vue'], resolve)
  },
  // ================================ 我的工作台 ====================================
  {
    // 我要申请
    name: 'workbenchApplyList',
    path: '/workbench/workbenchApply/list',
    component: resolve => require(['../views/process/apply/applyList.vue'], resolve)
  }, {
    // 申请信息
    name: 'workbenchApplyInfo',
    path: '/workbench/workbenchApplyInfo/index',
    component: resolve => require(['../views/process/apply/applyInfo/applyInfo.vue'], resolve)
  }, {
    // 我的任务
    name: 'workbenchMyTaskListIndex',
    path: '/workbench/workbenchMyTaskList/index',
    meta: {
      keepAlive: true
    },
    component: resolve => require(['../views/process/MyTaskList.vue'], resolve)
  }
]

