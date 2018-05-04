import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

const router = new VueRouter({
  mode: 'history',
  routes: staticRoute
});

// router为vue-router路由对象
router.beforeEach((to, from, next) => {
  // ajax获取权限列表函数
  // 这里省略了一些判断条件，比如判断是否已经拥有了权限数据等
  getPermission().then(res => {
    let isPermission = false

    permissionList.forEach((v) => {
      // 判断跳转的页面是否在权限列表中
      if (v.path == to.fullPath) {
        isPermission = true
      }
    });

    // 没有权限时跳转到401页面
    if (!isPermission) {
      next({path: "/error/401", replace: true})
    } else {
      next()
    }
  });

});

export default router
