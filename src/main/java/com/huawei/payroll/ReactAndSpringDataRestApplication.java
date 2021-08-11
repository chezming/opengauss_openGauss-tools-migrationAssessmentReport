package com.huawei.payroll;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.huawei.jdbc.mapper")
@ComponentScan(basePackages = {"com.huawei"})
public class ReactAndSpringDataRestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactAndSpringDataRestApplication.class, args);
    }
}