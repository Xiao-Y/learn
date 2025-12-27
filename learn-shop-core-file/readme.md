# Learn Shop Core File

## 项目简介
这是一个基于 Spring Boot 和 Spring Cloud 的微服务项目核心文件模块。该模块主要负责文件存储相关的核心业务逻辑处理，集成了 MinIO 对象存储服务，支持文件上传下载、视频缩略图生成、文件过期管理等功能。

## 技术栈
- Spring Boot 2.x
- Spring Cloud Alibaba
- Nacos (配置中心)
- MyBatis Flex (数据库操作)
- MinIO (对象存储)
- JavaCV (视频处理)
- Maven
- Docker

## 项目结构
```
learn-shop-core-file/
├── src/
│   ├── main/
│   │   ├── java/                     # Java 源代码
│   │   │   └── com.billow.file/      # 主包
│   │   │       ├── api/             # REST API 控制器
│   │   │       ├── config/          # 配置类和工具类
│   │   │       ├── dao/             # 数据访问层
│   │   │       ├── job/             # 定时任务
│   │   │       ├── pojo/            # 实体类和数据传输对象
│   │   │       │   ├── po/          # 持久化对象
│   │   │       │   └── search/      # 搜索参数对象
│   │   │       ├── service/         # 业务逻辑层
│   │   │       │   └── impl/        # 业务逻辑实现
│   │   │       ├── utils/           # 工具类
│   │   │       └── CoreFileApp.java # 应用启动类
│   │   ├── resources/               # 配置文件
│   │   │   ├── mapper.base/        # MyBatis映射文件
│   │   │   ├── application.yml     # 应用配置
│   │   │   └── logback-logstash.xml # 日志配置
│   │   └── docker/                  # Docker 相关配置
│   └── test/                        # 测试代码
├── target/                          # 编译输出目录
├── learn-shop-core-file.iml        # IntelliJ IDEA 模块文件
├── pom.xml                         # Maven 配置文件
└── readme.md                       # 项目说明文档
```

## 核心功能

### 1. 文件管理
- **文件上传**：支持任意类型文件上传到 MinIO 对象存储
- **文件下载**：支持文件下载和预览
- **文件去重**：基于 MD5 校验，避免重复存储相同文件
- **文件编号**：自动生成唯一文件编号（WJ前缀）
- **过期管理**：自动从预签名 URL 提取过期时间

### 2. 视频处理功能
- **自动缩略图生成**：视频文件上传时自动生成缩略图
- **智能截取时间**：支持指定时间点截取，超出时长时自动选择中间位置
- **多格式支持**：支持 mp4, avi, mov, wmv, flv, mkv, webm, m4v, 3gp, rmvb 等格式
- **内存处理**：缩略图生成直接返回字节数组，无临时文件残留

### 3. 存储管理
- **MinIO 集成**：完整的 MinIO 对象存储操作
- **存储桶管理**：支持多存储桶操作
- **URL 管理**：自动生成预签名访问 URL
- **元数据管理**：完整的文件元数据存储和查询

### 4. 定时任务
- **文件清理**：定时清理过期文件
- **存储优化**：定期清理无效文件引用

## 核心类说明

### API 层
- **MinioObjectApi**: 文件管理 REST API，提供上传、下载、列表查询等接口

### 服务层
- **MinioObjectService**: 文件管理业务接口
- **MinioObjectServiceImpl**: 文件管理业务实现，包含完整的文件处理逻辑

### 工具类
- **VideoProcessKit**: 视频处理工具类，专门负责视频缩略图生成
- **VideoFrameExtractor**: 视频帧提取工具，基于 JavaCV 实现
- **MinioKit**: MinIO 操作工具类

### 数据模型
- **MinioObject**: 文件对象实体，包含文件的所有元数据信息
- **MinioObjectSearchParam**: 文件搜索参数对象

### 定时任务
- **FileCleanupJob**: 文件清理定时任务

## 技术特性

### 1. 流处理优化
- **临时文件策略**：所有文件先保存到临时目录，避免流重复读取问题
- **资源管理**：使用 try-with-resources 确保资源正确释放
- **异常处理**：完善的异常处理机制，确保临时文件清理

### 2. 视频处理
- **无临时文件**：缩略图生成直接返回字节数组
- **智能时间选择**：自动选择最佳截取时间点
- **格式检测**：自动检测视频文件格式

### 3. 数据一致性
- **MD5 去重**：基于文件内容 MD5 避免重复存储
- **事务管理**：使用 @Transactional 确保数据一致性
- **过期时间提取**：自动从 MinIO 预签名 URL 提取过期时间

## 配置说明

### 应用配置
```yaml
# MinIO 配置
minio:
  bucketName: your-bucket-name
  endpoint: http://localhost:9000
  accessKey: your-access-key
  secretKey: your-secret-key

# 临时文件目录配置
file:
  temp:
    dir: ${java.io.tmpdir}
```

### 依赖配置
主要依赖包括：
- learn-shop-base-common：公共基础模块
- learn-shop-base-mybatis：MyBatis 基础模块
- JavaCV：视频处理库
- MinIO Java SDK：对象存储客户端

## API 接口

### 文件上传
```http
POST /minio/upload
Content-Type: multipart/form-data

参数：
- file: 上传的文件
- generateThumbnail: 是否生成缩略图（视频文件）
```

### 文件下载
```http
GET /minio/download/{id}

参数：
- id: 文件ID
```

### 文件列表
```http
POST /minio/list
Content-Type: application/json

请求体：MinioObjectSearchParam 搜索参数
```

### 富文本编辑器上传
```http
POST /minio/wangEditorUpload
Content-Type: multipart/form-data

参数：
- file: 上传的文件
```

## 开发指南

### 1. 本地开发环境
1. 安装必要软件：
   - JDK 8+
   - Maven 3.6+
   - MinIO Server
   - Docker（可选）

2. 启动 MinIO 服务：
```bash
docker run -p 9000:9000 -p 9001:9001 \
  -e "MINIO_ROOT_USER=admin" \
  -e "MINIO_ROOT_PASSWORD=password" \
  minio/minio server /data --console-address ":9001"
```

3. 构建项目：
```bash
mvn clean compile
```

### 2. 代码规范
- 遵循阿里巴巴 Java 开发规范
- 使用 Lombok 简化代码
- 完善的注释和文档
- 统一的异常处理

### 3. 测试
- 单元测试覆盖核心业务逻辑
- 集成测试验证 MinIO 集成
- 视频处理功能测试

## 部署说明

### Docker 部署
```bash
# 构建镜像
mvn clean package docker:build

# 运行容器
docker run -d \
  --name learn-shop-core-file \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  learn-shop-core-file:latest
```

### 生产环境配置
- 配置 MinIO 集群
- 设置合适的临时目录
- 配置文件清理策略
- 监控文件存储使用情况

## 监控和维护

### 1. 应用监控
- Spring Boot Actuator 健康检查
- 文件上传下载统计
- 存储空间使用监控

### 2. 日志管理
- 结构化日志输出
- 文件操作审计日志
- 错误日志告警

### 3. 性能优化
- 文件上传并发控制
- 缓存策略优化
- 定时任务调度优化

## 版本信息
- 当前版本：4.2-SNAPSHOT
- 最后更新：2024年12月

## 更新日志

### v4.2-SNAPSHOT
- 重构文件上传流程，解决流重复读取问题
- 优化视频处理功能，支持直接返回字节数组
- 统一临时文件处理策略
- 完善异常处理和资源清理
- 更新项目结构，统一代码风格

### 主要改进
1. **流处理优化**：所有文件统一使用临时文件策略
2. **视频处理增强**：缩略图生成无临时文件残留
3. **代码结构优化**：统一 API 和 Service 层设计
4. **资源管理改进**：完善的临时文件清理机制

## 联系方式
如有问题请联系项目维护团队。