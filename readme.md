# 配置文件获取

> 采用 `nacos` 做为配置中心及路由中心，所有配置文件都是从配置中心获取：`nacos--->dev-->cloud-config.properties`， 需要依赖 `learn-cloud-common` 模块

# 模块说明

## **1.公用组件**

- `learn-cloud-common` 获取配置中心配置文件，所有的 `learn-cloud-*` 都要依赖
- `learn-shop-base` 包含：

> `learn-shop-base-aop` aop 公用切面，统一数据格式返回，统一异常，日志打印，long 转前端 string。所有业务模块都要依赖
>
> `learn-shop-base-common` mq 交换机、路由、队列的定义，reids、swagger2、线程池的配置，获取用户信息工具类，redis 操作和redis锁控制类。所有业务模块都要依赖
>
> `learn-shop-base-email` 邮件发送配置。system 依赖
>
> `learn-shop-base-job` 定时任务配置，api 提供。system 依赖
>
> `learn-shop-base-jpa` jpa 配置，jpa 操作基本方法封装（弃用）
>
> `learn-shop-base-mybatis` mybatis 配置，基本方法封装，代码生成器。所有操作数据库的都要依赖
>
> `learn-shop-base-tools` 公用工具类方法
>
> `learn-shop-base-workflow` 工作流式相关。system 依赖
>
> `learn-shop-base-notice` 消息通知相关。job 依赖
> 
> 
## **2.核心服务，端口：87****

> `nacos` 注册中心，分布式配置中心 端口：8761
>
> `learn-cloud-getaway` 路由网关，端口：8771

## **3.后端业务服务，端口：88/89****

> `learn-shop-app` app端，端口：8089
> 
> `learn-shop-admin-user` 用户管理服务，端口：8801
>
> `learn-shop-admin-system` 系统管理服务，端口：8811
>
> `learn-shop-core-order` 购物车服务，端口：8901
>
> `learn-shop-core-cart` 购物车服务，端口：
>
> `learn-shop-core-product` 购物车服务，端口：8911
>
> `learn-shop-core-search` 搜索服务，端口：8981
>
> `learn-shop-core-promotion` 促销服务，端口：8921

# 项目启动顺序
* mysql
* redis
* rabbtimq
* nacos （单机启动命令：./startup.sh -m standalone）
* learn-cloud-getaway
* 启动业务服务

# 访问：（通过路由）
- 注册中心、配置中心：
> http://localhost:8761/nacos/index.html
> 
> 用户名/密码：nacos/nacos

- 业务服务：
>http://localhost:8771/core-order #订单相关
> 
>http://localhost:8771/admin-user #用户相关

- RabbitMQ: 管理页面
>http://localhost:15672
> 
>用户名/密码：admin/admin123

- Swagger2: 管理页面 <br>
>http://localhost:<port>/swagger-ui.html（查看单个）
> 
>http://localhost:8771/swagger-ui.html（查看聚合）
> 
>或者进入注册中心点击实例链接直接查看

# **注意**：

- 特别提醒：如果使用本地配置文件需要修改 `learn-cloud-common` 下的 `resources` 里面的 `bootstrap.yml` 的 `search-locations` 修改为本地路径

- 添加新服务时，要在 `learn-cloud-gateway` 中添加路由表

- 首次启动修改 `learn-shop-admin-system.yml` 中的 `database-schema-update` 为 true，自动建工作流的表

- 首次启动修改 `learn-shop-admin-system.yml` 中的 `start-init-data` 为 true，加载菜单和权限缓存

```yaml
core-order:
  path: /core-order/**
  serviceId: learn-shop-core-order
```

- 使用配置中心时 配置中心启动时会向注册中心注册，这里注册中心还没启动会报异常，不用关心
- 如果是 `learn-cloud-*` `pom` 中添加 `learn-cloud-common` 依赖
- 如果是 `learn-shop-admin-*` 和 `learn-shop-core-*` `pom `中添加 `learn-shop-base-common` 依赖
- 项目启动先要条件

> RabbitMQ, rabbitmq-server.bat
> 
> 添加新用户：admin 密码：admin123，修改admin用为超级管理员
> 
> 查询所有用户：rabbitmqctl.bat list_users
> 
> 添加新用户: rabbitmqctl.bat add_user username password
> 
> 赋予用户权限：rabbitmqctl.bat set_user_tags username administrator
> 
> 在admin中设定虚拟主机（virtual-host）为/learn-default

- redis启动

- [swagger2注解](https://www.jianshu.com/p/12f4394462d5) 使用说明 <br/>

- 注入 RedisTemplate 需要通过 `@Resource` 明确指定。因为两个使用的库不同，mybatis 默认使用的 15 号库。

```java
// 正常设值的
@Resource
private RedisTemplate<String, Object> redisTemplate;

// mybatis 设置缓存
@Resource
protected RedisTemplate<String, Object> redisCacheTemplate;
```

```shell
// 链路跟踪
-javaagent:D:\docker\SkyWalkIng\skywalking-agent\skywalking-agent.jar -Dskywalking.agent.service_name=learn-shop-core-product -Dskywalking.collector.backend_service=127.0.0.1:11800
```

# 其它
> HTTP Method 与 CURD 数据处理操作对应<br/>

> POST Create 新增一个没有id的资源<br/>

> GET Read 取得一个资源<br/>

> PUT Update 更新一个资源。或新增一个含 id 资源(如果 id 不存在)<br/>

> DELETE Delete 删除一个资源<br/>

# TODO
1.缓存的调整
2.订单释放
3.权限调整为权限码形式
4.后台管理首页修改为卡片形式

