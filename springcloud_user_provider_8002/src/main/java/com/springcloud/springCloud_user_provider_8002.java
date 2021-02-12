package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*import org.springframework.boot.web.support.SpringBootServletInitializer;
 * 这个用不了就使用下面这个代替
 * */

@SpringBootApplication
@EnableEurekaClient//注册到注册中心
@EnableDiscoveryClient//服务发现=》注册到多个服务中心去；
public class springCloud_user_provider_8002 extends SpringBootServletInitializer {
    //在pom配置打包方式为war，然后再启动类这里写下面这方法，就能做到tomcat启动项目了，方便热加载
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(springCloud_user_provider_8002.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(springCloud_user_provider_8002.class,args);
        System.out.println("启动成功！");
    }
}
