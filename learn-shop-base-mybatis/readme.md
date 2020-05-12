## learn-shop-base-mybatis 封装插件
添加pom文件
```xml
<dependency>
    <groupId>com.billow</groupId>
    <artifactId>learn-shop-base-mybatis</artifactId>
</dependency>
```

使用的是`mybatis-plus`，需要继承`BaseMapper`，其中已经封装好了常用的方法。
```properties
# mybatis-plus
# 对应实体类的包名或者 
mybatis-plus.type-aliases-package==com.billow.xxxx.entity
# 指定 mapper 位置。
mybatis-plus.mapper-locations=classpath:mapper/*/*.xml
```

添加扫描:
```java
@MapperScan("com.billow.*.dao")
```

###`learn-shop-base-mybatis`中封装了：

1.自动填充时间操作人的方法，请参见`AuditMetaObjectHandler`类。
```java
/**
 * 审计数据插件
 *
 * @return AuditMetaObjectHandler
 */
@Bean
@ConditionalOnMissingBean(name = "auditMetaObjectHandler")
public AuditMetaObjectHandler auditMetaObjectHandler() {
    return new AuditMetaObjectHandler();
}
```

2.分页插件。
```java
/**
 * mybatis 分页插件
 *
 * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
 * @author LiuYongTao
 * @date 2019/11/1 10:41
 */
@Bean
public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor page = new PaginationInterceptor();
    page.setDialectType(DbType.MYSQL.getDb());
    return page;
}
```

3.缓存功能，默认使用Redis，所有使用时需要配置redis
3.1redis 配置
```properties
#redis主机地址
spring.redis.host=${redis.service.ipAddress}
#redis主机端口
spring.redis.port=6379
#redis链接密码
spring.redis.password=
spring.redis.pool.maxActive=50
spring.redis.pool.maxWait=-1
spring.redis.pool.maxIdle=5
spring.redis.pool.minIdle=0
spring.redis.timeout=0
```

3.2开启二级缓存配置
```properties
# 开启缓存
mybatis-plus.configuration.cache-enabled=true
```

3.3使`mybatis-plus` 提供的方法生效,需要在继承`BaseMapper`方法上添加`@CacheNamespace`注解，指定缓存策略，默认使用`com.billow.mybatis.cache.MybatisRedisCache` 
```java
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsBrandDao extends BaseMapper<GoodsBrandPo> {

}
```

3.4自定义方法需要使用缓存请在xml文件中添加：
```xml
<!-- 使用缓存 -->
<!-- 注意：默认为 flushCache="true" useCache="true" -->
<!-- 1.自定义查询方法时，必须 flushCache="false" useCache="false"， -->
<!-- 2.更新，添加，删除时，必须 flushCache="true" useCache="true" 或者不添加 -->
<!-- 3.请务修改 cache-ref 中的配置 -->
<cache-ref namespace="继承 BaseMapper 的Dao的全类名"/>
<cache-ref namespace="com.billow.springbootmybatisredis.mapper.GirlsInfoDao"/>
```

4.本插件提供代码生成，代码生成类为 `com.billow.mybatis.gen.CodeGenerator`，执行main方法。
请根据需求修改其中的配置文件

