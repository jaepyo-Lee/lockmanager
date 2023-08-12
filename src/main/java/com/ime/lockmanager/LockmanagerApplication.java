package com.ime.lockmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LockmanagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(LockmanagerApplication.class, args);
	}

}
