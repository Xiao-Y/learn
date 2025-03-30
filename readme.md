# Learn Shop 微服务电商系统

## 项目简介
Learn Shop是一个基于Spring Cloud微服务架构的电商系统，采用前后端分离设计。系统包含商品管理、订单管理、购物车、促销、搜索等核心功能模块，以及统一认证、系统管理等基础服务模块。

## 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 6.0+
- RabbitMQ 3.8+
- Nacos 2.0+
- Node.js 14+ (前端开发)

## 项目结构
### 1. 基础服务
- `nacos` 注册中心，分布式配置中心 (端口：8761)
- `learn-cloud-gateway` 路由网关 (端口：8771)

### 2. 公用组件
- `learn-cloud-common` 配置中心配置文件，所有的 `learn-cloud-*` 都要依赖
- `learn-shop-base` 包含：
  - `learn-shop-base-aop` AOP切面组件
    - 统一数据格式返回
    - 统一异常处理
    - 日志打印
    - Long类型转String
  - `learn-shop-base-common` 公共组件
    - MQ配置（交换机、路由、队列）
    - Redis配置和操作工具
    - Swagger2配置
    - 线程池配置
    - 用户信息工具类
  - `learn-shop-base-email` 邮件服务组件
  - `learn-shop-base-job` 定时任务组件
  - `learn-shop-base-mybatis` MyBatis配置和代码生成
  - `learn-shop-base-tools` 通用工具类
  - `learn-shop-base-workflow` 工作流组件
  - `learn-shop-base-notice` 消息通知组件

### 3. 业务服务
- 管理后台服务 (端口：88xx)
  - `learn-shop-admin-user` 用户管理服务 (8801)
  - `learn-shop-admin-system` 系统管理服务 (8811)

- 核心业务服务 (端口：89xx)
  - `learn-shop-core-order` 订单服务 (8901)
  - `learn-shop-core-cart` 购物车服务
  - `learn-shop-core-product` 商品服务 (8911)
  - `learn-shop-core-search` 搜索服务 (8981)
  - `learn-shop-core-promotion` 促销服务 (8921)

- 其他服务
  - `learn-shop-app` APP端服务 (8089)
  - `learn-shop-public-auth` 认证服务

### 4. 前端项目
- `learn-shop-ui-admin` 后台管理前端
  - 基于Vue 3.x + Element Plus的中后台解决方案
  - 包含系统管理、商品管理、订单管理等功能
  - 详细开发规范请参考 project-rule.md

- `learn-shop-ui-app` 移动端应用
  - 基于Vue 3.x + Vant的移动端商城系统
  - 包含商品浏览、购物车、订单管理等功能
  - 详细开发规范请参考 project-rule.md

## 项目配置

### 1. 配置中心设置
- 采用Nacos作为配置中心和路由中心
- 配置文件路径：`nacos--->dev-->cloud-config.properties`
- 依赖模块：`learn-cloud-common`

### 2. 本地开发配置
如需使用本地配置文件：
1. 修改`learn-cloud-common/resources/bootstrap.yml`
2. 更新`search-locations`为本地路径

### 3. 新服务配置
添加新服务时需要：
1. 在`learn-cloud-gateway`中添加路由配置
2. 添加相应依赖

## 环境搭建

### 1. RabbitMQ配置
```shell
# 启动服务
rabbitmq-server.bat

# 添加管理员用户
rabbitmqctl.bat add_user admin admin123
rabbitmqctl.bat set_user_tags admin administrator

# 查看用户列表
rabbitmqctl.bat list_users

# 设置虚拟主机
# 在管理界面设置virtual-host为/learn-default
```

### 2. Redis配置
- 启动Redis服务
- 注意：项目使用两个Redis库
  ```java
  // 普通数据存储（默认库）
  @Resource
  private RedisTemplate<String, Object> redisTemplate;

  // MyBatis缓存（15号库）
  @Resource
  protected RedisTemplate<String, Object> redisCacheTemplate;
  ```

```shell
// 链路跟踪
-javaagent:D:\docker\SkyWalkIng\skywalking-agent\skywalking-agent.jar -Dskywalking.agent.service_name=learn-shop-core-product -Dskywalking.collector.backend_service=127.0.0.1:11800
```

### 3. 链路追踪配置
```shell
# 启动参数
-javaagent:D:\docker\SkyWalkIng\skywalking-agent\skywalking-agent.jar
-Dskywalking.agent.service_name=learn-shop-core-product
-Dskywalking.collector.backend_service=127.0.0.1:11800
```

## 项目启动

### 1. 启动顺序
1. 启动MySQL
2. 启动Redis
3. 启动RabbitMQ
4. 启动Nacos（单机模式：`./startup.sh -m standalone`）
5. 启动网关服务（learn-cloud-gateway）
6. 启动业务服务

### 2. 首次启动配置
1. 修改`learn-shop-admin-system.yml`：
   - 设置`database-schema-update: true`（自动创建工作流表）
   - 设置`start-init-data: true`（初始化菜单和权限）

## 服务访问

### 1. 管理入口
- Nacos控制台：http://localhost:8761/nacos/index.html
  - 用户名/密码：nacos/nacos

- RabbitMQ管理界面：http://localhost:15672
  - 用户名/密码：admin/admin123

### 2. 业务服务
所有服务通过网关访问：
- 订单服务：http://localhost:8771/core-order
- 用户服务：http://localhost:8771/admin-user

### 3. API文档
- 单个服务文档：http://localhost:<port>/swagger-ui.html
- 网关聚合文档：http://localhost:8771/swagger-ui.html

## 开发规范
详细的开发规范请参考 project-rule.md 文件，包括：
- 代码规范
- 接口规范
- 数据库规范
- 前端开发规范
- 安全规范
- 部署规范

## TODO清单
1. 缓存优化
   - [ ] 统一缓存策略
   - [ ] 添加缓存预热
   - [ ] 优化缓存更新机制

2. 订单系统优化
   - [ ] 实现订单自动释放
   - [ ] 添加订单状态机
   - [ ] 优化订单处理流程

3. 权限系统升级
   - [ ] 调整为权限码形式
   - [ ] 完善权限粒度
   - [ ] 添加数据权限

4. UI优化
   - [ ] 后台管理首页改为卡片布局
   - [ ] 优化移动端适配
   - [ ] 添加数据可视化

## 常见问题
1. 配置中心启动异常
   - 现象：配置中心启动时报注册中心连接异常
   - 解决：属于正常现象，待注册中心启动后会自动恢复

2. Redis连接问题
   - 现象：Redis连接报错
   - 解决：检查Redis服务是否启动，以及配置文件中的连接信息是否正确

## 贡献指南
1. Fork 项目
2. 创建功能分支
3. 提交代码
4. 发起Pull Request

## 版权说明
Copyright © 2024 Billow

## 前端开发指南

### 1. 项目说明
项目包含两个前端应用：
- `learn-shop-ui-admin`：后台管理系统，基于Element Plus的中后台解决方案
- `learn-shop-ui-app`：移动端应用，基于Vant的移动端商城系统

### 2. 技术栈

#### 2.1 后台管理系统
- 核心框架：Vue 3.x
- UI框架：Element Plus
- 状态管理：Pinia
- 构建工具：Vite
- HTTP客户端：Axios
- 路由管理：Vue Router
- 代码规范：ESLint + Prettier
- CSS预处理器：Sass/SCSS
- 工具库：
  - Lodash：工具函数库
  - Day.js：日期处理
  - ECharts：图表可视化
  - wangEditor：富文本编辑器

#### 2.2 移动端应用
- 核心框架：Vue 3.x
- UI框架：Vant
- 状态管理：Pinia
- 构建工具：Vite
- 移动端特性：
  - Viewport适配
  - Touch事件处理
  - 移动端手势库
  - 终端检测工具

### 3. 项目结构

#### 3.1 后台管理系统
```
learn-shop-ui-admin/
├── src/
│   ├── api/                # API接口定义
│   ├── assets/             # 静态资源
│   ├── components/         # 公共组件
│   │   ├── common/         # 基础组件
│   │   └── business/       # 业务组件
│   ├── composables/        # 组合式函数
│   ├── layouts/            # 布局组件
│   ├── router/             # 路由配置
│   ├── stores/             # 状态管理
│   ├── styles/             # 全局样式
│   ├── utils/              # 工具函数
│   └── views/              # 页面组件
├── .env                    # 环境变量
└── vite.config.js         # Vite配置
```

#### 3.2 移动端应用
```
learn-shop-ui-app/
├── src/
│   ├── api/              # API接口
│   ├── assets/          # 静态资源
│   ├── components/      # 公共组件
│   ├── hooks/           # 组合式函数
│   ├── pages/          # 页面组件
│   │   ├── home/       # 首页模块
│   │   ├── cart/       # 购物车模块
│   │   ├── user/       # 用户中心
│   │   └── product/    # 商品模块
│   ├── stores/         # 状态管理
│   ├── styles/         # 样式文件
│   └── utils/          # 工具函数
├── .env.*              # 环境配置
└── vite.config.js      # 构建配置
```

### 4. 开发指南

#### 4.1 后台管理系统

1. 环境准备
```bash
# 进入后台管理系统目录
cd learn-shop-ui-admin

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build
```

2. 主要功能模块
- 系统管理
  - 用户管理
  - 角色管理
  - 菜单管理
  - 字典管理
  - 系统配置
- 商品管理
- 订单管理
- 促销管理
- 统计分析

3. 自定义组件
```vue
<!-- 查询按钮组 -->
<button-group-query
  :show-add="true"
  :show-query="true"
  :query-filter="queryParams"
  has-add="sys:user:add"
  @onAdd="handleAdd"
  @onQuery="handleQuery"
/>

<!-- 自定义下拉选择器 -->
<custom-select
  v-model="selectedValue"
  :datasource="options"
  :field-type="'USER_TYPE'"
  :system-module="'system'"
  @onchange="handleChange"
/>

<!-- 菜单树组件 -->
<custom-menu-tree
  v-model="selectedMenus"
  :tree-data="menuData"
  :default-expand-all="true"
  @node-click="handleNodeClick"
/>

<!-- 图标选择器 -->
<custom-icon
  v-model="selectedIcon"
  :disabled="false"
  @change="handleIconChange"
/>

<!-- Cron表达式输入器 -->
<custom-cron-input
  v-model="cronExpression"
  :show-button="true"
  @change="handleCronChange"
/>

<!-- 分页组件 -->
<custom-page
  v-model:current="pageInfo.current"
  v-model:size="pageInfo.size"
  :total="pageInfo.total"
  @size-change="handleSizeChange"
  @current-change="handleCurrentChange"
/>

<!-- SKU规格选择器 -->
<custom-sku-spec-select
  v-model="selectedSpecs"
  :spec-list="specList"
  @change="handleSpecChange"
/>

<!-- 邮件模板选择器 -->
<custom-sel-mail-template
  v-model="selectedTemplate"
  :template-type="'NOTICE'"
  @change="handleTemplateChange"
/>
```

#### 4.2 移动端应用

1. 环境准备
```bash
# 进入移动端应用目录
cd learn-shop-ui-app

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 构建生产版本
npm run build
```

2. 主要功能模块
- 首页展示
- 商品浏览
- 购物车
- 订单管理
- 用户中心

3. 移动端适配
```scss
// styles/variables.scss
:root {
  // 主题颜色
  --primary-color: #1989fa;
  --success-color: #07c160;
  
  // 文字大小
  --font-size-xs: 10px;
  --font-size-sm: 12px;
  --font-size-md: 14px;
  --font-size-lg: 16px;
}
```

### 5. 部署说明

#### 5.1 后台管理系统
1. 构建配置
```js
// vite.config.js
export default defineConfig({
  build: {
    minify: 'terser',
    rollupOptions: {
      output: {
        manualChunks: {
          'vendor': ['vue', 'pinia', 'element-plus']
        }
      }
    }
  }
})
```

2. Nginx配置
```nginx
location /admin {
  try_files $uri $uri/ /index.html;
  root /usr/share/nginx/html/admin;
  index index.html;
}
```

#### 5.2 移动端应用
1. 构建配置
```js
// vite.config.js
export default defineConfig({
  build: {
    minify: 'terser',
    rollupOptions: {
      output: {
        manualChunks: {
          'vendor': ['vue', 'pinia', 'vant']
        }
      }
    }
  }
})
```

2. Nginx配置
```nginx
location /app {
  try_files $uri $uri/ /index.html;
  root /usr/share/nginx/html/app;
  index index.html;
}
```

### 6. 常见问题

#### 6.1 后台管理系统
1. 跨域问题
   - 开发环境：配置vite.config.js的proxy
   - 生产环境：配置nginx反向代理

2. 权限问题
   - 菜单权限：基于RBAC模型
   - 按钮权限：使用v-has指令

#### 6.2 移动端应用
1. 适配问题
   - iOS安全区适配
   - 键盘弹出处理
   - 横屏限制

2. 性能问题
   - 首屏加载优化
   - 图片懒加载
   - 列表虚拟滚动
