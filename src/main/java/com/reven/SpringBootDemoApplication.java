package com.reven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName:  SpringBootDemoApplication   
 * @author reven
 */
@SpringBootApplication
@MapperScan("com.reven.dao")
@EnableScheduling
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

}
