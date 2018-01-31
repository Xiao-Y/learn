package com.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * 熔断器仪表盘聚合
 */
@EnableTurbine
@SpringBootApplication
@EnableHystrixDashboard
public class TurbineApp {
    public static void main(String[] args) {
        SpringApplication.run(TurbineApp.class, args);
    }
}
