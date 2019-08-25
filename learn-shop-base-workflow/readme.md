### 一、pom.xml 中添加

````xml
<dependency>
    <groupId>com.billow</groupId>
    <artifactId>learn-shop-base-workflow</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
````
### 二、配置文件
由于使用的是activiti中使用的是jpa,所有要配置jpa选项
````yml
spring:
  jpa: #JPA配置
    generate-ddl: false
    show-sql: true
    hibernate:
      ddl-auto: update
  activiti:
    check-process-definitions: false #校验流程文件，默认校验resources下的processes文件夹里的流程文件
    database-schema-update: true
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    username: 
    password: 
    url: jdbc:mysql://ip:port/XXX?useUnicode=true&characterEncoding=utf8&useSSL=false
````