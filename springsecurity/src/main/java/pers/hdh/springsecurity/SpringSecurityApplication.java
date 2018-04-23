package pers.hdh.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hdonghong
 * @date 2018/04/22
 */
@ComponentScan(basePackages={"pers.hdh.springsecurity"})// springboot默认扫描启动类同层包及其子包的类
@SpringBootApplication
@EnableAutoConfiguration
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}
}
