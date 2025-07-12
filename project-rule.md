# Learn项目开发规范

## 1. 项目架构

### 1.1 技术栈
- 注册中心/配置中心：Nacos
- 网关：Spring Cloud Gateway
- 数据库：MySQL
- 缓存：Redis
- 消息队列：RabbitMQ
- 链路追踪：SkyWalking
- ORM：MyBatis
- 文档：Swagger2
- 工具库：Hutool、Lombok

### 1.2 项目结构
```
learn/
├── learn-cloud-common/          # 配置中心公共模块：存放公共配置、工具类等
├── learn-cloud-gateway/         # 网关服务(端口:8771)：统一入口、路由转发、鉴权等
├── learn-shop-base/            # 基础组件
│   ├── learn-shop-base-aop/         # AOP组件：统一返回、统一异常、日志等
│   ├── learn-shop-base-common/      # 公共组件：MQ、Redis、Swagger2配置等
│   ├── learn-shop-base-email/       # 邮件组件：邮件模板配置、发送服务等
│   ├── learn-shop-base-excel/       # Excel组件：Excel导入导出、模板管理等
│   ├── learn-shop-base-job/         # 定时任务：Quartz配置、任务调度等
│   ├── learn-shop-base-jpa/         # JPA配置（已弃用）：原JPA持久层配置
│   ├── learn-shop-base-mybatis/     # MyBatis配置：数据库配置、代码生成等
│   ├── learn-shop-base-notice/      # 通知组件：站内信、消息推送等
│   ├── learn-shop-base-redis/       # Redis组件：缓存配置、分布式锁等
│   ├── learn-shop-base-security/    # 安全组件：认证授权、安全配置等
│   ├── learn-shop-base-tools/       # 工具组件：通用工具类、辅助方法等
│   └── learn-shop-base-workflow/    # 工作流组件：Activiti工作流引擎配置
├── learn-shop-admin-*/         # 后台管理服务(端口:88**)
│   ├── learn-shop-admin-system/     # 系统管理服务(8811)：系统配置、菜单、权限等
│   └── learn-shop-admin-user/       # 用户管理服务(8801)：用户、角色、部门等
├── learn-shop-core-*/          # 核心业务服务(端口:89**)
│   ├── learn-shop-core-cart/        # 购物车服务：购物车管理、商品选择等
│   ├── learn-shop-core-order/       # 订单服务(8901)：订单管理、支付等
│   ├── learn-shop-core-product/     # 商品服务(8911)：商品管理、分类、规格等
│   ├── learn-shop-core-promotion/   # 促销服务(8921)：优惠券、活动、秒杀等
│   └── learn-shop-core-search/      # 搜索服务(8981)：ES搜索、索引管理等
├── learn-shop-public-*/        # 公共服务
│   └── learn-shop-public-auth/      # 认证服务：OAuth2认证、JWT令牌等
├── learn-shop-app/             # APP端服务(8089)：移动端API接口
├── learn-shop-interface/       # 接口模块：微服务间Feign接口定义
├── learn-shop-ui-*/           # 前端项目
│   ├── learn-shop-ui-admin/         # 后台管理前端：Vue.js + Element UI
│   └── learn-shop-ui-app/           # APP端前端：移动端H5界面
├── doc/                       # 项目文档：设计文档、API文档等
├── pom.xml                    # 项目依赖管理
├── .gitignore                 # Git忽略文件配置
├── LICENSE                    # 开源协议
└── README.md                  # 项目说明文档
```

各类微服务模块的标准目录结构：

1. 核心业务服务模块（如learn-shop-core-cart、learn-shop-core-order、learn-shop-core-product、learn-shop-core-promotion、learn-shop-core-search）：
```
learn-shop-core-{service}/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/billow/{service}/
│   │   │       ├── api/           # 后端页面API接口
│   │   │       ├── app/           # APP端相关接口
│   │   │       ├── cache/         # 缓存相关处理
│   │   │       ├── common/        # 公共工具类
│   │   │       ├── config/        # 配置类
│   │   │       ├── consumer/       # MQ消费者
│   │   │       ├── dao/          # 数据访问层
│   │   │       ├── feign/        # 远程服务提供者
│   │   │       ├── remote/       # 远程服务消费者
│   │   │       ├── job/          # 定时任务
│   │   │       ├── pojo/         # 实体类
│   │   │       │   ├── build/    # 新增对象
│   │   │       │   ├── ex/       # 扩展对象
│   │   │       │   ├── excel/    # excel导入导出对象
│   │   │       │   ├── search/   # 查询对象 继承 BasePag
│   │   │       │   ├── vo/       # 视图对象
│   │   │       │   └── po/       # 数据库实体
│   │   │       ├── receive/      # 消息接收处理
│   │   │       └── service/      # 业务逻辑层
│   │   │           └── impl/     # 接口实现类
│   │   └── resources/
│   │       ├── mapper/          # MyBatis映射文件
│   │       ├── application.yml  # 应用配置
│   │       └── bootstrap.yml    # 启动配置
│   └── test/                    # 单元测试
├── pom.xml                      # 模块依赖
└── README.md                    # 模块说明
```

3. 管理服务模块（如learn-shop-admin-system、learn-shop-admin-user）：
```
learn-shop-admin-{service}/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/billow/{service}/
│   │   │       ├── api/           # 后端页面API接口
│   │   │       ├── app/           # APP端相关接口
│   │   │       ├── cache/         # 缓存相关处理
│   │   │       ├── common/        # 公共工具类
│   │   │       ├── config/        # 配置类
│   │   │       ├── consumer/       # MQ消费者
│   │   │       ├── dao/          # 数据访问层
│   │   │       ├── feign/        # 远程服务提供者
│   │   │       ├── remote/       # 远程服务消费者
│   │   │       ├── job/          # 定时任务
│   │   │       ├── pojo/         # 实体类
│   │   │       │   ├── build/    # 新增对象
│   │   │       │   ├── ex/       # 扩展对象
│   │   │       │   ├── excel/    # excel导入导出对象
│   │   │       │   ├── search/   # 查询对象 继承 BasePag
│   │   │       │   ├── vo/       # 视图对象
│   │   │       │   └── po/       # 数据库实体
│   │   │       ├── receive/      # 消息接收处理
│   │   │       └── service/      # 业务逻辑层
│   │   │           └── impl/     # 接口实现类
│   │   └── resources/
│   │       ├── mapper/          # MyBatis映射文件
│   │       ├── application.yml  # 应用配置
│   │       └── bootstrap.yml    # 启动配置
│   └── test/                    # 单元测试
├── pom.xml                      # 模块依赖
└── README.md                    # 模块说明
```

4. 基础组件模块结构：
```
learn-shop-base-{component}/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/billow/base/
│   │   │       ├── config/        # 配置类
│   │   │       ├── constant/      # 常量定义
│   │   │       ├── handler/       # 处理器
│   │   │       ├── properties/    # 配置属性
│   │   │       ├── util/          # 工具类
│   │   │       └── service/       # 服务接口
│   │   │           └── impl/      # 接口实现
│   │   └── resources/
│   │       └── META-INF/
│   │           └── spring.factories  # 自动配置
│   └── test/                        # 单元测试
├── pom.xml                          # 模块依赖
└── README.md                        # 模块说明
```

注意：
1. 不同类型的微服务模块可能有特定的包结构，上述结构仅供参考
2. 可以根据实际业务需求增减目录结构
3. 所有模块都必须包含基本的pom.xml和README.md文件
4. 每个模块的README.md需要包含模块的功能说明、使用方法等

5. 前端项目目录结构：

后台管理前端项目(learn-shop-ui-admin)：
```
learn-shop-ui-admin/
├── build/                # 构建相关配置
├── config/              # 项目配置文件
├── src/                # 源代码
│   ├── api/            # 接口请求
│   ├── components/     # 公共组件
│   ├── directives/     # 自定义指令
│   ├── global/         # 全局配置
│   ├── lang/           # 国际化语言包
│   ├── router/         # 路由配置
│   ├── store/          # Vuex状态管理
│   ├── utils/          # 工具函数
│   ├── views/          # 页面视图组件
│   ├── static/         # 静态资源
│   ├── App.vue         # 根组件
│   └── main.js         # 入口文件
├── public/             # 静态资源
│   ├── index.html      # HTML模板
│   └── favicon.ico     # 网站图标
├── .babelrc           # Babel配置
├── .editorconfig      # 编辑器配置
├── .eslintrc.js       # ESLint配置
├── .gitignore         # Git忽略文件
├── package.json       # 项目依赖
└── README.md          # 项目说明
```

移动端前端项目(learn-shop-ui-app)：
```
learn-shop-ui-app/
├── public/             # 静态资源
│   ├── index.html      # HTML模板
│   └── favicon.ico     # 网站图标
├── src/                # 源代码
│   ├── api/            # 接口请求
│   ├── assets/         # 项目资源
│   ├── components/     # 公共组件
│   ├── filter/         # 全局过滤器
│   ├── router/         # 路由配置
│   ├── store/          # Vuex状态管理
│   ├── utils/          # 工具函数
│   ├── views/          # 页面视图组件
│   ├── App.vue         # 根组件
│   └── main.js         # 入口文件
├── .eslintrc.js        # ESLint配置
├── .gitignore          # Git忽略文件
├── babel.config.js     # Babel配置
├── package.json        # 项目依赖
├── vue.config.js       # Vue CLI配置
└── README.md           # 项目说明
```

前端项目开发注意事项：
1. 后台管理系统使用Vue.js + Element UI开发
2. 移动端使用Vue.js + Vant UI开发
3. 组件命名采用大驼峰命名法（PascalCase）
4. 页面组件放在views目录，公共组件放在components目录
5. API接口按照业务模块进行分类
6. 静态资源（图片、字体等）放在assets目录
7. 工具函数放在utils目录，并做好分类
8. 路由配置要求：
   - 按照业务模块划分路由
   - 使用路由懒加载
   - 配置路由元信息（meta）
9. 状态管理要求：
   - 按照业务模块拆分store
   - 遵循Vuex的设计规范
   - 避免滥用全局状态
10. 样式规范：
    - 使用SCSS预处理器
    - 遵循BEM命名规范
    - 抽离公共样式
    - 使用主题变量

## 2. 开发规范

### 2.1 代码规范
- 使用阿里巴巴Java开发手册规范
- 统一使用UTF-8编码
- 缩进使用4个空格
- 行宽限制120字符
- 文件末尾保留一个空行

### 2.2 命名规范

#### 2.2.1 项目命名
- 微服务模块：learn-shop-{module}
- 基础组件：learn-shop-base-{component}
- 核心服务：learn-shop-core-{service}
- 管理服务：learn-shop-admin-{service}

#### 2.2.2 包命名
- 基础包名：com.billow
- 模块包名：com.billow.{module}
- 后台端接口：*.api
- 移动端接口：*.app
- 业务逻辑层：*.service
- 接口实现类：*.service.impl
- 数据访问层：*.dao
- 实体类-新增对象：*.pojo.build
- 实体类-扩展对象：*.pojo.ex
- 实体类-查询对象：*.pojo.search
- 实体类-视图对象：*.pojo.vo
- 实体类-数据库实体：*.pojo.po
- 工具类：*.util

#### 2.2.3 类命名
- Api类：*Api
- Service接口：*Service
- Service实现：*ServiceImpl
- DAO接口：*Mapper
- 实体类-新增对象：*BuildParam
- 实体类-扩展对象：*Ex
- 实体类-查询对象：*SearchParam
- 实体类-视图对象：*Vo
- 实体类-数据库实体：*Po


### 2.3 接口规范

#### 2.3.1 RESTful API规范
- POST：新增资源
- GET：获取资源
- PUT：更新资源
- DELETE：删除资源

#### 2.3.2 响应格式
```json
{
    "code": 200,
    "message": "success",
    "data": {}
}
```

#### 2.3.3 接口文档
- 使用Swagger2注解
- @Api：标记控制器
- @ApiOperation：标记接口
- @ApiParam：标记参数
- @ApiModel：标记实体
- @ApiModelProperty：标记字段

### 2.4 数据库规范

#### 2.4.1 表命名
- 使用小写字母
- 单词间用下划线分隔
- 表名格式：模块名_业务名

#### 2.4.2 字段命名
- 使用小写字母
- 单词间用下划线分隔
- 必备字段：
  - id：主键
  - create_time：创建时间
  - update_time：更新时间
  - is_deleted：删除标记

### 2.5 缓存规范

#### 2.5.1 Redis使用规范
- 键名规范：{服务名}:{业务名}:{数据类型}:{标识}
- 使用RedisTemplate注入时指定类型
```java
@Resource
private RedisTemplate<String, Object> redisTemplate;  // 正常缓存
@Resource
private RedisTemplate<String, Object> redisCacheTemplate;  // MyBatis缓存
```

#### 2.5.2 缓存更新策略
- 先更新数据库，再删除缓存
- 使用分布式锁防止缓存击穿
- 设置合理的过期时间

### 2.6 消息队列规范

#### 2.6.1 RabbitMQ使用规范
- 交换机命名：{服务名}.{业务名}.exchange
- 队列命名：{服务名}.{业务名}.queue
- 路由键命名：{服务名}.{业务名}.routing.key

#### 2.6.2 消息格式
```json
{
    "messageId": "消息ID",
    "messageType": "消息类型",
    "timestamp": "时间戳",
    "data": "消息内容"
}
```

### 2.7 日志规范

#### 2.7.1 日志级别
- ERROR：系统异常，影响功能
- WARN：警告信息，可能影响功能
- INFO：重要业务信息
- DEBUG：调试信息

#### 2.7.2 日志内容
- 必须包含：时间、级别、类名、方法名
- 异常日志必须记录完整堆栈
- 敏感信息脱敏处理

### 2.8 异常处理

#### 2.8.1 异常分类
- BusinessException：业务异常
- SystemException：系统异常
- ValidationException：参数校验异常

#### 2.8.2 异常处理原则
- 统一使用全局异常处理
- 业务异常必须指定错误码
- 不允许吞掉异常
- 异常信息国际化

## 3. 开发流程

### 3.1 分支管理
- master：主分支，稳定版本
- develop：开发分支
- feature/*：功能分支
- hotfix/*：紧急修复分支
- release/*：发布分支

### 3.2 版本发布流程
1. 功能开发（feature分支）
2. 代码评审
3. 合并到develop分支
4. 测试验证
5. 创建release分支
6. 版本测试
7. 合并到master分支
8. 打版本标签

### 3.3 代码提交规范
- feat：新功能
- fix：修复bug
- docs：文档更新
- style：代码格式
- refactor：重构
- test：测试
- chore：构建过程或辅助工具的变动

## 4. 部署规范

### 4.1 环境配置
- 开发环境：dev
- 测试环境：test
- 预发布环境：pre
- 生产环境：prod

### 4.2 配置中心
- 配置文件格式：{服务名}-{环境}.yml
- 必须配置项：
  - 服务端口
  - 数据库连接
  - Redis配置
  - RabbitMQ配置
  - 日志级别

### 4.3 启动参数
```shell
# 链路追踪
-javaagent:/path/to/skywalking-agent.jar
-Dskywalking.agent.service_name={服务名}
-Dskywalking.collector.backend_service={收集器地址}
```

### 4.4 健康检查
- 使用Spring Boot Actuator
- 必须开启：health、info、metrics
- 定期检查服务状态

## 5. 安全规范

### 5.1 认证授权
- 统一使用OAuth2.0
- JWT令牌格式
- 权限控制到按钮级别
- 使用权限码管理权限

### 5.2 密码安全
- 密码必须加密存储
- 使用BCrypt加密算法
- 定期要求修改密码
- 密码强度要求

### 5.3 接口安全
- 使用HTTPS
- 接口签名验证
- 防重放攻击
- 参数校验
- SQL注入防护
- XSS防护

## 6. 性能规范

### 6.1 数据库性能
- 合理使用索引
- 避免全表扫描
- 分页查询限制
- 批量操作优化
- 大事务拆分

### 6.2 缓存优化
- 合理使用缓存
- 防止缓存雪崩
- 防止缓存穿透
- 热点数据优化

### 6.3 JVM优化
- 合理设置内存大小
- 选择适当的GC算法
- 定期监控GC情况
- 及时处理内存泄漏

## 7. 测试规范

### 7.1 单元测试
- 测试类命名：*Test
- 测试方法命名：test_方法名_场景
- 测试覆盖率要求：>80%
- 必须包含正向和异常测试

### 7.2 接口测试
- 接口文档完整
- 测试用例完整
- 包含性能测试
- 自动化测试 