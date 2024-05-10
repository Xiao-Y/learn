后台控制器：系统管理服务

设置自增从指定位置开始 
```sql
ALTER TABLE sys_permission AUTO_INCREMENT = 158
```

链路跟踪：
docker-compose.yml（elasticsearch 之前已经安装了）
```yaml
version: '3'
services:
  #构建SkyWalking 服务
  skywalking-oap:
    image: apache/skywalking-oap-server:9.2.0
    container_name: skywalking-oap
    restart: always
    ports:
      - 11800:11800
      - 12800:12800
    environment:
      SW_CORE_RECORD_DATA_TTL: 15
      SW_CORE_METRICS_DATA_TTL: 15
      SW_STORAGE: elasticsearch
      SW_STORAGE_ES_CLUSTER_NODES: elasticsearch:9200
      TZ: Asia/Shanghai
      JAVA_OPTS: "-Xms2048m -Xmx2048m"
  #SkyWalkIng 可视化web界面
  skywalking-ui:
    image: apache/skywalking-ui:9.2.0
    container_name: skywalking-ui
    depends_on:
      - skywalking-oap
    links:
      - skywalking-oap
    restart: always
    ports:
      - 8099:8080
    environment:
      SW_OAP_ADDRESS: http://skywalking-oap:12800
      TZ: Asia/Shanghai

networks:
    default:
        external: true
        name: learn_shop

```

pom文件中添加（learn-shop-base-aop 中已经添加了）：
```xml
        <!-- skywalking链路追踪 -->
        <!-- 如果想在项目代码中获取链路TraceId，则需要引入此依赖 -->
        <dependency>
            <groupId>org.apache.skywalking</groupId>
            <artifactId>apm-toolkit-trace</artifactId>
            <version>9.2.0</version>
        </dependency>
        <!-- skywalking logback日志插件 -->
        <dependency>
            <groupId>org.apache.skywalking</groupId>
            <artifactId>apm-toolkit-logback-1.x</artifactId>
            <version>9.2.0</version>
        </dependency>
```

下载：
apache-skywalking-java-agent-9.0.0.tgz


VM options 中添加：
```
-javaagent:D:\docker\SkyWalkIng\skywalking-agent\skywalking-agent.jar
-Dskywalking.agent.service_name=learn-shop-admin-system
-Dskywalking.collector.backend_service=127.0.0.1:11800
```

logback-logstash.xml 中添加：
```xml
    <!-- 控制台打印 -->
    <appender name="CONSOLE_OUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.TraceIdPatternLogbackLayout">
                <Pattern>%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}) [%-5level] [%tid] %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}</Pattern>
            </layout>
        </encoder>
    </appender>

    <!-- 日志传输 -->
    <appender name="grpc" class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.log.GRPCLogClientAppender">
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="org.apache.skywalking.apm.toolkit.log.logback.v1.x.mdc.TraceIdMDCPatternLogbackLayout">
                <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{tid}] [%thread] %-5level %logger{36} -%msg%n</Pattern>
            </layout>
        </encoder>
    </appender>

    <!-- 一般用默认的info就可以 -->
    <root level="info">
        <!-- 控制台输出日志-->
        <appender-ref ref="CONSOLE_OUT"/>
        <appender-ref ref="grpc"/>
    </root>
```
