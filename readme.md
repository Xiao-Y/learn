所有配置文件都是从配置中心获取： <br/>
配置文件：learn-cloud-config--->cloud-config-repo-->cloud-config-dev.properties <br/>

核心服务，端口：87**： <br/>
learn-cloud-eureka  注册中心，端口：8761 <br/>
learn-cloud-zuul    路由网关，端口：8771 <br/>
learn-cloud-config  分布式配置中心，端口：8781 <br/>

公用业务服务，端口：80**： <br/>
learn-shop-public-common  公用组件，端口： <br/>
learn-shop-public-tools 公用工具，端口： <br/>

后端业务服务，端口：88**： <br/>
learn-shop-admin-user  用户管理服务，端口：8801 <br/>
learn-shop-admin-system  系统管理服务，端口： <br/>

前台业务服务，端口：89**： <br/>
learn-shop-core-order   购物车服务，端口：8901 <br/>
learn-shop-core-cart   购物车服务，端口： <br/>
learn-shop-core-product   购物车服务，端口： <br/>



项目启动顺序： <br/>
1.learn-cloud-config <br/>
2.learn-cloud-eureka <br/>
3.learn-cloud-zuul <br/>
4.启动公用业务服务 <br/>
5.启动业务服务 <br/>


访问：（通过路由） <br/>
业务服务： <br/>
http://localhost:8771/core-order #订单相关 <br/>
http://localhost:8771/admin-user #用户相关 <br/>


注意： <br/>
1.添加新服务时，要在learn-cloud-zuul中添加路由表 <br/>
&nbsp;core-order: <br/>
&nbsp;&nbsp;&nbsp;&nbsp;path: /core-order/** <br/>
&nbsp;&nbsp;&nbsp;&nbsp;serviceId: learn-shop-core-order <br/>

2.使用配置中心时， <br/>
记得复制：bootstrap.yml到resources中 <br/>
添加： <br/>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>




