路由网关(zuul)：主要用于集群，不必关心端口是多少，只要serviceId相同就可以通过路由表匹配

访问：
http://localhost:8771/core-order/testOrder/indexUser

原理：
通过路由表
zuul:
  routes:
    admin-user:
      path: /admin-user/**
      serviceId: learn-shop-admin-user
    core-order:
          path: /core-order/**
          serviceId: learn-shop-core-order
          

在learn-shop-core-order工程中：
spring:
  application:
    name: learn-shop-core-order #客户端名称