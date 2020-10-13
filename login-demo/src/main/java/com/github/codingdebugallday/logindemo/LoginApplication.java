package com.github.codingdebugallday.logindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * <p>
 * SpringBootServletInitializer 的类，该类在idea开发工具启动springBoot main函数时不会被初始化，
 * 在Tomcat启动时才会被初始化
 * </p>
 *
 * @author isaac 2020/10/13 16:27
 * @since 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession
public class LoginApplication extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(LoginApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(LoginApplication.class, args);
	}

}
