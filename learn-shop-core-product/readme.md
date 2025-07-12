# Learn Shop Core Product

## 项目简介
这是一个基于 Spring Boot 和 Spring Cloud 的微服务项目核心产品模块。该模块主要负责产品相关的核心业务逻辑处理。

## 技术栈
- Spring Boot
- Spring Cloud Alibaba
- Nacos (配置中心)
- MyBatis
- Redis
- Maven
- Docker

## 项目结构
```
learn-shop-core-product/
├── src/
│   ├── main/
│   │   ├── java/                     # Java 源代码
│   │   │   └── com.billow.product/   # 主包
│   │   │       ├── api/             # 后台页面应用层
│   │   │       ├── app/             # 移动页面应用层
│   │   │       ├── cache/           # 缓存相关
│   │   │       ├── common/          # 公共工具类
│   │   │       ├── config/          # 配置类
│   │   │       ├── consume/         # 服务消费者
│   │   │       ├── dao/             # 数据访问层
│   │   │       ├── job/             # 定时任务
│   │   │       ├── pojo/            # 实体类
│   │   │       ├── receive/         # 消息接收处理
│   │   │       ├── service/         # 业务逻辑层
│   │   │       └── CoreProductApp.java  # 应用启动类
│   │   ├── resources/               # 配置文件
│   │   │   ├── mapper.base/        # MyBatis映射文件
│   │   │   ├── application.yml     # 应用配置
│   │   │   └── logback-logstash.xml # 日志配置
│   │   └── docker/                  # Docker 相关配置
│   └── test/                        # 测试代码
├── target/                          # 编译输出目录
└── pom.xml                         # Maven 配置文件
```

### 核心模块说明
- **api**: 后台页面应用层
- **app**: 移动页面应用层
- **cache**: 缓存管理，包含缓存策略和实现
- **common**: 通用工具类和常量定义
- **config**: 应用配置类，包含各种中间件和框架配置
- **consume**: 服务消费者，调用其他微服务
- **dao**: 数据访问层，处理数据库操作
- **job**: 定时任务管理
- **pojo**: 数据模型和传输对象
- **receive**: 消息接收处理，处理异步消息
- **service**: 核心业务逻辑实现

### 配置文件说明
- **application.yml**: 应用主配置文件，包含服务端口、数据源等配置
- **logback-logstash.xml**: 日志配置文件，支持日志收集
- **mapper.base/**: MyBatis SQL映射文件目录

## 主要功能
- 产品核心业务处理
- 数据持久化
- 缓存管理
- 消息通知

## 依赖模块
- learn-shop-base-common：公共基础模块
- learn-shop-base-aop：AOP 基础模块
- learn-shop-base-mybatis：MyBatis 基础模块
- learn-shop-base-redis：Redis 基础模块
- learn-shop-base-notice：消息通知模块

## 配置说明
项目使用 Nacos 作为配置中心，主要配置包括：
- 数据库配置
- Redis 配置
- 业务配置

## 构建和部署
### 本地开发环境搭建
1. 确保已安装以下软件：
   - JDK 8+
   - Maven 3.6+
   - Docker（可选）

2. 克隆项目并构建：
```bash
mvn clean package
```

### Docker 部署
项目支持 Docker 部署，构建命令：
```bash
mvn clean package docker:build
```

## 开发指南
1. 代码规范
   - 遵循阿里巴巴 Java 开发规范
   - 使用 Lombok 简化代码
   - 必要的注释和文档

2. 分支管理
   - master：主分支，用于生产环境
   - develop：开发分支
   - feature/*：新功能分支
   - hotfix/*：紧急修复分支

## 监控和维护
- 使用 Spring Boot Actuator 进行应用监控
- 集成日志系统进行问题排查
- 支持优雅停机

## 版本信息
当前版本：3.0-SNAPSHOT

## 联系方式
如有问题请联系项目维护团队。 