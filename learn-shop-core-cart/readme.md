# 购物车服务设计文档

## 1. 系统设计

### 1.1 功能概述
购物车服务提供商品加入购物车、修改数量、删除商品、清空购物车、选择结算等基本功能。用户必须登录后才能使用购物车功能。

### 1.2 系统架构
- 采用Redis+MySQL的存储方案
  - Redis：存储购物车实时数据，提供高性能的读写操作
  - MySQL：存储持久化的购物车数据，用于数据分析和历史记录
- 消息队列：处理购物车数据同步、商品库存校验等异步任务
- 分布式锁：处理并发操作的数据一致性问题

### 1.3 核心流程
1. 加入购物车流程
   ```
   1. 验证用户登录状态和租户信息
   2. 校验商品信息（是否存在、是否上架、库存）
   3. 查询购物车是否已存在该商品
   4. 更新或新增购物车记录
   5. 同步数据到Redis缓存
   6. 记录操作日志
   ```

2. 结算流程
   ```
   1. 校验选中商品
   2. 验证商品库存和状态
   3. 计算商品价格（包含促销规则）
   4. 生成结算信息
   5. 跳转订单确认页
   6. 记录操作日志
   ```

### 1.4 数据同步策略
1. Redis到MySQL同步
   ```
   1. 定时任务：每5分钟同步一次
   2. 触发同步：数据变更累积到100条
   3. 手动同步：管理员触发
   ```

2. MySQL到Redis同步
   ```
   1. 系统启动：加载数据到Redis
   2. 数据订正：每天凌晨对账
   3. 缓存失效：按需加载
   ```

### 1.5 字段说明
1. 公共字段（继承自BasePo）
   ```
   id: 主键ID (bigint)
   creator_code: 创建人代码 (varchar)
   updater_code: 更新人代码 (varchar)
   create_time: 创建时间 (datetime)
   update_time: 更新时间 (datetime)
   valid_ind: 有效标志 (tinyint)
   ```

2. 状态定义
   ```
   购物车状态(status):
   1 - 正常
   2 - 已清空
   3 - 已下单

   商品状态(status):
   1 - 正常
   2 - 已失效
   3 - 已下单

   促销类型(promotion_type):
   1 - 满减
   2 - 折扣
   3 - 秒杀
   ```

### 1.6 数据一致性保证
1. 乐观锁
   ```
   - 使用version字段实现乐观锁
   - 更新时version+1
   - 并发冲突时重试3次
   ```

2. 分布式锁
   ```
   - 加入购物车时锁定SKU
   - 更新数量时锁定购物车项
   - 清空购物车时锁定用户
   ```

3. 缓存一致性
   ```
   - 采用延迟双删策略
   - 异步更新缓存
   - 定时对账数据
   ```

## 2. 数据库设计

### 2.1 购物车主表(cart_info)
```sql
CREATE TABLE `cart_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `total_quantity` int(11) NOT NULL DEFAULT 0 COMMENT '商品总数量',
    `total_amount` decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品总金额',
    `selected_quantity` int(11) NOT NULL DEFAULT 0 COMMENT '已选商品数量',
    `selected_amount` decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '已选商品金额',
    `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-已清空，3-已下单',
    `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `creator_code` varchar(64) DEFAULT NULL COMMENT '创建人代码',
    `updater_code` varchar(64) DEFAULT NULL COMMENT '更新人代码',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `valid_ind` tinyint(1) NOT NULL DEFAULT 1 COMMENT '有效标志：1-有效，0-无效',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车信息表';
```

### 2.2 购物车商品表(cart_item)
```sql
CREATE TABLE `cart_item` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
    `cart_id` bigint(20) NOT NULL COMMENT '购物车ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `product_id` bigint(20) NOT NULL COMMENT '商品ID',
    `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
    `quantity` int(11) NOT NULL DEFAULT 1 COMMENT '商品数量',
    `original_price` decimal(10,2) NOT NULL COMMENT '原始价格',
    `sale_price` decimal(10,2) NOT NULL COMMENT '销售价格',
    `subtotal` decimal(10,2) NOT NULL COMMENT '小计金额',
    `selected` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否选中：0-未选中，1-已选中',
    `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-已失效，3-已下单',
    `invalid_reason` varchar(255) DEFAULT NULL COMMENT '失效原因',
    `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    `product_name` varchar(200) NOT NULL COMMENT '商品名称',
    `sku_name` varchar(200) NOT NULL COMMENT 'SKU名称',
    `sku_spec_values` varchar(500) NOT NULL COMMENT 'SKU规格值JSON',
    `product_pic` varchar(500) NOT NULL COMMENT '商品图片',
    `promotion_id` bigint(20) DEFAULT NULL COMMENT '促销活动ID',
    `promotion_type` tinyint(4) DEFAULT NULL COMMENT '促销类型：1-满减，2-折扣，3-秒杀',
    `promotion_amount` decimal(10,2) DEFAULT 0.00 COMMENT '促销优惠金额',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `creator_code` varchar(64) DEFAULT NULL COMMENT '创建人代码',
    `updater_code` varchar(64) DEFAULT NULL COMMENT '更新人代码',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `valid_ind` tinyint(1) NOT NULL DEFAULT 1 COMMENT '有效标志：1-有效，0-无效',
    PRIMARY KEY (`id`),
    KEY `idx_cart_id` (`cart_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_sku_id` (`sku_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车商品表';
```

### 2.3 购物车操作日志表(cart_operation_log)
```sql
CREATE TABLE `cart_operation_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `tenant_id` bigint(20) NOT NULL COMMENT '租户ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `cart_id` bigint(20) NOT NULL COMMENT '购物车ID',
    `cart_item_id` bigint(20) DEFAULT NULL COMMENT '购物车商品ID',
    `operation_type` tinyint(4) NOT NULL COMMENT '操作类型：1-添加商品，2-更新数量，3-删除商品，4-清空购物车，5-选中商品，6-取消选中',
    `operation_desc` varchar(500) NOT NULL COMMENT '操作描述',
    `operation_result` tinyint(1) NOT NULL COMMENT '操作结果：0-失败，1-成功',
    `fail_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
    `operation_time` datetime NOT NULL COMMENT '操作时间',
    `creator_code` varchar(64) DEFAULT NULL COMMENT '创建人代码',
    `updater_code` varchar(64) DEFAULT NULL COMMENT '更新人代码',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `valid_ind` tinyint(1) NOT NULL DEFAULT 1 COMMENT '有效标志：1-有效，0-无效',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_cart_id` (`cart_id`),
    KEY `idx_tenant_id` (`tenant_id`),
    KEY `idx_operation_time` (`operation_time`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车操作日志表';
```

### 2.4 Redis数据结构
```
# 购物车数据 - Hash结构
key: cart:info:{userId}
field: base_info
value: {
    "id": "123",
    "totalQuantity": 2,
    "totalAmount": 199.98,
    "selectedQuantity": 1,
    "selectedAmount": 99.99,
    "status": 1,
    "version": 1,
    "validInd": true,
    "createTime": "2024-01-01 12:00:00",
    "updateTime": "2024-01-01 12:00:00"
}

# 购物车商品数据 - Hash结构
key: cart:items:{userId}
field: {skuId}
value: {
    "id": "456",
    "skuId": "789",
    "productId": "101",
    "quantity": 1,
    "originalPrice": 109.99,
    "salePrice": 99.99,
    "subtotal": 99.99,
    "selected": true,
    "status": 1,
    "productName": "示例商品",
    "skuName": "红色 XL",
    "skuSpecValues": "{\"color\":\"红色\",\"size\":\"XL\"}",
    "productPic": "http://example.com/image.jpg",
    "promotionId": 1001,
    "promotionType": 1,
    "promotionAmount": 10.00,
    "version": 1,
    "validInd": true,
    "createTime": "2024-01-01 12:00:00",
    "updateTime": "2024-01-01 12:00:00"
}

# 购物车商品数量限制 - String结构
key: cart:limit:{userId}
value: 99  # 最大商品种类数量
```

## 3. 接口文档

### 3.1 加入购物车
```
POST /cart/add

Request:
{
    "skuId": 123,
    "quantity": 1
}

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "cartId": 456,
        "itemCount": 1
    }
}
```

### 3.2 更新购物车商品数量
```
PUT /cart/update/quantity

Request:
{
    "skuId": 123,
    "quantity": 2
}

Response:
{
    "code": 200,
    "message": "success",
    "data": true
}
```

### 3.3 删除购物车商品
```
DELETE /cart/delete

Request:
{
    "skuIds": [123, 456]
}

Response:
{
    "code": 200,
    "message": "success",
    "data": true
}
```

### 3.4 获取购物车列表
```
GET /cart/list

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "items": [{
            "skuId": 123,
            "productId": 456,
            "quantity": 1,
            "selected": true,
            "price": 99.99,
            "productName": "示例商品",
            "skuName": "红色 XL",
            "picture": "http://example.com/image.jpg"
        }],
        "totalCount": 1,
        "totalPrice": 99.99
    }
}
```

### 3.5 选择/取消选择商品
```
PUT /cart/select

Request:
{
    "skuIds": [123, 456],
    "selected": true
}

Response:
{
    "code": 200,
    "message": "success",
    "data": true
}
```

### 3.6 获取结算商品信息
```
GET /cart/settlement

Response:
{
    "code": 200,
    "message": "success",
    "data": {
        "items": [{
            "skuId": 123,
            "quantity": 1,
            "price": 99.99,
            "subtotal": 99.99
        }],
        "totalCount": 1,
        "totalPrice": 99.99
    }
}
```

## 4. 性能优化

### 4.1 缓存策略
1. 购物车数据主要存储在Redis中，采用Hash结构
   - cart:info:{userId} 存储购物车基本信息
   - cart:items:{userId} 存储购物车商品信息
   - cart:limit:{userId} 存储购物车限制信息

2. 数据同步机制
   - 写入：先更新数据库，再删除缓存
   - 读取：先读缓存，缓存不存在则读库
   - 定时任务：每5分钟同步一次全量数据

3. 缓存预热
   - 系统启动时加载热门商品
   - 定时更新商品基础信息
   - 按需加载用户购物车数据

### 4.2 并发处理
1. 分布式锁
   ```java
   public boolean addToCart(Long skuId, Integer quantity) {
       String lockKey = "cart:lock:" + userId + ":" + skuId;
       try {
           // 获取分布式锁
           if (!redisLockService.tryLock(lockKey, 3, TimeUnit.SECONDS)) {
               throw new BusinessException("操作太频繁，请稍后重试");
           }
           // 执行购物车操作
           return doAddToCart(skuId, quantity);
       } finally {
           redisLockService.unlock(lockKey);
       }
   }
   ```

2. 乐观锁
   ```java
   public boolean updateQuantity(Long itemId, Integer quantity) {
       int retryCount = 0;
       while (retryCount < 3) {
           CartItem item = cartItemMapper.selectById(itemId);
           item.setQuantity(quantity);
           item.setVersion(item.getVersion() + 1);
           if (cartItemMapper.updateWithVersion(item) > 0) {
               return true;
           }
           retryCount++;
       }
       return false;
   }
   ```

### 4.3 限制策略
1. 数量限制
   - 单个用户购物车商品种类：99个
   - 单个商品购买数量：库存量
   - 购物车总商品数量：999个

2. 接口限制
   - 添加商品：每秒5次
   - 更新数量：每秒10次
   - 查询接口：每秒20次

3. 数据大小限制
   - Redis单个hash最大存储：512KB
   - 商品图片URL长度：500字符
   - 规格值JSON长度：500字符

## 5. 异常处理

### 5.1 业务异常
1. 商品不存在
2. 商品已下架
3. 库存不足
4. 购物车已满
5. 商品数量超限

### 5.2 系统异常
1. 缓存访问异常
2. 数据库访问异常
3. 并发操作异常
4. 网络超时异常

## 6. 监控告警

### 6.1 监控指标
1. 接口响应时间
2. 缓存命中率
3. 错误率统计
4. 并发数统计

### 6.2 告警规则
1. 接口响应时间>500ms
2. 错误率>1%
3. 缓存命中率<90%
4. 并发数>1000

## 7. 测试用例

### 7.1 功能测试
1. 添加商品到购物车
2. 修改商品数量
3. 删除购物车商品
4. 清空购物车
5. 选择/取消选择商品
6. 购物车商品列表
7. 结算商品信息

### 7.2 性能测试
1. 并发添加商品
2. 高频更新商品数量
3. 大量商品结算
4. 购物车同步压力测试

## 8. 部署说明

### 8.1 环境要求
- JDK 1.8+
- Redis 6.0+
- MySQL 5.7+
- RabbitMQ 3.8+

### 8.2 配置说明
```yaml
cart:
  # Redis配置
  redis:
    database: 0
    expire: 7  # 购物车数据过期天数
    prefix: "cart"  # 键前缀
  
  # 限制配置
  limit:
    maxItems: 99  # 最大商品种类数
    maxQuantity: 999  # 单个商品最大数量
    maxTotal: 999  # 购物车最大总数量
    
  # 异步任务配置
  async:
    corePoolSize: 5
    maxPoolSize: 10
    queueCapacity: 100
    
  # 分布式锁配置
  lock:
    timeout: 3  # 锁超时时间（秒）
    retry: 3  # 重试次数
    
  # 数据同步配置
  sync:
    interval: 300  # 同步间隔（秒）
    batchSize: 100  # 批量同步大小
    
  # 监控配置
  monitor:
    responseTime: 500  # 响应时间阈值（毫秒）
    errorRate: 0.01  # 错误率阈值
    hitRate: 0.9  # 缓存命中率阈值
```