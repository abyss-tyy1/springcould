package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableEurekaServer //注册中心的注解
public class boot extends SpringBootServletInitializer {
    //在pom配置打包方式为war，然后再启动类这里写下面这方法，就能做到tomcat启动项目了，方便热加载
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(boot.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(boot.class,args);
        System.out.println("启动成功！");
    }
}
