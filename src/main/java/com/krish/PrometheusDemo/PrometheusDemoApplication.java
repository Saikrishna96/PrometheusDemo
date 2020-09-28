package com.krish.PrometheusDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrometheusDemoApplication {

    private final BeerService beerService;

    public PrometheusDemoApplication(BeerService beerService) {
        this.beerService = beerService;
    }

	public static void main(String[] args) {
        System.out.println("yolyoyoyooyyoy");
	    SpringApplication.run(PrometheusDemoApplication.class, args);
	}

}
