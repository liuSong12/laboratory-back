package com.study.labsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
@MapperScan("com.study.labsystem.mapper")
public class LaboratoryBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(LaboratoryBackApplication.class, args);
	}
}
