package com.rivera.denunciaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DenunciaserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DenunciaserviceApplication.class, args);
	}

}
