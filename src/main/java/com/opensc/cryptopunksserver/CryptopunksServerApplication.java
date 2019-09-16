package com.opensc.cryptopunksserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CryptopunksServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptopunksServerApplication.class, args);
	}

    @Bean(initMethod="init")
    public ApplicationInit exBean() {
        return new ApplicationInit();
    }
}