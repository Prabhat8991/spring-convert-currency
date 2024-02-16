package com.convertcurrency.convertcurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ConvertCurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvertCurrencyApplication.class, args);
	}

}
