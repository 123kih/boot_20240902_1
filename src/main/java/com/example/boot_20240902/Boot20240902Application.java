package com.example.boot_20240902;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//자동정렬 => alt + shift + f
@SpringBootApplication

//컨트롤러 위치 설정
@ComponentScan(basePackages = {
	"com.example.controller" ,
	"com.example.restcontroller" ,
	"com.example.security",
	"com.example.token",
	"com.example.service"
})

//매퍼 위치 설정
@MapperScan(basePackages = {"com.example.mapper"})

//엔티티 위치 설정
@EntityScan(basePackages = {"com.example.entity"})

//저장소 위치 설정
@EnableJpaRepositories(basePackages = {"com.example.repository"})


public class Boot20240902Application {
	public static void main(String[] args) {
		SpringApplication.run(Boot20240902Application.class, args);
	}

}
