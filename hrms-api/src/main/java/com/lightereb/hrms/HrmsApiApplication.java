package com.lightereb.hrms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用启动入口
 */
@SpringBootApplication
@MapperScan("com.lightereb.hrms.mapper")
public class HrmsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrmsApiApplication.class, args);
    }

}
