package com.billow.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients //(basePackages={"com.billow.remote"})
@EnableHystrix
@EnableHystrixDashboard
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
//@EnableJpaRepositories(repositoryBaseClass = WiselyRepositoryImpl.class)
public class AuthServerApplication {

//    @Bean(name = "auditorAware")
//    public AuditorAware<String> auditorAware() {
//        return () -> SecurityUtils.getCurrentUserUsername();
//    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }
}
