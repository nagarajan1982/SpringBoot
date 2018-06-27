package com.bskyb.internettv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.bskyb.internettv"})
public class ParentalControlServiceApplication {
	public static void main(String[] args) {
        SpringApplication.run(ParentalControlServiceApplication.class, args);
	}
}
