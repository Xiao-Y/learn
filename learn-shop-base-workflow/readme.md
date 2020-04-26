### 一、pom.xml 中添加

````xml
<!-- spring 相关 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- 工作流 -->
<dependency>
    <groupId>com.billow</groupId>
    <artifactId>learn-shop-base-workflow</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<!-- mysql -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
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

### 三、使用 activiti modelet 构建 bpmn 流程图
下载 activiti-app 修改 `activiti-app\WEB-INF\classes\META-INF\activiti-app` 路径下的`activiti-app.properties`中的数据库配置文件
```properties
datasource.url=jdbc:mysql://127.0.0.1:3306/learn?characterEncoding=UTF-8
datasource.username=root
datasource.password=root
```
还可以修改用户名、密码和角色
```properties
admin.email=admin
admin.password=test
admin.lastname=Administrator
```
启动 tomcat 后，使用以上配置的用户名和密码登陆 

**注意：**
启动类上需要添加 @ComponentScan("com.billow") 扫描路径