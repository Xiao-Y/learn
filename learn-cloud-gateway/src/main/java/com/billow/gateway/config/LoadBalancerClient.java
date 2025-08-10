//package com.billow.gateway.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.loadbalancer.Request;
//import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Component
//public class LoadBalancerClient {
//
//    @Autowired
//    private ReactiveLoadBalancer.Factory<ServiceInstance> loadBalancerFactory;
//
//    public Mono<ServiceInstance> choose(String serviceId) {
//        ReactiveLoadBalancer<ServiceInstance> loadBalancer = loadBalancerFactory.getInstance(serviceId);
//        return loadBalancer.choose(new Request() {
//        });
//    }
//}