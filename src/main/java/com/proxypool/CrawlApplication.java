package com.proxypool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.proxypool.dao")
@EnableScheduling
@EnableAsync
public class CrawlApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlApplication.class, args);
	}
}
